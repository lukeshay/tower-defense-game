package com.pvptowerdefense.server.users.models;

import com.pvptowerdefense.server.cards.models.Card;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> deck;

    private String deckName;

    public Deck(){

    }

    public Deck(List<Card> deck, String deckName) {
        this.deck = deck;
        this.deckName = deckName;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void addCard(Card cardName){

    }
}

