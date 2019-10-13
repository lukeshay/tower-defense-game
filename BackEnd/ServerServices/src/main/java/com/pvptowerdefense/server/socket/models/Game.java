package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;
import java.util.Date;

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

		map = new Map();
	}

	private void sendMap() {
		playerOne.getAsyncRemote().sendObject(map);
		playerTwo.getAsyncRemote().sendObject(map);
	}

	void handleMessage(Session session, String message) {
		// parse message and add to map
	}

	private void gameOver() {
		playerOne.getAsyncRemote().sendObject(Messages.gameWin());
		playerTwo.getAsyncRemote().sendObject(Messages.gameLoss());
	}

	@Override
	public void run() {
		long startTime = new Date().getTime();
		boolean cont = true;

		while (cont) {
			try {
				Thread.sleep(1);
			} catch (Exception ignore) {}

			sendMap();

			cont = checkForLoss();

			if (new Date().getTime() - startTime > 10000) {
				cont = false;
			}
		}

		gameOver();
	}

	private boolean checkForLoss() {

		return true;
	}
}
