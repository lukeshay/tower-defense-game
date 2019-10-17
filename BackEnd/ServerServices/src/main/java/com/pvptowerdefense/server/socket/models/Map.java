package com.pvptowerdefense.server.socket.models;

import java.util.*;

public class Map {

    private List<PlayedCard> cards;
    private String player1;
    private String player2;
    private boolean gameState;
    private String winner;

    private static final int MAX_X = 2999;
    private static final int MAX_Y = 999;
    private static final int TOWER1_Y = 150;
    private static final int TOWER2_Y = 250;
    private static final int TOWER3_Y = 350;
    private static final int TOWER1_X = 150;
    private static final int TOWER2_X = 100;
    private static final int TOWER3_X = 150;


    public Map(String userId1, String userId2){
        cards = new ArrayList<PlayedCard>();
        player1 = userId1;
        player2 = userId2;
        gameState = true;

        cards.add(makeTower(TOWER1_X, TOWER1_Y, userId1));
        cards.add(makeTower(TOWER2_X, TOWER2_Y, userId1));
        cards.add(makeTower(TOWER3_X, TOWER3_Y, userId1));

        cards.add(makeTower(MAX_X - TOWER1_X, TOWER1_Y, userId2));
        cards.add(makeTower(MAX_X - TOWER2_X, TOWER1_Y, userId2));
        cards.add(makeTower(MAX_X - TOWER3_X, TOWER1_Y, userId2));
    }

    public List<PlayedCard> getCards() {
        return cards;
    }

    public void setCards(List<PlayedCard> cards) {
        this.cards = cards;
    }

    public void addCard(PlayedCard card){
        cards.add(card);
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
        // go through array and check for card positions


        for(PlayedCard playedCard : cards){
            if(playedCard.getPlayer().equals(player1)){
                playedCard.setXValue(playedCard.getXValue()+playedCard.getSpeed());
            }
            else if(playedCard.getPlayer().equals(player2)){
                playedCard.setXValue(playedCard.getXValue() - playedCard.getSpeed());
            }
            else{ cards.remove(playedCard); }
        }

        return gameState;
    }

    private PlayedCard makeTower(int xValue, int yValue, String player){
        return new PlayedCard("Tower", "tower", 0, 10, 100, 0, "UNIT", 100, xValue,
                yValue, player);
    }


}
