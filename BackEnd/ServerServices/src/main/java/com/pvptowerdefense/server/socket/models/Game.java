package com.pvptowerdefense.server.socket.models;

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
			Future<Void> deliveryProgress1 =
					playerOneSession.getAsyncRemote().sendText(Messages.convertToJson(map.getCards()));
			Future<Void> deliveryProgress2 =
					playerTwoSession.getAsyncRemote().sendText(Messages.convertToJson(map.getCards()));
			deliveryProgress1.isDone();
			deliveryProgress2.isDone();
		});
	}

	void handleMessage(Session session, String message) {
		PlayedCard card = Messages.convertJsonToCard(message);
		if (card != null) {
			map.addCard(card);
			session.getAsyncRemote().sendText(Messages.cardAdded().toString());
		}
	}

	private void gameOver(String winner) {
		sendInPlayCards();
		sendResultMessages(winner);
	}

	private void sendResultMessages(String winner) {
		if (winner == null) {
			playerOneSession.getAsyncRemote().sendText(Messages.gameLoss().toString());
			playerTwoSession.getAsyncRemote().sendText(Messages.gameLoss().toString());
		}
		else if (winner.equals(playerOneId)) {
			playerOneSession.getAsyncRemote().sendText(Messages.gameWin().toString());
			playerTwoSession.getAsyncRemote().sendText(Messages.gameLoss().toString());
		}
		else {
			playerOneSession.getAsyncRemote().sendText(Messages.gameLoss().toString());
			playerTwoSession.getAsyncRemote().sendText(Messages.gameWin().toString());
		}

		try {
			playerOneSession.close();
			playerTwoSession.close();
		} catch (IOException ignore) {
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
					someoneDed && bothConnected;

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
