package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Object to hold current matchups. The matchups are between two users. Their
 * sessions and id's are stored. The id can be used to make calls to the
 * database if necessary.
 */
public class MatchUp {
	private static final int MAX_T = 10;

	private Game game;
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_T);

	private String playerOneId;
	private Session playerOneSession;

	private String playerTwoId;
	private Session playerTwoSession;

	/**
	 * Constructor for a matchup.
	 *
	 * @param playerOneId      Id of the first player
	 * @param playerOneSession Session of the first player
	 * @param playerTwoId      Id of the second player
	 * @param playerTwoSession Session of the second player
	 */
	public MatchUp(String playerOneId, Session playerOneSession, String playerTwoId, Session playerTwoSession) {
		this.playerOneId = playerOneId;
		this.playerOneSession = playerOneSession;
		this.playerTwoId = playerTwoId;
		this.playerTwoSession = playerTwoSession;

		game = new Game(playerOneSession, playerTwoSession);

		pool.execute(game);
	}

	/**
	 * Gets player one id.
	 *
	 * @return the player one id
	 */
	public String getPlayerOneId() {
		return playerOneId;
	}

	/**
	 * Sets player one id.
	 *
	 * @param playerOneId the player one id
	 */
	public void setPlayerOneId(String playerOneId) {
		this.playerOneId = playerOneId;
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
	 * Sets player one session.
	 *
	 * @param playerOneSession the player one session
	 */
	public void setPlayerOneSession(Session playerOneSession) {
		this.playerOneSession = playerOneSession;
	}

	/**
	 * Gets player two id.
	 *
	 * @return the player two id
	 */
	public String getPlayerTwoId() {
		return playerTwoId;
	}

	/**
	 * Sets player two id.
	 *
	 * @param playerTwoId the player two id
	 */
	public void setPlayerTwoId(String playerTwoId) {
		this.playerTwoId = playerTwoId;
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
	 * Sets player two session.
	 *
	 * @param playerTwoSession the player two session
	 */
	public void setPlayerTwoSession(Session playerTwoSession) {
		this.playerTwoSession = playerTwoSession;
	}

	/**
	 * Gets game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Sets game.
	 *
	 * @param game the game
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Takes in the session you currently have and returns the other one.
	 *
	 * @param session The session you have
	 * @return The other session
	 */
	private Session getOtherSession(Session session) {
		if (session.equals(getPlayerOneSession())) {
			return getPlayerTwoSession();
		}
		else {
			return getPlayerOneSession();
		}
	}

	/**
	 * Send message.
	 *
	 * @param session the session
	 * @param bytes the message
	 */
	public void sendMessage(Session session, byte[] bytes) {
//		game.handleMessage(session, message);

		// this is temporary for testing
		getOtherSession(session).getAsyncRemote().sendBinary(ByteBuffer.wrap(bytes));
	}

	public void endGame() {
		game.handleMessage(null, "STOP");
	}

	public static ThreadPoolExecutor getPool() {
		return pool;
	}
}

