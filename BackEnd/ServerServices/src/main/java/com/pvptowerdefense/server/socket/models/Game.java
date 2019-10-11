package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;

public class Game implements Runnable {
	// will need some sort of map
	// will need to keep track of time
	// will be started from the match up

	private Session playerOne;
	private Session playerTwo;

	private Map map;

	public Game(Session playerOne, Session playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	private void sendMap() {
		playerOne.getAsyncRemote().sendObject(map);
		playerTwo.getAsyncRemote().sendObject(map);
	}

	public void handleMessage(Session session, String message) {
		// parse message and add to map
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1);
			} catch (Exception ignore) {}

			sendMap();
		}
	}
}
