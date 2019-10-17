package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;
import java.util.Date;

public class Game implements Runnable {
	// will need some sort of map
	// will need to keep track of time
	// will be started from the match up

	private Session playerOneSession;
	private String playerOneId;
	private Session playerTwoSession;
	private String playerTwoId;

	private Map map;

	public Game(String playerOneId, Session playerOneSession,
	            String playerTwoId, Session playerTwoSession) {
		this.playerOneSession = playerOneSession;
		this.playerTwoSession = playerTwoSession;

		map = new Map(playerOneId, playerTwoId);
	}

	private void sendInPlayCards() {
		playerOneSession.getAsyncRemote().sendBinary(Messages.serializeToByteBuffer(map.getCards()));
		playerTwoSession.getAsyncRemote().sendBinary(Messages.serializeToByteBuffer(map.getCards()));
	}

	void handleMessage(Session session, byte[] message) {
		Object obj = Messages.deserialize(message);

		if (obj instanceof PlayedCard) {
			map.addCard((PlayedCard) obj);
		}
		else if (obj instanceof String) {
			playerOneSession.getAsyncRemote().sendBinary(Messages.serializeToByteBuffer(obj));
			playerTwoSession.getAsyncRemote().sendBinary(Messages.serializeToByteBuffer(obj));
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

	private boolean checkBothConnected() {
		return playerOneSession.isOpen() && playerTwoSession.isOpen();
	}

	@Override
	public void run() {
		long startTime = new Date().getTime();
		boolean cont = true;

		while (cont) {
			boolean someoneDed = map.clockCycle();
			boolean bothConnected = checkBothConnected();

			sendInPlayCards();

			cont = new Date().getTime() - startTime < 100000 &&
					someoneDed &&
					bothConnected;

			try {
				Thread.sleep(1000 / 60);
			}
			catch (InterruptedException ignore) {
			}
		}
		String winner = map.getWinner();
		gameOver(winner);
	}
}
