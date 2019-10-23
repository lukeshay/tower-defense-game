package com.pvptowerdefense.server.socket.handlers;

import com.pvptowerdefense.server.socket.models.Game;
import com.pvptowerdefense.server.socket.models.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Handler for socket connections. The request has to be sent to the domain
 * url/socket/{id} where id is the phone id.
 */
@Component
@ServerEndpoint("/socket/{id}")
public class SocketHandler {
	private static HashMap<String, Session> idAndSession = new HashMap<>();
	private static HashMap<Session, String> sessionAndId = new HashMap<>();
	private static List<Game> gameList = new ArrayList<>();
	private static Logger logger =
			LoggerFactory.getLogger(SocketHandler.class.getName());

	/**
	 * This class handles the incoming socket requests.
	 */
	public SocketHandler() {
	}

	/**
	 * When a connection is opened, the session and id is added to the hash
	 * maps.
	 *
	 * @param session The new session.
	 * @param id      The id of the user as a String.
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("id") String id) {
		CompletableFuture.runAsync(() -> {
			logger.info(id + " connected");
			idAndSession.put(id, session);
			sessionAndId.put(session, id);

			if (idAndSession.size() % 2 == 1) {
				session.getAsyncRemote().sendText(
						Messages.connectedTrueMatchUpFalse().toString()
				);
			}
			else {
				for (Map.Entry<Session, String> entry : sessionAndId.entrySet()) {
					Session otherSession = entry.getKey();
					String otherId = entry.getValue();

					if (!otherId.equals(id) && gameList.stream().noneMatch(game ->
							(game.getPlayerOneSession().equals(otherSession) ||
									game.getPlayerTwoSession().equals(otherSession)))) {
						gameList.add(new Game(otherId, otherSession, id,
								session));

						otherSession.getAsyncRemote().sendText(
								Messages.connectedTrueMatchUpTrue("left").toString()
						);

						session.getAsyncRemote().sendText(
								Messages.connectedTrueMatchUpTrue("right").toString()
						);
					}
				}
			}
			Game.getPool().purge();
		});

	}

	/**
	 * Calls the match up send message method if the user is in a match up.
	 *
	 * @param session The session sending the message.
	 * @param message The message as a byte[]. *NOTE This might change types.
	 */
	@OnMessage
	public void onMessage(Session session, String message) {
		CompletableFuture.runAsync(() -> {
			Game game = findMatchUp(session);
			if (game != null) {
				game.handleMessage(session, message);
			}
		});
	}

	/**
	 * Removes the session from the hash maps and it's match up from the list.
	 *
	 * @param session The session that is leaving.
	 * @param id      The id of the user as a String.
	 */
	@OnClose
	public void onClose(Session session, @PathParam("id") String id) {
		CompletableFuture.runAsync(() -> {
			idAndSession.remove(id);
			sessionAndId.remove(session);

			gameList.remove(findMatchUp(session));
			Game game = findMatchUp(session);

			if (game != null) {
				gameList.remove(game);
				Game.getPool().remove(game);
			}

			Game.getPool().purge();
		});
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		CompletableFuture.runAsync(() -> {
			session.getAsyncRemote().sendText("ERROR " + throwable.getMessage());
			throwable.printStackTrace();
		});
	}


	/**
	 * Finds the match up of the inputted session.
	 *
	 * @param session The session whose match up is being looked for.
	 * @return The match up.
	 */
	private Game findMatchUp(Session session) {
		try {
			return gameList.stream().filter(getMatchUpPredicate(session))
					.collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Helper for findMatchUp.
	 *
	 * @param session The session being looked for.
	 * @return Predicate for the match up.
	 */
	private Predicate<Game> getMatchUpPredicate(Session session) {
		return game -> game.getPlayerOneSession().equals(session) ||
				game.getPlayerTwoSession().equals(session);
	}

}
