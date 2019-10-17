package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;
import java.util.Date;

public class Game implements Runnable {
	// will need some sort of map
	// will need to keep track of time
	// will be started from the match up

	private String gameState;

	private Session playerOneSession;
	private String playerOneId;
	private Session playerTwoSession;
	private String playerTwoId;

	private Map map;

	public Game(String playerOneId, Session playerOneSession,
	            String playerTwoId, Session playerTwoSession) {
		this.playerOneSession = playerOneSession;
		this.playerTwoSession = playerTwoSession;

		map = new Map();

		gameState = "waiting";
	}

	private void sendInPlayCards() {
		playerOneSession.getAsyncRemote().sendObject(map.getCards());
		playerTwoSession.getAsyncRemote().sendObject(map.getCards());
	}

	void handleMessage(Session session, byte[] message) {
		Object obj = Messages.deserialize(message);


		if (obj instanceof String && ((String) obj).equals("STOP")) {
			gameState = "STOP";
		}
		else if (obj instanceof PlayedCard) {
			map.addCard((PlayedCard) obj);
		}
	}

	private void gameOver(String winner) {
		sendInPlayCards();
		sendResultMessages(winner);
	}

	private void sendResultMessages(String winner) {
		if (winner.equals(playerOneId)) {
			playerOneSession.getAsyncRemote().sendObject(Messages.gameWin());
			playerTwoSession.getAsyncRemote().sendObject(Messages.gameLoss());
		}
		else {
			playerOneSession.getAsyncRemote().sendObject(Messages.gameLoss());
			playerTwoSession.getAsyncRemote().sendObject(Messages.gameWin());
		}
	}

	@Override
	public void run() {
		long startTime = new Date().getTime();
		boolean cont = true;

		gameState = "running";

		while (cont) {
			// cont = map.clockCycle();
			sendInPlayCards();

			if (new Date().getTime() - startTime > 100000 || gameState.equals(
					"STOP")) {
				cont = false;
			}

			try {
				Thread.sleep(1000 / 60);
			}
			catch (InterruptedException ignore) {}
		}
		// winner = map.getWinner();
		gameOver(playerOneId);
	}

	private boolean checkForLoss() {
		return false;
	}
}
