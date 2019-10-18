package com.pvptowerdefense.server.socket.models;

import rx.Completable;
import shared.PlayedCard;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Game implements Runnable {
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
		CompletableFuture.runAsync(() -> {
			try {
				Future<Void> deliveryProgress1 =
						playerOneSession.getAsyncRemote().sendBinary(Messages.serializeToByteBuffer(map.getCards()));
				Future<Void> deliveryProgress2 =
						playerTwoSession.getAsyncRemote().sendBinary(Messages.serializeToByteBuffer(map.getCards()));
				deliveryProgress1.isDone();
				deliveryProgress2.isDone();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	void handleMessage(Session session, byte[] message) {
		Object obj = Messages.deserialize(message);

		map.addCard((PlayedCard) obj);
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
