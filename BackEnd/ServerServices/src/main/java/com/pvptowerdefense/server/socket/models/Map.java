package com.pvptowerdefense.server.socket.models;

import shared.PlayedCard;

import java.util.*;

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

    public List<PlayedCard> getCards() {
        List<PlayedCard> total = new ArrayList<>();
        total.addAll(cardsP1);
        total.addAll(cardsP2);
        return total;
    }

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

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public boolean isGameState() {
        return gameState;
    }

    public void setGameState(boolean gameState) {
        this.gameState = gameState;
    }

    public String getWinner() {
        return winner;
    }

    public boolean clockCycle(){
        if (counter % 60 == 0) {
            for (PlayedCard p1Cards : cardsP1) {
                boolean attack = false;
                for (PlayedCard p2Cards : cardsP2) {
                    if (distance(p1Cards, p2Cards) <= p1Cards.getRange() && !attack) {
                        System.out.println("P1 card: " + p1Cards.toString() + " has X: " + p1Cards.getxValue()+ " has Y: " + p1Cards.getyValue());
                        System.out.println("P2 card: " + p2Cards.toString() + " has X: " + p2Cards.getxValue()+ " has Y: " + p2Cards.getyValue());
                        System.out.println("Distance between: " + distance(p1Cards, p2Cards));
                        attack = true;
                        System.out.println(attack);
                        System.out.println("P1 DMG: " + p1Cards.getDamage());
                        System.out.println("P2 HP before: " + p2Cards.getHitPoints());
                        p2Cards.setHitPoints(p2Cards.getHitPoints() - p1Cards.getDamage());

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
                        attack = true;
                        p1Cards.setHitPoints(p1Cards.getHitPoints() - p2Cards.getDamage());
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


        counter++;
        return gameState;
    }

    private PlayedCard makeTower(int xValue, int yValue, String player, String towerName){
        return new PlayedCard(towerName, "tower", 0, 1, 100, 0, "UNIT", 100, xValue,
                    yValue, player);

    }

    private double distance(PlayedCard card1, PlayedCard card2){
        int x = card1.getxValue() - card2.getxValue();
        int y = card1.getyValue() - card2.getyValue();
        int xSquare = x * x;
        int ySquare = y * y;
        return Math.sqrt(xSquare + ySquare);
    }
}
