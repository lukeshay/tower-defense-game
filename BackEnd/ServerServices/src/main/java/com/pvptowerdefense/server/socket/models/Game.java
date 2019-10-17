package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;
import java.util.Date;

public class Game implements Runnable {
	// will need some sort of map
	// will need to keep track of time
	// will be started from the match up

	private String gameState;

	private Session playerOne;
	private Session playerTwo;

	private Map map;

	public Game(Session playerOne, Session playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;

		map = new Map();

		gameState = "waiting";
	}

	private void sendMap() {
//		playerOne.getAsyncRemote().sendObject(map.getCards());
//		playerTwo.getAsyncRemote().sendObject(map.getCards());
	}

	void handleMessage(Session session, String message) {
		if (session == null && message.equals("STOP")) {
			gameState = "STOP";
		}
	}

	private void gameOver() {
		playerOne.getAsyncRemote().sendObject(Messages.gameWin());
		playerTwo.getAsyncRemote().sendObject(Messages.gameLoss());
	}

	@Override
	public void run() {
		long startTime = new Date().getTime();
		boolean cont = true;

		gameState = "running";

		while (cont) {
			try {
				Thread.sleep(1);
			} catch (Exception ignore) {}

			// map.clockCycle();
			sendMap();

			cont = !checkForLoss();

			if (new Date().getTime() - startTime > 100000 || gameState.equals(
					"STOP")) {
				cont = false;
			}

			try {
				Thread.sleep(1000 / 60);
			}
			catch (InterruptedException ignore) {}
		}

		gameOver();
	}

	private boolean checkForLoss() {
//		System.out.println("Checking for loss");
		return false;
	}
}
