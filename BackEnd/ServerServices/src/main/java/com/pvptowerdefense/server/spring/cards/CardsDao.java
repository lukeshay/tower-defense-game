package com.pvptowerdefense.server.spring.cards;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Cards dao.
 */
@Repository
public interface CardsDao extends CrudRepository<Card, String> {
	/**
	 * Gets card by name.
	 *
	 * @param name the name
	 * @return the card by name
	 */
	Card getCardByName(String name);
}
