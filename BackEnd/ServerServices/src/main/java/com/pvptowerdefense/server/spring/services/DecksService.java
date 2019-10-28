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

@Service
public class DecksService {
    private static Logger logger =
            LoggerFactory.getLogger(DecksService.class.getName());
    private DecksDao decksDao;

    @Autowired
    public DecksService(DecksDao decksDao){ this.decksDao = decksDao; }

    public List<Deck> getAllDecks(){
        List<Deck> decks = new ArrayList<Deck>();
        decksDao.findAll().forEach(decks::add);

        return decks;
    }

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
}
