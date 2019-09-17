package com.example.myapplication;

public class Player {
    public Deck deck;
    public CardInHand[] hand;

    public Player(){
        deck = new Deck(this);
        hand = new CardInHand[4];
    }

    public void drawHand(){
        hand[0] = deck.drawCard(0);
        hand[1] = deck.drawCard(1);
        hand[2] = deck.drawCard(2);
        hand[3] = deck.drawCard(3);
    }
}
