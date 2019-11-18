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
	public SocketMessage(String playerOneId, String playerTwoId) {
		this.playerOneId = playerOneId;
		this.playerTwoId = playerTwoId;

		this.playerOneMana = 0;
		this.playerTwoMana = 0;

		this.winner = "";
		this.gameState = "";
		this.turnState = "";

		this.currentTime = 0;

		this.playedCards = new ArrayList<>();

		this.serverMessage = "";
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

	public int getPlayerOneTrophies() {
		return playerOneTrophies;
	}

	public void setPlayerOneTrophies(int playerOneTrophies) {
		this.playerOneTrophies = playerOneTrophies;
	}

	public int getPlayerTwoTrophies() {
		return playerTwoTrophies;
	}

	public void setPlayerTwoTrophies(int playerTwoTrophies) {
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
	 * Gets turn state.
	 *
	 * @return the turn state
	 */
	public String getTurnState() {
		return turnState;
	}

	/**
	 * Sets turn state.
	 *
	 * @param turnState the turn state
	 */
	public void setTurnState(String turnState) {
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
	public void setCurrentTime(long currentTime) {
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
	public void setServerMessage(String serverMessage) {
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
