package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;

/**
 * Object to hold current matchups. The matchups are between two users. Their
 * sessions and id's are stored. The id can be used to make calls to the
 * database if necessary.
 */
public class MatchUp {
	private String playerOneId;
	private Session playerOneSession;

	private String playerTwoId;
	private Session playerTwoSession;

	/**
	 * Constructor for a matchup.
	 *
	 * @param playerOneId Id of the first player
	 * @param playerOneSession Session of the first player
	 * @param playerTwoId Id of the second player
	 * @param playerTwoSession Session of the second player
	 */
	public MatchUp(String playerOneId, Session playerOneSession, String playerTwoId, Session playerTwoSession) {
		this.playerOneId = playerOneId;
		this.playerOneSession = playerOneSession;
		this.playerTwoId = playerTwoId;
		this.playerTwoSession = playerTwoSession;
	}

	public String getPlayerOneId() {
		return playerOneId;
	}

	public void setPlayerOneId(String playerOneId) {
		this.playerOneId = playerOneId;
	}

	public Session getPlayerOneSession() {
		return playerOneSession;
	}

	public void setPlayerOneSession(Session playerOneSession) {
		this.playerOneSession = playerOneSession;
	}

	public String getPlayerTwoId() {
		return playerTwoId;
	}

	public void setPlayerTwoId(String playerTwoId) {
		this.playerTwoId = playerTwoId;
	}

	public Session getPlayerTwoSession() {
		return playerTwoSession;
	}

	public void setPlayerTwoSession(Session playerTwoSession) {
		this.playerTwoSession = playerTwoSession;
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
}

