package com.pvptowerdefense.server.decks.decksdao;

import com.pvptowerdefense.server.decks.models.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecksDao extends CrudRepository<Deck, String> {
    public Deck findDeckByDeckId(String deckId);
}
