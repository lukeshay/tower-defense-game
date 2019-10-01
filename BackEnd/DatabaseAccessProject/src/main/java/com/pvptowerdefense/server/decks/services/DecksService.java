package com.pvptowerdefense.server.decks.services;

import com.pvptowerdefense.server.cards.models.Card;
import com.pvptowerdefense.server.decks.decksdao.DecksDao;
import com.pvptowerdefense.server.decks.models.Deck;
import com.pvptowerdefense.server.users.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DecksService {
    private DecksDao decksDao;

    public void loadPresetDecksToDatabase(){
        List<Deck> decks = Arrays.asList(
                new Deck(new ArrayList<Card>(), "deck1"),
                new Deck(new ArrayList<Card>(), "deck2"),
                new Deck(new ArrayList<Card>(), "deck3"),
                new Deck(new ArrayList<Card>(), "deck4")
        );

        decks.get(0).addCard(new Card("TylerK", "base", 3,1,1,1,
                "unit",1));
        decksDao.saveAll(decks);

    }

    public Deck findDeckById(String deckId){
        return decksDao.findDeckByDeckId(deckId);
    }

    public List<Deck> getAllDecks(){
        List<Deck> decks = new ArrayList<>();
        decksDao.findAll().forEach(decks::add);
        return decks;
    }
}
