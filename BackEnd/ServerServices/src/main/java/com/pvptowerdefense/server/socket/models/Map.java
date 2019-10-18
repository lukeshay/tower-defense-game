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

    private static final int MAX_X = 2999;
    private static final int MAX_Y = 999;
    private static final int TOWER1_Y = 150;
    private static final int TOWER2_Y = 250;
    private static final int TOWER3_Y = 350;
    private static final int TOWER1_X = 150;
    private static final int TOWER2_X = 100;
    private static final int TOWER3_X = 150;


    public Map(String userId1, String userId2){
        cardsP1 = new ArrayList<PlayedCard>();
        cardsP2 = new ArrayList<PlayedCard>();
        player1 = userId1;
        player2 = userId2;
        gameState = true;
        counter = 0;

        cardsP1.add(makeTower(TOWER1_X, TOWER1_Y, userId1));
        cardsP1.add(makeTower(TOWER2_X, TOWER2_Y, userId1));
        cardsP1.add(makeTower(TOWER3_X, TOWER3_Y, userId1));

        cardsP2.add(makeTower(MAX_X - TOWER1_X, TOWER1_Y, userId2));
        cardsP2.add(makeTower(MAX_X - TOWER2_X, TOWER1_Y, userId2));
        cardsP2.add(makeTower(MAX_X - TOWER3_X, TOWER1_Y, userId2));
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
                    if (distance(p1Cards, p2Cards) <= p1Cards.getRange()) {
                        p2Cards.setHitPoints(p2Cards.getHitPoints() - p1Cards.getDamage());
                        attack = true;
                        break;
                    }
                }
                if(!attack){
                    p1Cards.setxValue(p1Cards.getxValue() + p1Cards.getSpeed());
                }
            }

            for (PlayedCard p2Cards : cardsP2) {
                boolean attack = false;
                for (PlayedCard p1Cards : cardsP1) {
                    if (distance(p2Cards, p1Cards) <= p2Cards.getRange()) {
                        p1Cards.setHitPoints(p1Cards.getHitPoints() - p2Cards.getDamage());
                        attack = true;
                        break;
                    }
                }
                if(!attack){
                    p2Cards.setxValue(p2Cards.getxValue() + p2Cards.getSpeed());
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

    private void attackMove(List<PlayedCard> cardsP1, List<PlayedCard> cardsP2) {
        for (PlayedCard p1Cards : cardsP1) {
            boolean attack = false;
            for (PlayedCard p2Cards : cardsP2) {
                if (distance(p1Cards, p2Cards) <= p1Cards.getRange() && attack == false) {
                    p2Cards.setHitPoints(p2Cards.getHitPoints() - p1Cards.getDamage());
                    attack = true;
                }
            }
            if(!attack){
                p1Cards.setXValue(p1Cards.getXValue() + p1Cards.getSpeed());
            }
        }
    }

    private PlayedCard makeTower(int xValue, int yValue, String player){
        return new PlayedCard("Tower", "tower", 0, 10, 100, 0, "UNIT", 100, xValue,
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
