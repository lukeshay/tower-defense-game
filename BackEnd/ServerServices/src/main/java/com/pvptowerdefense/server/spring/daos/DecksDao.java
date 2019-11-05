package com.pvptowerdefense.server.spring.daos;

import com.pvptowerdefense.server.spring.models.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface DecksDao extends CrudRepository<Deck, String> {
    public Deck findDeckByDeckId(int deckId);
    public List<Deck> findDecksByUserId(String userId);
}
