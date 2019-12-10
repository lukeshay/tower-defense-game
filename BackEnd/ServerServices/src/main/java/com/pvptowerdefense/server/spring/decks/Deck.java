package com.pvptowerdefense.server.spring.decks;

import com.pvptowerdefense.server.spring.cards.Card;

import javax.persistence.*;
import java.util.List;

/**
 * Deck type class
 */
@Entity
public class Deck {

    @ElementCollection
    private List<Card> deck;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "DECK_ID", nullable = false)
    private int deckId;

    @Column(unique = true, name = "DECK_NAME", nullable = false)
    private String deckName;

	/**
	 * Creates a deck from given parameters
	 *
	 * @param deck     - list of cards, possibly empty
	 * @param deckName - the name for the deck
	 * @param userId   the user id
	 */
	public Deck(List<Card> deck, String deckName, String userId) {
        this.deck = deck;
        this.deckName = deckName;
        this.userId = userId;
    }

	/**
	 * Gets the list of cards in the user's current deck
	 *
	 * @return list of cards
	 */
	public List<Card> getDeck() {
        return deck;
    }

	/**
	 * Adds a new card to the deck
	 *
	 * @param card - card to be added
	 */
	public void addCard(Card card){
        deck.add(card);
    }

	/**
	 * Removes card from the deck
	 *
	 * @param card - card
	 */
	public void removeCard(Card card) { deck.remove(card);}

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	public String getUserId() { return userId; }
}