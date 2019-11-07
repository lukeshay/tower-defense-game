package com.pvptowerdefense.server.spring.services;

import com.pvptowerdefense.server.spring.daos.DecksDao;
import com.pvptowerdefense.server.spring.models.Card;
import com.pvptowerdefense.server.spring.models.Deck;
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
<<<<<<< HEAD
     * Returns all the decks
     * @return list of all the decks
=======
     * Gets all decks.
     *
     * @return all decks in database
>>>>>>> 21-implement-game-timer-and-new-message-protocol
     */
    public List<Deck> getAllDecks(){
        List<Deck> decks = new ArrayList<Deck>();
        decksDao.findAll().forEach(decks::add);

        return decks;
    }

    /**
<<<<<<< HEAD
     * Returns a deck given it's id
     * @param deckId - deck's deckId
     * @return a deck matching the deckId
=======
     * Finds the deck that matches the given id.
     *
     * @param deckId the deck id
     * @return the deck
>>>>>>> 21-implement-game-timer-and-new-message-protocol
     */
    public Deck findDeckByDeckId(int deckId){ return decksDao.findDeckByDeckId(deckId); }

    /*
     * Method to add an empty deck of cards for the user
     * @param String deckname - name for the deck
     * @param int deckId - id for the deck
     */
    public void addEmptyDeck(String deckName, String userId){
        decksDao.save(new Deck(new ArrayList<Card>(), deckName, userId));
        logger.info("Adding empty deck");
    }

    /**
     * Returns the amount of decks for a user
     * @param userId - the user's Id
     * @return total amount of decks
     */
    public int getTotalDecksPerUser(String userId){
        return decksDao.findDecksByUserId(userId).size();
    }

    /**
     * Adds a card to a specified deck
     * @param card - card to be added
     * @param deckId - deckId of the deck the card will be added to
     */
    public void addCardToDeck(Card card, int deckId){
        decksDao.findDeckByDeckId(deckId).addCard(card);
    }

    /**
     * Deletes a deck
     * @param deckId - deckId to delete
     */
    public void deleteDeck(int deckId){
        decksDao.delete(decksDao.findDeckByDeckId(deckId));
    }

    /**
     * Deletes a card from a deck
     * @param deckId - deck id
     * @param card - card
     */
    public void deleteCardFromDeck(int deckId, Card card){
        decksDao.findDeckByDeckId(deckId).removeCard(card);
    }

    /**
     * Returns a deck of cards
     * @param deckId - id of the deck
     * @return - deck
     */
    public List<Card> getDeck(int deckId){
        return decksDao.findDeckByDeckId(deckId).getDeck();
    }
}
