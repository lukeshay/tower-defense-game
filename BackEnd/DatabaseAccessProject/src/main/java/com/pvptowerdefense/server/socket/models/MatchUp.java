package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;

public class MatchUp {
	private String playerOneId;
	private Session playerOneSession;

	private String playerTwoId;
	private Session playerTwoSession;

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

	public Session getOtherSession(Session session) {
		if (session.equals(getPlayerOneSession())) {
			return getPlayerTwoSession();
		}
		else {
			return getPlayerOneSession();
		}
	}
}

