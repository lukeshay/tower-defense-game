package com.pvptowerdefense.server.socket.models;

import java.util.*;

/**
 * Map class type
 * Used to contain the game logic and the list of cards being played for each player
 */
public class Map {

    private List<PlayedCard> cardsP1;
    private List<PlayedCard> cardsP2;
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
     * @param userId1 - the userId for player 1
     * @param userId2 - the userId for player 2
     */
    public Map(String userId1, String userId2){
        cardsP1 = new ArrayList<PlayedCard>();
        cardsP2 = new ArrayList<PlayedCard>();
        player1 = userId1;
        player2 = userId2;
        gameState = true;
        counter = 0;

        cardsP1.add(makeTower(TOWER1_X, TOWER1_Y, userId1, "tower1"));
        cardsP1.add(makeTower(TOWER2_X, TOWER2_Y, userId1, "tower2"));
        cardsP1.add(makeTower(TOWER3_X, TOWER3_Y, userId1, "tower3"));

        cardsP2.add(makeTower(MAX_X - TOWER1_X - 250, TOWER1_Y, userId2, "tower1"));
        cardsP2.add(makeTower(MAX_X - TOWER2_X - 250, TOWER2_Y, userId2, "tower2"));
        cardsP2.add(makeTower(MAX_X - TOWER3_X - 250, TOWER3_Y, userId2, "tower3"));
    }

    /**
     * Gets the list of all the played cards for each user
     * @return list of played cards for each user
     */
    public List<PlayedCard> getCards() {
        List<PlayedCard> total = new ArrayList<>();
        total.addAll(cardsP1);
        total.addAll(cardsP2);
        return total;
    }

    /**
     * Adds a card to the specified players list of played cards
     * @param card - card to be added
     */
    public void addCard(PlayedCard card){
        if(card.getPlayer().equals(player1)){
            cardsP1.add(card);
        }
        else if(card.getPlayer().equals(player2)){
            cardsP2.add(card);
        }
        else { // do nothing
        }
    }

    /**
     * Gets player1's user Id
     * @return the userId
     */
    public String getPlayer1() {
        return player1;
    }

    /**
     * Sets player1's userId
     * @param player1 - new userId
     */
    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    /**
     * Gets player2's user Id
     * @return the userId
     */
    public String getPlayer2() {
        return player2;
    }

    /**
     * Sets player2's userId
     * @param player2 - new userId
     */
    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    /**
     * Gets the current game state
     * @return the game state
     */
    public boolean isGameState() {
        return gameState;
    }

    /**
     * Sets the current game state to the desired value
     * @param gameState - new game state value
     */
    public void setGameState(boolean gameState) {
        this.gameState = gameState;
    }

    /**
     * Gets the winner of the game
     * @return the winner's userId
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Method that is called 60 times a second to move and attack the cards
     * @return the game state to check if it should end
     */
    public boolean clockCycle(){
        if (counter % 60 == 0) {
            for (PlayedCard p1Cards : cardsP1) {
                boolean attack = false;
                for (PlayedCard p2Cards : cardsP2) {
                    if (distance(p1Cards, p2Cards) <= p1Cards.getRange() && !attack) {
                        System.out.println("P1 X: " + p1Cards.getxValue());
                        System.out.println("P2 X: " + p2Cards.getxValue());
                        System.out.println("Distance: " + distance(p1Cards, p2Cards));
                        System.out.println(p1Cards.getName() + " is P1, " + p2Cards.getName() + " is P2.");
                        System.out.println("P1 DMG: " + p1Cards.getDamage());
                        System.out.println("P2 HP before: " + p2Cards.getHitPoints());
                        attack = true;
                        p2Cards.setHitPoints(p2Cards.getHitPoints() - p1Cards.getDamage());
                        System.out.println("P2 HP after: " + p2Cards.getHitPoints());

                    }
                }
                if(!attack){
                    p1Cards.setxValue(p1Cards.getxValue() + p1Cards.getSpeed());
                }
            }
            for (PlayedCard p2Cards : cardsP2) {
                boolean attack = false;
                for (PlayedCard p1Cards : cardsP1) {
                    if (distance(p2Cards, p1Cards) <= p2Cards.getRange() && !attack) {
                        System.out.println("P2 X: " + p2Cards.getxValue());
                        System.out.println("P1 X: " + p1Cards.getxValue());
                        System.out.println("Distance: " + distance(p1Cards, p2Cards));
                        System.out.println(p2Cards.getName() + " is P2, " + p1Cards.getName() + " is P1.");
                        System.out.println("P2 DMG: " + p2Cards.getDamage());
                        System.out.println("P1 HP before: " + p1Cards.getHitPoints());
                        attack = true;
                        p1Cards.setHitPoints(p1Cards.getHitPoints() - p2Cards.getDamage());
                        System.out.println("P1 HP after: " + p1Cards.getHitPoints());
                    }
                }
                if(!attack){
                    p2Cards.setxValue(p2Cards.getxValue() - p2Cards.getSpeed());
                }
            }
            for(PlayedCard p1 : cardsP1){
                if(p1.getHitPoints() <= 0){
                    cardsP1.remove(p1);
                }
            }
            for(PlayedCard p2 : cardsP2){
                if(p2.getHitPoints() <= 0){
                    cardsP2.remove(p2);
                }
            }
        }
        else {
            for (PlayedCard p1Cards : cardsP1) {
                boolean attack = false;
                for (PlayedCard p2Cards : cardsP2) {
                    if (distance(p1Cards, p2Cards) <= p1Cards.getRange() && attack == false) {
                        attack = true;
                    }
                }
                if(!attack){
                    p1Cards.setxValue(p1Cards.getxValue() + p1Cards.getSpeed());
                }
            }
            for (PlayedCard p2Cards : cardsP2) {
                boolean attack = false;
                for (PlayedCard p1Cards : cardsP1) {
                    if (distance(p2Cards, p1Cards) <= p2Cards.getRange() && attack == false) {
                        attack = true;
                    }
                }
                if(!attack){
                    p2Cards.setxValue(p2Cards.getxValue() - p2Cards.getSpeed());
                }
            }
        }

        for(PlayedCard playCard : cardsP1){
            if(playCard.getDescription().equals("tower")){
                if(playCard.getName().equals("tower2") && playCard.getHitPoints() <= 0){
                    gameState = false;
                    winner = player2;
                }
            }
        }

        for(PlayedCard playCard : cardsP2){
            if(playCard.getDescription().equals("tower")){
                if(playCard.getName().equals("tower2") && playCard.getHitPoints() <= 0){
                    gameState = false;
                    winner = player1;
                }
            }
        }

        counter++;
        return gameState;
    }

    /**
     * Helper method to make the towers at the start of the game
     * @param xValue - x value for the turret
     * @param yValue - y value for the turret
     * @param player - which player the turret is for
     * @param towerName - the tower's name
     * @return the created played card
     */
    private PlayedCard makeTower(int xValue, int yValue, String player, String towerName){
        return new PlayedCard(towerName, "tower", 0, 1, 5, 0, "UNIT", 100, xValue,
                    yValue, player);

    }

    /**
     * Helper method to find the distance between 2 cards
     * @param card1 - first card
     * @param card2 - second card
     * @return - distance between the first and second card
     */
    private double distance(PlayedCard card1, PlayedCard card2){
        int x = card1.getxValue() - card2.getxValue();
        int y = card1.getyValue() - card2.getyValue();
        int xSquare = x * x;
        int ySquare = y * y;
        return Math.sqrt(xSquare + ySquare);
    }
}
