package com.pvptowerdefense.server.spring.decks;

import com.pvptowerdefense.server.spring.cards.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Decks service.
 */
@Service
public class DecksService {
    private static Logger logger =
            LoggerFactory.getLogger(DecksService.class.getName());
    private DecksDao decksDao;

	/**
	 * Instantiates a new Decks service.
	 *
	 * @param decksDao the decks dao
	 */
	@Autowired
    public DecksService(DecksDao decksDao){ this.decksDao = decksDao; }

	/**
	 * <<<<<<< HEAD
	 * Returns all the decks
	 *
	 * @return list of all the decks======= Gets all decks.
	 */
	public List<Deck> getAllDecks(){
        List<Deck> decks = new ArrayList<>();
        decksDao.findAll().forEach(decks::add);

        return decks;
    }

	/**
	 * Add empty deck.
	 *
	 * @param deckName the deck name
	 * @param userId   the user id
	 */
	/*
     * Method to add an empty deck of cards for the user
     * @param String deckname - name for the deck
     * @param int deckId - id for the deck
     */
    public void addEmptyDeck(String deckName, String userId, Card card){
        List<Card> cards = new ArrayList<>();
        cards.add(card);
    	Deck deck = new Deck(cards, deckName, userId);
    	decksDao.save(deck);
        logger.info("Adding empty deck");
    }

	/**
	 * Returns the amount of decks for a user
	 *
	 * @param userId - the user's Id
	 * @return total amount of decks
	 */
	public int getTotalDecksPerUser(String userId){
        return decksDao.findDecksByUserId(userId).size();
    }

	/**
	 * Adds a card to a specified deck
	 *
	 * @param card   - card to be added
	 * @param deckId - deckId of the deck the card will be added to
	 */
	public void addCardToDeck(Card card, int deckId){
        Deck deck = decksDao.findDeckByDeckId(deckId);
        deck.addCard(card);
        decksDao.save(deck);
    }

	/**
	 * Deletes a deck
	 *
	 * @param deckId - deckId to delete
	 */
	public void deleteDeck(int deckId){
        decksDao.delete(decksDao.findDeckByDeckId(deckId));
    }

	/**
	 * Deletes a card from a deck
	 *
	 * @param deckId - deck id
	 * @param card   - card
	 */
	public void deleteCardFromDeck(int deckId, Card card){
        Deck deck = decksDao.findDeckByDeckId(deckId);
        deck.removeCard(card);
        decksDao.save(deck);
    }

	/**
	 * Returns a deck of cards
	 *
	 * @param deckId - id of the deck
	 * @return - deck
	 */
	public List<Card> getDeck(int deckId){
        return decksDao.findDeckByDeckId(deckId).getDeck();
    }

	/**
	 * Gets all the user's decks
	 *
	 * @param userId - user's id
	 * @return list of decks
	 */
	public List<Deck> getUsersDeck(String userId) {
        return decksDao.findDecksByUserId(userId);
    }

	/**
	 * Gets the userId of the deck
	 *
	 * @param deckId - deck's id
	 * @return - UserId
	 */
	public String getUserByDeckId(int deckId) {
        return decksDao.findDeckByDeckId(deckId).getUserId();
    }
}
