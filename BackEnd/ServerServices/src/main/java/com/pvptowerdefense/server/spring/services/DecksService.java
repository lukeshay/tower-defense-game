package com.pvptowerdefense.server.spring.services;

import com.pvptowerdefense.server.spring.daos.DecksDao;
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
     * Gets all decks.
     *
     * @return all decks in database
     */
    public List<Deck> getAllDecks(){
        List<Deck> decks = new ArrayList<Deck>();
        decksDao.findAll().forEach(decks::add);

        return decks;
    }

    /**
     * Finds the deck that matches the given id.
     *
     * @param deckId the deck id
     * @return the deck
     */
    public Deck findDeckByDeckId(int deckId){ return decksDao.findDeckByDeckId(deckId); }
}
