package com.pvptowerdefense.server.socket.models;

import java.util.*;

public class Map {

    private PlayedCard[][] cardsInPlay;
    private List<PlayedCard> cards;
    private static final int TOWER1_Y = 150;
    private static final int TOWER2_Y = 250;
    private static final int TOWER3_Y = 350;
    private static final int TOWER1_X = 150;
    private static final int TOWER2_X = 100;
    private static final int TOWER3_X = 150;


    public Map(){
        cardsInPlay = new PlayedCard[1500][500];
        cards = new ArrayList<PlayedCard>();
        cardsInPlay[TOWER1_X][TOWER1_Y] = makeTower(TOWER1_X, TOWER1_Y, 1);
        cardsInPlay[TOWER2_X][TOWER2_Y] = makeTower(TOWER2_X, TOWER2_Y, 1);
        cardsInPlay[TOWER3_X][TOWER3_Y] = makeTower(TOWER3_X, TOWER3_Y, 1);

        cardsInPlay[1499 - TOWER1_X][TOWER1_Y] = makeTower(1499 - TOWER1_X, TOWER1_Y, 2);
        cardsInPlay[1499 - TOWER2_X][TOWER2_Y] = makeTower(1499 - TOWER2_X, TOWER1_Y, 2);
        cardsInPlay[1499 - TOWER3_X][TOWER3_Y] = makeTower(1499 - TOWER3_X, TOWER1_Y, 2);
    }

    public PlayedCard[][] getCardsInPlay() {
        return cardsInPlay;
    }

    public void setCardsInPlay(PlayedCard[][] cardsInPlay) {
        this.cardsInPlay = cardsInPlay;
    }

    public List<PlayedCard> getCards() {
        return cards;
    }

    public void setCards(List<PlayedCard> cards) {
        this.cards = cards;
    }

    public void addCard(PlayedCard card){
        cardsInPlay[card.getxValue()][card.getyValue()] = card;
        cards.add(card);
    }

    public void clockCycle(){
        // go through array and check for card positions
        for(PlayedCard playedCard : cards){
            if(playedCard.getPlayer() == 1){
                cardsInPlay[playedCard.getxValue()][playedCard.getyValue()] = null;
                playedCard.setxValue(playedCard.getxValue()+playedCard.getSpeed());
                cardsInPlay[playedCard.getxValue()][playedCard.getyValue()] = playedCard;
            }
            else if(playedCard.getPlayer() == 2){
                cardsInPlay[playedCard.getxValue()][playedCard.getyValue()] = null;
                playedCard.setxValue(playedCard.getxValue() - playedCard.getSpeed());
                cardsInPlay[playedCard.getxValue()][playedCard.getyValue()] = playedCard;
            }
            else{ cardsInPlay[playedCard.getxValue()][playedCard.getyValue()] = null; }
        }
    }

    private PlayedCard makeTower(int xValue, int yValue, int player){
        return new PlayedCard("Tower", "tower", 0, 10, 100, 0, "UNIT", 100, xValue,
                yValue, player);
    }
}
