package com.pvptowerdefense.server.spring.daos;

import com.pvptowerdefense.server.spring.models.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * The interface Decks dao.
 */
@Repository
public interface DecksDao extends CrudRepository<Deck, String> {
    Deck findDeckByDeckId(int deckId);
    List<Deck> findDecksByUserId(String userId);
}
