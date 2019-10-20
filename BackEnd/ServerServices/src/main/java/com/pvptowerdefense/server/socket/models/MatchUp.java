package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;
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

		game = new Game(playerOneId, playerOneSession, playerTwoId,
				playerTwoSession);

		pool.execute(game);
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
	 * Gets game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Takes in the session you currently have and returns the other one.
	 *
	 * @param session The session you have
	 * @return The other session
	 */
	public Session getOtherSession(Session session) {
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
	 * @param message the message
	 */
	public void sendMessage(Session session, String message) {
		game.handleMessage(session, message);
	}

	public static ThreadPoolExecutor getPool() {
		return pool;
	}
}

