package com.pvptowerdefense.server.socket.models;

import com.pvptowerdefense.server.spring.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * The type Match up.
 */
public class MatchUp implements Runnable {
	private static final int MAX_T = 10;
	private static final int PRE_GAME_TIME = 30000;
	private static final int IN_GAME_TIME = 300000;
	private static final int POST_GAME_TIME = 300000;
	private static Logger logger =
			LoggerFactory.getLogger(MatchUp.class.getName());
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_T);
	private static RestTemplate restTemplate = new RestTemplate();
	private static WebClient.Builder webClientBuilder = WebClient.builder();
	private Session playerOneSession;
	private String playerOneId;
	private Session playerTwoSession;
	private String playerTwoId;
	private Game game;
	private SocketMessage socketMessage;

	/**
	 * Instantiates a new Match up.
	 *
	 * @param playerOneId      the player one id
	 * @param playerOneSession the player one session
	 * @param playerTwoId      the player two id
	 * @param playerTwoSession the player two session
	 */
	public MatchUp(String playerOneId, Session playerOneSession,
	               String playerTwoId, Session playerTwoSession) {
		this.playerOneSession = playerOneSession;
		this.playerTwoSession = playerTwoSession;

		game = new Game(playerOneId, playerTwoId);
		socketMessage = new SocketMessage(playerOneId, playerTwoId);

		pool.execute(this);
	}

	/**
	 * Gets pool.
	 *
	 * @return the pool
	 */
	public static ThreadPoolExecutor getPool() {
		return pool;
	}

	/**
	 * Gets the time in milliseconds.
	 *
	 * @return the current time in milliseconds
	 */
	private static long getTime() {
		return new Date().getTime();
	}

	/**
	 * Gets player one session.
	 *
	 * @return the player one session
	 */
	public Session getPlayerOneSession() {
		return playerOneSession;
	}

	/**
	 * Gets player two session.
	 *
	 * @return the player two session
	 */
	public Session getPlayerTwoSession() {
		return playerTwoSession;
	}

	/**
	 * Sleeps for given time.
	 *
	 * @param milliseconds time in milliseconds
	 */
	private void nap(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception ignore) {
		}
	}

	/**
	 * Closes both players connections.
	 */
	private void closeBothPlayersConnections() {
		logger.info("disconnecting both players");

		if (playerOneSession.isOpen()) {
			try {
				playerOneSession.close();
			} catch (IOException ignore) {
			}
		}

		if (playerTwoSession.isOpen()) {
			try {
				playerTwoSession.close();
			} catch (IOException ignore) {
			}
		}
	}

	/**
	 * Returns whether both users are connected.
	 *
	 * @return boolean
	 */
	private boolean areBothConnected() {
		return playerOneSession.isOpen() && playerTwoSession.isOpen();
	}

	/**
	 * Handle message coming from the users in the current match up.
	 *
	 * @param session the session
	 * @param message the message
	 */
	public void handleMessage(Session session, String message) {
		logger.info("handling message");
		PlayedCard card = Messages.convertJsonToCard(message);

		if (card != null) {
			game.addCard(card);
			session.getAsyncRemote().sendText(Messages.cardAdded());
		}
		else {
			getOtherSession(session).getAsyncRemote().sendText(message);
		}
	}

	private Session getOtherSession(Session session) {
		return session.equals(playerOneSession) ? playerTwoSession : playerOneSession;
	}

	/**
	 * Sends the given object to both players as json.
	 *
	 * @param o the message
	 */
	private void sendMessage(Object o) {
		CompletableFuture.runAsync(() -> {
			Future<Void> deliveryProgress1 =
					playerOneSession.getAsyncRemote().sendText(Messages.convertToJson(o));
			Future<Void> deliveryProgress2 =
					playerTwoSession.getAsyncRemote().sendText(Messages.convertToJson(o));

			deliveryProgress1.isDone();
			deliveryProgress2.isDone();
		});
	}

	/**
	 * Sets the socket message object to correct values and sends the pre
	 * game message.
	 */
	private void sendPreGameMessage() {
		logger.info("sending pre-game message");

		socketMessage.setGameState("pre-game");
		socketMessage.setServerMessage("");
		sendMessage(socketMessage);

	}

	/**
	 * Sets teh socket message object to correct values and sends the start
	 * game message.
	 */
	private void sendStartGameMessage() {
		logger.info("sending starting-game message");

		socketMessage.setGameState("starting-game");
		socketMessage.setServerMessage("starting game in 3 seconds");
		sendMessage(socketMessage);
	}

	/**
	 * Sets teh socket message object to correct values and sends the in
	 * game message.
	 *
	 * @param message a message from the server
	 * @param time    the game time in milliseconds
	 */
	private void sendInGameMessage(String message, int time) {
		logger.info("sending in-game message");

		socketMessage.setGameState("in-game");
		socketMessage.setServerMessage(message);
		socketMessage.setPlayedCards(game.getCards());
		socketMessage.setTurnState(game.getTurnState());
		socketMessage.setCurrentTime(time);
		socketMessage.setPlayerOneMana(game.getPlayerOneMana());
		socketMessage.setPlayerTwoMana(game.getPlayerTwoMana());
		sendMessage(socketMessage);
	}

	/**
	 * Sets the socket message object to correct values and sends the pre
	 * game message.
	 *
	 * @param time the post game time in milliseconds
	 */
	private void sendPostGameMessage(int time) {
		logger.info("sending in-game message");

		socketMessage.setGameState("in-game");
		socketMessage.setServerMessage("");
		socketMessage.setPlayedCards(game.getCards());
		socketMessage.setTurnState("");
		socketMessage.setWinner(game.getWinner());
		socketMessage.setCurrentTime(time);
		sendMessage(socketMessage);

		try {
			User user1 = webClientBuilder.build()
					.get()
					.uri(new URI(String.format("http://localhost:8080/users/%s", playerOneId)))
					.retrieve().bodyToMono(User.class).block();
			User user2 = webClientBuilder.build()
					.get()
					.uri(new URI(String.format("http://localhost:8080/users/%s", playerTwoId)))
					.retrieve().bodyToMono(User.class).block();

			if (user1.getPhoneId().equals(game.getWinner())) {
				user1.setTrophies(user1.getTrophies() + 10);
				if (user2.getTrophies() < 5) {
					user2.setTrophies(0);
				}
				else {
					user2.setTrophies(user2.getTrophies() - 5);
				}
			}
			else {
				user2.setTrophies(user2.getTrophies() + 10);
				if (user1.getTrophies() < 5) {
					user1.setTrophies(0);
				}
				else {
					user1.setTrophies(user1.getTrophies() - 5);
				}
			}

			webClientBuilder.build()
					.put()
					.uri("http://localhost:8080/users", user1)
					.retrieve();
			webClientBuilder.build()
					.put()
					.uri("http://localhost:8080/users", user2)
					.retrieve();
		}
		catch (URISyntaxException e) {
			logger.error("Error when getting trophies", e);
		}


//		restTemplate.put(String.format("http://localhost:8080/users/%s/trophies/%d", playerOneId, 20), null);
//		restTemplate.put(String.format("http://localhost:8080/users/%s/trophies/%d", playerTwoId, 20), null);
//		try {
//			webClientBuilder.build().put().uri(new URI(String.format("http://localhost:8080/users/%s/trophies/%d", playerOneId, 20))).retrieve();
//			webClientBuilder.build().put().uri(new URI(String.format("http://localhost:8080/users/%s/trophies/%d", playerTwoId, 20))).retrieve();
//		} catch (URISyntaxException e) {
//			logger.error("Error when sending trophies", e);
//		}
	}

	@Override
	public void run() {
		long time = getTime();
		boolean cont = true;

		sendPreGameMessage();

		while (getTime() - time > PRE_GAME_TIME) {
			nap(1000);
		}

		sendStartGameMessage();
		nap(3000);

		time = getTime();

		while (cont) {
			boolean someoneDed = game.clockCycle();
			boolean bothConnected = areBothConnected();

			sendInGameMessage("", Math.toIntExact(getTime() - time));

			nap(1000 / 60);

			cont = getTime() - time < IN_GAME_TIME &&
					someoneDed && bothConnected;
		}

		time = getTime();

		while (areBothConnected() && getTime() - time < POST_GAME_TIME) {
			sendPostGameMessage(Math.toIntExact(getTime() - time));
			nap(1000);
		}

		closeBothPlayersConnections();
		pool.remove(this);
	}
}
