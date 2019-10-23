package com.pvptowerdefense.server.socket.models;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class MatchUp implements Runnable {
	private static final int MAX_T = 10;
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_T);

	private Session playerOneSession;
	private String playerOneId;
	private Session playerTwoSession;
	private String playerTwoId;

	private Map map;

	public MatchUp(String playerOneId, Session playerOneSession,
	               String playerTwoId, Session playerTwoSession) {
		this.playerOneSession = playerOneSession;
		this.playerTwoSession = playerTwoSession;

		map = new Map(playerOneId, playerTwoId);

		pool.execute(this);
	}

	public static ThreadPoolExecutor getPool() {
		return pool;
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

	public void handleMessage(Session session, String message) {
		PlayedCard card = Messages.convertJsonToCard(message);
		if (card != null) {
			map.addCard(card);
			session.getAsyncRemote().sendText(Messages.cardAdded().toString());
		}
	}

	private void gameOver(String winner) {
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

	private boolean areBothConnected() {
		return playerOneSession.isOpen() && playerTwoSession.isOpen();
	}

	public Session getPlayerOneSession() {
		return playerOneSession;
	}

	public Session getPlayerTwoSession() {
		return playerTwoSession;
	}

	@Override
	public void run() {
		long startTime = new Date().getTime();
		boolean cont = true;

		while (cont) {
			boolean someoneDed = map.clockCycle();
			boolean bothConnected = areBothConnected();

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
