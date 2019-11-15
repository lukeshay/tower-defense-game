package com.pvptowerdefense.server.socket.handlers;

import com.pvptowerdefense.server.socket.models.MatchUp;
import com.pvptowerdefense.server.socket.models.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
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
	private static List<MatchUp> matchUpList = new ArrayList<>();
	private static Logger logger =
			LoggerFactory.getLogger(SocketHandler.class.getName());

//	private UsersService usersService; TODO needs replaced by RestTemplate

	/**
	 * This class handles the incoming socket requests.
	 */
	@Autowired
	public SocketHandler() {
//		this.usersService = usersService;
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
//			if (!isValidUser(id)) {
//				try {
//					session.close();
//				} catch (IOException ignore) {
//				}
//
//				return;
//			}

			logger.info(id + " connected");
			purgeMapsAndList();

			idAndSession.put(id, session);
			sessionAndId.put(session, id);

			if (idAndSession.size() % 2 == 1) {
				logger.info(id + " not added to game");
				session.getAsyncRemote().sendText(
						Messages.connectedTrueMatchUpFalse()
				);
			}
			else {
				for (Map.Entry<Session, String> entry : sessionAndId.entrySet()) {
					Session otherSession = entry.getKey();
					String otherId = entry.getValue();

					if (!otherId.equals(id) && matchUpList.stream().noneMatch(matchUp ->
							(matchUp.getPlayerOneSession().equals(otherSession) ||
									matchUp.getPlayerTwoSession().equals(otherSession)))) {
						logger.info("matching up " + otherId + " and " + id);

						MatchUp matchUp = new MatchUp(otherId, otherSession, id, session);
						matchUp.startMatchUp();

						matchUpList.add(matchUp);

						otherSession.getAsyncRemote().sendText(
								Messages.connectedTrueMatchUpTrue("left")
						);

						session.getAsyncRemote().sendText(
								Messages.connectedTrueMatchUpTrue("right")
						);
					}
				}
			}
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
			MatchUp matchUp = findMatchUp(session);
			if (matchUp != null) {
				matchUp.handleMessage(session, message);
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
			purgeMapsAndList();
		});
	}

	/**
	 * Logs errors and sends them to the sessions they are linked to.
	 *
	 * @param session   the session
	 * @param throwable the error
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		CompletableFuture.runAsync(() -> {
			session.getAsyncRemote().sendText("ERROR " + throwable.getMessage());
			logger.error("Error in SocketHandler", throwable);
		});
	}


	/**
	 * Finds the match up of the inputted session.
	 *
	 * @param session The session whose match up is being looked for.
	 * @return The match up.
	 */
	private MatchUp findMatchUp(Session session) {
		try {
			return matchUpList.stream().filter(
					matchUp -> matchUp.getPlayerOneSession().equals(session) ||
							matchUp.getPlayerTwoSession().equals(session)
			)
					.collect(Collectors.toList()).get(0);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Cleans up the maps and lists. Removed sessions and games that are stale.
	 */
	private void purgeMapsAndList() {
		logger.info("purging");
		idAndSession.forEach((id, session) -> {
			if (!session.isOpen()) {
				idAndSession.remove(id);
				sessionAndId.remove(session);

				MatchUp matchUp = findMatchUp(session);
				if (matchUp != null) {
					matchUpList.remove(matchUp);
					MatchUp.getPool().remove(matchUp);
				}
			}
		});
	}

//	/**
//	 * Makes sure the user trying to connect is valid.
//	 *
//	 * @param id the userId
//	 * @return boolean
//	 */
//	private boolean isValidUser(String id) {
//		return usersService.isUserInDatabase(id);
//	}
}
