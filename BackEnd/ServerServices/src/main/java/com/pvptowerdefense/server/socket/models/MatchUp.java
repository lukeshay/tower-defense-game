package com.pvptowerdefense.server.socket.models;

import com.pvptowerdefense.server.spring.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

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
	private static WebClient.Builder webClientBuilder = WebClient.builder();
	private Session playerOneSession;
	private String playerOneId;
	private User userOne;
	private User userTwo;
	private Session playerTwoSession;
	private String playerTwoId;
	private Game game;
	private SocketMessage socketMessage;
	private List<SocketMessage> messageHistory;

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

		this.playerOneId = playerOneId;
		this.playerTwoId = playerTwoId;

		game = new Game(playerOneId, playerTwoId);
		socketMessage = new SocketMessage(playerOneId, playerTwoId);

		messageHistory = new ArrayList<>();
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

	private static User updateUserInDatabase(User userTwo) {
		return webClientBuilder.build()
				.put()
				.uri("http://localhost:8080/users")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.syncBody(userTwo)
				.retrieve()
				.bodyToMono(User.class)
				.block();
	}

	private static User getUserFromDatabase(String userId) throws URISyntaxException {
		return webClientBuilder.build()
				.get()
				.uri(new URI(String.format("http://localhost:8080/users/%s", userId)))
				.retrieve().bodyToMono(User.class).block();
	}

	/**
	 * Start match up.
	 */
	public void startMatchUp() {
		pool.execute(this);
	}

	public void stopMatchUp() {
		pool.remove(this);
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
	 * Gets socket message.
	 *
	 * @return the socket message
	 */
	public SocketMessage getSocketMessage() {
		return socketMessage;
	}

	/**
	 * Gets message history.
	 *
	 * @return the message history
	 */
	public List<SocketMessage> getMessageHistory() {
		return messageHistory;
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
	 * @return boolean boolean
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
		logger.info("handling message: " + message);

		if (message.contains("\"name\"") && message.contains("\"description\"")) {
			logger.error("adding card");
			game.addCard(Messages.convertJsonToPlayedCard(message));
			session.getAsyncRemote().sendText(Messages.cardAdded());
		}
		else {
			logger.error("sending message");
			getOtherSession(session).getAsyncRemote().sendText(message);
		}
	}

	/**
	 * Gets other session.
	 *
	 * @param session the session
	 * @return the other session
	 */
	private Session getOtherSession(Session session) {
		return session.equals(playerOneSession) ? playerTwoSession : playerOneSession;
	}

	/**
	 * Sends the given object to both players as json.
	 *
	 * @param socketMessage the message
	 */
	private void sendMessage(SocketMessage socketMessage) {
		CompletableFuture.runAsync(() -> {
			try {
				messageHistory.add(socketMessage.clone());
			} catch (CloneNotSupportedException e) {
				logger.error("error adding message to history", e);
			}

			if (playerOneSession.isOpen()) {
				playerOneSession.getAsyncRemote().sendText(Messages.convertToJson(socketMessage));
			}

			if (playerTwoSession.isOpen()) {
				playerTwoSession.getAsyncRemote().sendText(Messages.convertToJson(socketMessage));
			}
		});
	}

	/**
	 * Sets the socket message object to correct values and sends the pre
	 * game message.
	 */
	public void sendPreGameMessage() {
		logger.info("sending pre-game message");

		try {
			userOne = getUserFromDatabase(playerOneId);
			userTwo = getUserFromDatabase(playerTwoId);

			socketMessage.setPlayerOneTrophies(userOne.getTrophies());
			socketMessage.setPlayerTwoTrophies(userTwo.getTrophies());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		socketMessage.setGameState("pre-game");
		socketMessage.setServerMessage("select deck");
		sendMessage(socketMessage);

	}

	/**
	 * Sets teh socket message object to correct values and sends the start
	 * game message.
	 */
	public void sendStartGameMessage() {
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
	private void sendInGameMessage(String message, long time) {
		logger.debug("sending in-game message");

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
	public void sendPostGameMessage(long time) {
		logger.info("sending in-game message");
		String winner;

		if (!playerTwoSession.isOpen() && (game.getWinner() == null || game.getWinner().equals(""))) {
			logger.info(playerTwoId + " disconnected");
			winner = playerOneId;
		}
		else if (!playerOneSession.isOpen() && (game.getWinner() == null || game.getWinner().equals(""))) {
			logger.info(playerOneId + " disconnected");
			winner = playerTwoId;
		}
		else if (game.getWinner() == null || game.getWinner().equals("")) {
			List<PlayedCard> playerOneTowers = game.getPlayerOneCards().stream().filter(card -> card.getName().contains("tower")).collect(Collectors.toList());
			List<PlayedCard> playerTwoTowers = game.getPlayerTwoCards().stream().filter(card -> card.getName().contains("tower")).collect(Collectors.toList());

			int oneTowerHealth = playerOneTowers.stream().mapToInt(PlayedCard::getCurrentHitPoints).sum();
			int twoTowerHealth = playerTwoTowers.stream().mapToInt(PlayedCard::getCurrentHitPoints).sum();

			winner = oneTowerHealth > twoTowerHealth ? playerOneId : playerTwoId;
		}
		else {
			winner = game.getWinner();
		}

		sendPostGameMessage(time, winner);
	}

	/**
	 * Send post game message.
	 *
	 * @param time   the time
	 * @param winner the winner
	 */
	public void sendPostGameMessage(long time, String winner) {
		logger.info("sending post-game message");
		socketMessage.setGameState("post-game");
		socketMessage.setWinner(winner);
		socketMessage.setServerMessage("the game is over");
		socketMessage.setPlayedCards(game.getCards());
		socketMessage.setTurnState("");
		socketMessage.setCurrentTime(time);

		if (userOne == null || userTwo == null) {
			throw new NullPointerException("One of the users was not received from the server.");
		}

		if (userOne.getPhoneId().equals(socketMessage.getWinner())) {
			userOne.setTrophies(userOne.getTrophies() + 10);
			if (userTwo.getTrophies() < 5) {
				userTwo.setTrophies(0);
			}
			else {
				userTwo.setTrophies(userTwo.getTrophies() - 5);
			}
		}
		else {
			userTwo.setTrophies(userTwo.getTrophies() + 10);
			if (userOne.getTrophies() < 5) {
				userOne.setTrophies(0);
			}
			else {
				userOne.setTrophies(userOne.getTrophies() - 5);
			}
		}

		socketMessage.setPlayerOneTrophies(userOne.getTrophies());
		socketMessage.setPlayerTwoTrophies(userTwo.getTrophies());

		sendMessage(socketMessage);

		try {
			userOne = updateUserInDatabase(userOne);
			userTwo = updateUserInDatabase(userTwo);

		} catch (Exception e) {
			logger.error("Error when getting trophies", e);
		}
	}

	@Override
	public void run() {
		long time = getTime();
		boolean cont = true;

		sendPreGameMessage();

		while (getTime() - time < PRE_GAME_TIME) {
			nap(1000);
		}

		sendStartGameMessage();
		nap(3000);

		time = getTime();

		while (cont) {
			if (!areBothConnected()) {
				if (!getPlayerOneSession().isOpen() && !getPlayerTwoSession().isOpen()) {
					break;
				}

				sendPostGameMessage(time, getPlayerOneSession().isOpen() ? playerOneId : playerTwoId);
				break;
			}

			boolean someoneDed = game.clockCycle();

			sendInGameMessage("", getTime() - time);

			nap(1000 / 60);

			cont = getTime() - time < IN_GAME_TIME && someoneDed;
		}

		time = getTime();

		while (areBothConnected() && getTime() - time < POST_GAME_TIME) {
			sendPostGameMessage(getTime() - time);
			nap(1000);
		}

		closeBothPlayersConnections();
		pool.remove(this);
	}
}
