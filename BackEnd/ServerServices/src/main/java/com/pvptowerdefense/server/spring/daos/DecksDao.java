package com.pvptowerdefense.server.spring.daos;

import com.pvptowerdefense.server.spring.models.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Decks dao.
 */
@Repository
public interface DecksDao extends CrudRepository<Deck, String> {
	/**
	 * Find deck by deck id deck.
	 *
	 * @param deckId the deck id
	 * @return the deck
	 */
	public Deck findDeckByDeckId(int deckId);
}
