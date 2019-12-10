package com.pvptowerdefense.server.spring.decks;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
	Deck findDeckByDeckId(int deckId);

	/**
	 * Find decks by user id list.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<Deck> findDecksByUserId(String userId);
}
