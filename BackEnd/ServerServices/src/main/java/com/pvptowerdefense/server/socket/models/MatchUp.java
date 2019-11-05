package com.pvptowerdefense.server.socket.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * The type Match up.
 */
public class MatchUp implements Runnable {
	private static Logger logger =
			LoggerFactory.getLogger(MatchUp.class.getName());

	private static final int MAX_T = 10;
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_T);

	private static final int PRE_GAME_TIME = 30000;
	private static final int IN_GAME_TIME = 300000;
	private static final int POST_GAME_TIME = 300000;

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

		if (card != null && game.isValidCard(card)) {
			game.addCard(card);
			session.getAsyncRemote().sendText(Messages.cardAdded());
		}
		else {
			session.getAsyncRemote().sendText(Messages.cardNotAdded("Invalid location or user."));
		}
	}

	/**
	 * Sends the given object to both players as json.
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
	 * @param time the game time in milliseconds
	 */
	private void sendInGameMessage(String message, int time) {
		logger.info("sending in-game message");

		socketMessage.setGameState("in-game");
		socketMessage.setServerMessage(message);
		socketMessage.setPlayedCards(game.getCards());
//		socketMessage.setTurnState(game.getTurnState());
		socketMessage.setCurrentTime(time);
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
	}

	/**
	 * Gets the time in milliseconds.
	 * @return the current time in milliseconds
	 */
	private static long getTime() {
		return new Date().getTime();
	}

	@Override
	public void run() {
		long time = getTime();
		boolean cont = true;

		sendPreGameMessage();

		while(getTime() - time > PRE_GAME_TIME) {
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
