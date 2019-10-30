package com.pvptowerdefense.server.socket.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Socket message.
 */
public class SocketMessage {
	private String playerOneId;
	private String playerTwoId;

	private int playerOneMana;
	private int playerTwoMana;

	private String winner;
	private String gameState;

	private int currentTime;

	private List<PlayedCard> playedCards;

	/**
	 * Instantiates a new Socket message.
	 *
	 * @param playerOneId the player one id
	 * @param playerTwoId the player two id
	 */
	public SocketMessage(String playerOneId, String playerTwoId) {
		this.playerOneId = playerOneId;
		this.playerTwoId = playerTwoId;

		this.playerOneMana = 0;
		this.playerTwoMana = 0;

		this.winner = "";
		this.gameState = "";

		this.currentTime = 0;

		this.playedCards = new ArrayList<>();
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
	 * Sets player one id.
	 *
	 * @param playerOneId the player one id
	 */
	public void setPlayerOneId(String playerOneId) {
		this.playerOneId = playerOneId;
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
	 * Sets player two id.
	 *
	 * @param playerTwoId the player two id
	 */
	public void setPlayerTwoId(String playerTwoId) {
		this.playerTwoId = playerTwoId;
	}

	/**
	 * Gets player one mana.
	 *
	 * @return the player one mana
	 */
	public int getPlayerOneMana() {
		return playerOneMana;
	}

	/**
	 * Sets player one mana.
	 *
	 * @param playerOneMana the player one mana
	 */
	public void setPlayerOneMana(int playerOneMana) {
		this.playerOneMana = playerOneMana;
	}

	/**
	 * Gets player two mana.
	 *
	 * @return the player two mana
	 */
	public int getPlayerTwoMana() {
		return playerTwoMana;
	}

	/**
	 * Sets player two mana.
	 *
	 * @param playerTwoMana the player two mana
	 */
	public void setPlayerTwoMana(int playerTwoMana) {
		this.playerTwoMana = playerTwoMana;
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
	public void setWinner(String winner) {
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
	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	/**
	 * Gets current time.
	 *
	 * @return the current time
	 */
	public int getCurrentTime() {
		return currentTime;
	}

	/**
	 * Sets current time.
	 *
	 * @param currentTime the current time
	 */
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	/**
	 * Gets played cards.
	 *
	 * @return the played cards
	 */
	public List<PlayedCard> getPlayedCards() {
		return playedCards;
	}

	/**
	 * Sets played cards.
	 *
	 * @param playedCards the played cards
	 */
	public void setPlayedCards(List<PlayedCard> playedCards) {
		this.playedCards = playedCards;
	}
}
