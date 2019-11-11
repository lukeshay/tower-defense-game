package com.pvptowerdefense.server.socket.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Game class type
 * Used to contain the game logic and the list of cards being played for each player
 */
public class Game {
	private static Logger logger =
			LoggerFactory.getLogger(Game.class.getName());

	private List<PlayedCard> playerOneCards;
	private List<PlayedCard> playerTwoCards;
	private int playerOneMana;
	private int playerTwoMana;
	private String player1;
	private String player2;
	private boolean gameState;
	private String winner;
	private int counter;

	private static final int MAX_X = 1920;
	private static final int MAX_Y = 1080;
	private static final int TOWER1_Y = MAX_Y / 2 - 150;
	private static final int TOWER2_Y = MAX_Y / 4 - 150;
	private static final int TOWER3_Y = 3 * MAX_Y / 4 - 150;
	private static final int TOWER1_X = 50;
	private static final int TOWER2_X = 150;
	private static final int TOWER3_X = 150;


	/**
	 * Constructs a new game given the 2 player's IDs
	 *
	 * @param userId1 - the userId for player 1
	 * @param userId2 - the userId for player 2
	 */
	public Game(String userId1, String userId2) {
		playerOneCards = new ArrayList<PlayedCard>();
		playerTwoCards = new ArrayList<PlayedCard>();
		player1 = userId1;
		player2 = userId2;
		gameState = true;
		counter = 0;
		playerOneMana = 0;
		playerTwoMana = 0;

		playerOneCards.add(makeTower(TOWER1_X, TOWER1_Y, userId1, "tower1"));
		playerOneCards.add(makeTower(TOWER2_X, TOWER2_Y, userId1, "tower2"));
		playerOneCards.add(makeTower(TOWER3_X, TOWER3_Y, userId1, "tower3"));

		playerTwoCards.add(makeTower(MAX_X - TOWER1_X - 250, TOWER1_Y, userId2, "tower4"));
		playerTwoCards.add(makeTower(MAX_X - TOWER2_X - 250, TOWER2_Y, userId2, "tower5"));
		playerTwoCards.add(makeTower(MAX_X - TOWER3_X - 250, TOWER3_Y, userId2, "tower6"));
	}

	/**
	 * Gets the list of all the played cards for each user
	 *
	 * @return list of played cards for each user
	 */
	public List<PlayedCard> getCards() {
		List<PlayedCard> total = new ArrayList<>();
		total.addAll(playerOneCards);
		total.addAll(playerTwoCards);
		return total;
	}


	/**
	 * Adds a card to the specified players list of played cards
	 *
	 * @param card - card to be added
	 */
	public void addCard(PlayedCard card) {
		if (card.getPlayer().equals(player1)) {
			playerOneCards.add(card);
		}
		else if (card.getPlayer().equals(player2)) {
			playerTwoCards.add(card);
		}
	}

	/**
	 * Gets player1's user Id
	 *
	 * @return the userId
	 */
	public String getPlayer1() {
		return player1;
	}

	/**
	 * Sets player1's userId
	 *
	 * @param player1 - new userId
	 */
	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	/**
	 * Gets player2's user Id
	 *
	 * @return the userId
	 */
	public String getPlayer2() {
		return player2;
	}

	/**
	 * Sets player2's userId
	 *
	 * @param player2 - new userId
	 */
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public int getPlayerOneMana() {
		return playerOneMana;
	}

	public void setPlayerOneMana(int playerOneMana) {
		this.playerOneMana = playerOneMana;
	}

	public int getPlayerTwoMana() {
		return playerTwoMana;
	}

	public void setPlayerTwoMana(int playerTwoMana) {
		this.playerTwoMana = playerTwoMana;
	}

	/**
	 * Gets the current game state
	 *
	 * @return the game state
	 */
	public boolean isGameState() {
		return gameState;
	}

	/**
	 * Sets the current game state to the desired value
	 *
	 * @param gameState - new game state value
	 */
	public void setGameState(boolean gameState) {
		this.gameState = gameState;
	}

	/**
	 * Gets the winner of the game
	 *
	 * @return the winner's userId
	 */
	public String getWinner() {
		return winner;
	}

	public String getTurnState() {
		return counter % 60 == 0 ? "attack" : "move";
	}

	/**
	 * Method that is called 60 times a second to move and attack the cards
	 *
	 * @return the game state to check if it should end
	 */
	public boolean clockCycle() {
		if (!gameState) throw new IllegalStateException("Game is over.");

		attackOrMove(playerOneCards, playerTwoCards, 1);
		attackOrMove(playerTwoCards, playerOneCards, -1);

		if (counter % 60 == 0) {
			playerOneMana = playerOneMana < 5 ? playerOneMana + 1: 5;
			playerTwoMana = playerTwoMana > 5 ? playerTwoMana + 1: 5;
		}

		for (ListIterator<PlayedCard> cards = playerOneCards.listIterator(); cards.hasNext();) {
			PlayedCard card = cards.next();
			if (card.getHitPoints() <= 0) {
			    if (card.getName().equals("tower2")) {
			        gameState = false;
			        winner = player2;
                }
			    trophyUpdate(winner);
				cards.remove();
			}
		}

		for (ListIterator<PlayedCard> cards = playerTwoCards.listIterator(); cards.hasNext();) {
			PlayedCard card = cards.next();
			if (card.getHitPoints() <= 0) {
                if (card.getName().equals("tower5")) {
                    gameState = false;
                    winner = player1;
                }
				trophyUpdate(winner);
				cards.remove();
			}
		}

		counter++;
		return gameState;
	}

	/**
	 * Runs the attack or move cycle. Attacks once a second and moves if it is not in range.
	 *
	 * @param actionCards the cards attacking or moving
	 * @param otherCards the cards being check against
	 * @param direction the direction the card should move
	 */
	private void attackOrMove(List<PlayedCard> actionCards, List<PlayedCard> otherCards, int direction) {
		for (PlayedCard actionCard : actionCards) {
			actionCard.setAttacking(false);
			actionCard.setCardAttackingDistance(Integer.MAX_VALUE);

			for (PlayedCard otherCard : otherCards) {
				double distance = distance(actionCard, otherCard);

				if (distance <= actionCard.getRange() && distance < actionCard.getCardAttackingDistance()) {
					actionCard.setCardAttacking(otherCard.getName());
					actionCard.setCardAttackingDistance((int) distance);
					actionCard.setAttacking(true);
				}
			}

			if (actionCard.isAttacking() && counter % 60 == 0) {
				PlayedCard attacked = otherCards.stream()
						.filter(card -> actionCard.getCardAttacking().equals(card.getName()) && actionCard.getCardAttackingDistance() == (int) distance(actionCard, card))
						.findFirst()
						.get();

				attacked.setHitPoints(attacked.getHitPoints() - actionCard.getDamage());
			}
			else if (!actionCard.isAttacking()) {
					int newXValue = actionCard.getxValue() + (actionCard.getSpeed() * direction);
					newXValue = direction == 1 ? Math.min(newXValue, MAX_X - 1) : Math.max(newXValue, 0);
					actionCard.setxValue(newXValue);
			}
		}
	}

    /**
	 * Helper method to make the towers at the start of the game
	 *
	 * @param xValue    - x value for the turret
	 * @param yValue    - y value for the turret
	 * @param player    - which player the turret is for
	 * @param towerName - the tower's name
	 * @return the created played card
	 */
	private PlayedCard makeTower(int xValue, int yValue, String player, String towerName) {
		return new PlayedCard(towerName, "tower", 0, 1, 100, 0, "UNIT", 300, xValue, yValue, player);
	}

	/**
	 * Helper method to find the distance between 2 cards
	 *
	 * @param card1 - first card
	 * @param card2 - second card
	 * @return - distance between the first and second card
	 */
	private double distance(PlayedCard card1, PlayedCard card2) {
		int x = card1.getxValue() - card2.getxValue();
		int y = card1.getyValue() - card2.getyValue();
		int xSquare = x * x;
		int ySquare = y * y;
		return Math.sqrt(xSquare + ySquare);
	}

	void trophyUpdate(String winner){
//		if(winner.equals(player1)){
//			String uri =  "http://coms-309-ss-5.misc.iastate.edu:3306/users/{userId}/trophies";
//			String uri2 = "http://coms-309-ss-5.misc.iastate.edu:3306/users/{userId}/trophies/{trophies}";
//			Map<String, String> params = new HashMap<String, String>();
//			Map<String, String> params2 = new HashMap<String, String>();
//			Map<String, Integer> params3 = new HashMap<String, Integer>();
//			params.put("userId", player2);
//			params2.put("userId", player1);
//			RestTemplate restTemplate = new RestTemplate();
//			int trophies = restTemplate.getForObject(uri, Integer.class, params);
//			if(trophies < 5){
//				params3.put("trophies", 0);
//				restTemplate.put(uri2, params, params3);
//			}
//			else{
//				trophies -= 5;
//				params3.put("trophies", trophies);
//				restTemplate.put(uri2, params, params3);
//			}
//			int trophies2 = restTemplate.getForObject(uri, Integer.class, params2);
//			params3.remove("trophies");
//			params3.put("trophies", trophies2 + 10);
//			restTemplate.put(uri2, params2, params3);
//		}
//		else{
//			String uri =  "http://coms-309-ss-5.misc.iastate.edu:3306/users/{userId}/trophies";
//			String uri2 = "http://coms-309-ss-5.misc.iastate.edu:3306/users/{userId}/trophies/{trophies}";
//			Map<String, String> params = new HashMap<String, String>();
//			Map<String, String> params2 = new HashMap<String, String>();
//			Map<String, Integer> params3 = new HashMap<String, Integer>();
//			params.put("userId", player2);
//			params2.put("userId", player1);
//			RestTemplate restTemplate = new RestTemplate();
//			int trophies = restTemplate.getForObject(uri, Integer.class, params);
//			if(trophies < 5){
//				params3.put("trophies", 0);
//				restTemplate.put(uri2, params, params3);
//			}
//			else{
//				trophies -= 5;
//				params3.put("trophies", trophies);
//				restTemplate.put(uri2, params, params3);
//			}
//			int trophies2 = restTemplate.getForObject(uri, Integer.class, params2);
//			params3.remove("trophies");
//			params3.put("trophies", trophies2 + 10);
//			restTemplate.put(uri2, params2, params3);
//		}


	}

}
