package com.pvptowerdefense.server.spring.models;

import com.pvptowerdefense.server.spring.models.Card;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Deck {

    @ElementCollection
    private List<Card> deck;

    @Id
    private String deckName;

    public Deck(){
        this.deck = new ArrayList<>();
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

