package com.pvptowerdefense.server.spring.models;

import com.pvptowerdefense.server.spring.models.Card;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Deck type class
 */
@Entity
public class Deck {

    @ElementCollection
    private List<Card> deck;

    @Column(unique = false, name = "USER_ID", nullable = false)
    private String userId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "DECK_ID", nullable = false)
    private int deckId;

    @Column(unique = true, name = "DECK_NAME", nullable = false)
    private String deckName;

    /**
     * Creates an empty deck
     */
    public Deck(){
        this.deck = new ArrayList<>();
    }

    /**
     * Creates a deck from given parameters
     * @param deck - list of cards, possibly empty
     * @param deckName - the name for the deck
     */
    public Deck(List<Card> deck, String deckName, String userId) {
        this.deck = deck;
        this.deckName = deckName;
        this.userId = userId;
    }

    /**
     * Gets the list of cards in the user's current deck
     * @return list of cards
     */
    public List<Card> getDeck() {
        return deck;
    }

    /**
     * Sets the list of cards to the desired list for the deck
     * @param deck - list of cards to be changed to
     */
    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    /**
     * Gets the current deck's name
     * @return the deck name
     */
    public String getDeckName() {
        return deckName;
    }

    /**
     * Sets the deck name to the desired name
     * @param deckName - name to change the deck name to
     */
    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    /**
     * Adds a new card to the deck
     * @param cardName - card to be added
     */
    public void addCard(Card cardName){
        deck.add(cardName);
    }
}

