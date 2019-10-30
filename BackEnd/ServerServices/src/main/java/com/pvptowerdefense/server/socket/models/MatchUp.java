package com.pvptowerdefense.server.socket.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class MatchUp implements Runnable {
	private static Logger logger =
			LoggerFactory.getLogger(MatchUp.class.getName());

	private static final int MAX_T = 10;
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_T);

	private static final int PRE_GAME_TIME = 30000;
	private static final int IN_GAME_TIME = 600000;
	private static final int POST_GAME_TIME = 600000;

	private Session playerOneSession;
	private String playerOneId;
	private Session playerTwoSession;
	private String playerTwoId;

	private Game game;

	private SocketMessage socketMessage;

	public MatchUp(String playerOneId, Session playerOneSession,
	               String playerTwoId, Session playerTwoSession) {
		this.playerOneSession = playerOneSession;
		this.playerTwoSession = playerTwoSession;

		game = new Game(playerOneId, playerTwoId);
		socketMessage = new SocketMessage(playerOneId, playerTwoId);

		pool.execute(this);
	}

	public static ThreadPoolExecutor getPool() {
		return pool;
	}

	private void sendInPlayCards() {
		logger.info("sending cards");
		sendMessage(game.getCards());
	}

	public void handleMessage(Session session, String message) {
		logger.info("handling message");
		PlayedCard card = Messages.convertJsonToCard(message);

		if (card != null) {
			game.addCard(card);
			session.getAsyncRemote().sendText(Messages.cardAdded());
		}
	}

	private void gameOver(String winner) {
		if (winner == null) {
			playerOneSession.getAsyncRemote().sendText(Messages.gameLoss());
			playerTwoSession.getAsyncRemote().sendText(Messages.gameLoss());
		}
		else if (winner.equals(playerOneId)) {
			playerOneSession.getAsyncRemote().sendText(Messages.gameWin());
			playerTwoSession.getAsyncRemote().sendText(Messages.gameLoss());
		}
		else {
			playerOneSession.getAsyncRemote().sendText(Messages.gameLoss());
			playerTwoSession.getAsyncRemote().sendText(Messages.gameWin());
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

	private void nap(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception ignore) {
		}
	}

	private void sendMessage(Object o) {
		CompletableFuture.runAsync(() -> {
			Future<Void> deliveryProgress1 =
					playerOneSession.getAsyncRemote().sendText(Messages.convertToJson(o));
			Future<Void> deliveryProgress2 =
					playerTwoSession.getAsyncRemote().sendText(Messages.convertToJson(o));

			deliveryProgress1.isDone();
			deliveryProgress2.isDone();
		});
	}

	private void sendPreGameMessage() {
		logger.info("sending pre-game message");

		socketMessage.setGameState("pre-game");
		socketMessage.setServerMessage("");
		sendMessage(socketMessage);

	}

	private void sendStartGameMessage() {
		logger.info("sending starting-game message");

		socketMessage.setGameState("starting-game");
		socketMessage.setServerMessage("starting game in 3 seconds");
		sendMessage(socketMessage);
	}

	private void sendInGameMessage(String message, int time) {
		logger.info("sending in-game message");

		socketMessage.setGameState("in-game");
		socketMessage.setServerMessage(message);
		socketMessage.setPlayedCards(game.getCards());
//		socketMessage.setTurnState(game.getTurnState());
		socketMessage.setCurrentTime(time);
		sendMessage(socketMessage);
	}

	private void sendPostGameMessage(int time) {
		logger.info("sending in-game message");

		socketMessage.setGameState("in-game");
		socketMessage.setServerMessage("");
		socketMessage.setPlayedCards(game.getCards());
		socketMessage.setTurnState("");
		socketMessage.setWinner(game.getWinner());
		socketMessage.setCurrentTime(time);
		sendMessage(socketMessage);
	}

	@Override
	public void run() {
		sendPreGameMessage();
		nap(PRE_GAME_TIME);

		long time;
		boolean cont = true;

		sendStartGameMessage();
		nap(3000);
		time = new Date().getTime();

		while (cont) {
			boolean someoneDed = game.clockCycle();
			boolean bothConnected = areBothConnected();

			sendInGameMessage("", Math.toIntExact(new Date().getTime() - time));

			nap(1000 / 60);

			cont = new Date().getTime() - time < IN_GAME_TIME &&
					someoneDed && bothConnected;
		}

		time = new Date().getTime();

		while (areBothConnected() && new Date().getTime() - time < POST_GAME_TIME) {
			sendPostGameMessage(Math.toIntExact(new Date().getTime() - time));
			nap(1000);
		}

		pool.remove(this);
	}
}
