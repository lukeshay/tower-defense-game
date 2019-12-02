package com.pvptowerdefense.server.socket.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Socket message.
 */
public class SocketMessage implements Cloneable {
	private String playerOneId;
	private String playerTwoId;

	private int playerOneMana;
	private int playerTwoMana;

	private int playerOneTrophies;
	private int playerTwoTrophies;

	private String winner;
	private String gameState;
	private String turnState;

	private long currentTime;

	private List<PlayedCard> playedCards;

	private String serverMessage;

	/**
	 * Instantiates a new Socket message.
	 *
	 * @param playerOneId the player one id
	 * @param playerTwoId the player two id
	 */
	SocketMessage(String playerOneId, String playerTwoId) {
		this.playerOneId = playerOneId;
		this.playerTwoId = playerTwoId;

		playerOneMana = 0;
		playerTwoMana = 0;

		winner = "";
		gameState = "";
		turnState = "";

		currentTime = 0;

		playedCards = new ArrayList<>();

		serverMessage = "";
	}

	/**
	 * Gets player one id.
	 *
	 * @return the player one id
	 */
	public String getPlayerOneId() {
		return playerOneId;
	}

	/**
	 * Gets player two id.
	 *
	 * @return the player two id
	 */
	public String getPlayerTwoId() {
		return playerTwoId;
	}

	/**
	 * Sets player one mana.
	 *
	 * @param playerOneMana the player one mana
	 */
	void setPlayerOneMana(int playerOneMana) {
		this.playerOneMana = playerOneMana;
	}

	/**
	 * Sets player two mana.
	 *
	 * @param playerTwoMana the player two mana
	 */
	void setPlayerTwoMana(int playerTwoMana) {
		this.playerTwoMana = playerTwoMana;
	}

	/**
	 * Sets player one trophies.
	 *
	 * @param playerOneTrophies the player one trophies
	 */
	void setPlayerOneTrophies(int playerOneTrophies) {
		this.playerOneTrophies = playerOneTrophies;
	}

	/**
	 * Sets player two trophies.
	 *
	 * @param playerTwoTrophies the player two trophies
	 */
	void setPlayerTwoTrophies(int playerTwoTrophies) {
		this.playerTwoTrophies = playerTwoTrophies;
	}

	/**
	 * Gets winner.
	 *
	 * @return the winner
	 */
	public String getWinner() {
		return winner;
	}

	/**
	 * Sets winner.
	 *
	 * @param winner the winner
	 */
	void setWinner(String winner) {
		this.winner = winner;
	}

	/**
	 * Gets game state.
	 *
	 * @return the game state
	 */
	public String getGameState() {
		return gameState;
	}

	/**
	 * Sets game state.
	 *
	 * @param gameState the game state
	 */
	void setGameState(String gameState) {
		this.gameState = gameState;
	}

	/**
	 * Sets turn state.
	 *
	 * @param turnState the turn state
	 */
	void setTurnState(String turnState) {
		this.turnState = turnState;
	}

	/**
	 * Gets current time.
	 *
	 * @return the current time
	 */
	public long getCurrentTime() {
		return currentTime;
	}

	/**
	 * Sets current time.
	 *
	 * @param currentTime the current time
	 */
	void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	/**
	 * Sets played cards.
	 *
	 * @param playedCards the played cards
	 */
	void setPlayedCards(List<PlayedCard> playedCards) {
		this.playedCards = playedCards;
	}

	/**
	 * Gets server message.
	 *
	 * @return the server message
	 */
	public String getServerMessage() {
		return serverMessage;
	}

	/**
	 * Sets server message.
	 *
	 * @param serverMessage the server message
	 */
	void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	@Override
	public SocketMessage clone() throws CloneNotSupportedException {
		return (SocketMessage) super.clone();
	}

}
