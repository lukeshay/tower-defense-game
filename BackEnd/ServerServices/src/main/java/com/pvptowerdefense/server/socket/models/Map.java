package com.pvptowerdefense.server.socket.models;

public class Map {

    private PlayedCard[][] cardsInPlay;

    public Map(){
        cardsInPlay = new PlayedCard[1500][500];
    }

    public PlayedCard[][] getCardsInPlay() {
        return cardsInPlay;
    }

    public void setCardsInPlay(PlayedCard[][] cardsInPlay) {
        this.cardsInPlay = cardsInPlay;
    }
}
