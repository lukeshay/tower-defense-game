package com.pvptowerdefense.server.spring.services;

import com.pvptowerdefense.server.spring.daos.CardsDao;
import com.pvptowerdefense.server.spring.models.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Cards service.
 */
@Service
@Validated
public class CardsService {
	private static Logger logger =
			LoggerFactory.getLogger(CardsService.class.getName());
	private CardsDao cardsDao;

	/**
	 * Instantiates a new Cards service.
	 *
	 * @param cardsDao the cards dao
	 */
	@Autowired
	public CardsService(CardsDao cardsDao) {
		this.cardsDao = cardsDao;
	}

	/**
	 * Gets all cards.
	 *
	 * @return the all cards
	 */
	public List<Card> getAllCards() {
		logger.info("getting all cards");
		List<Card> cards = new ArrayList<>();
		cardsDao.findAll().forEach(cards::add);
		return cards;
	}

	/**
	 * Load cards to database.
	 */
	public void loadCardsToDatabase() {
		logger.info("loading cards to database");
		List<Card> cards = Arrays.asList(
		        new Card("Skeleton", "Weak skeleton ground troop.", 1, 1, 1, 2, "UNIT", 250),
				new Card("Reaper", "Weak reaper", 1, 1, 1, 2, "UNIT", 250),
				new Card("Reaper", "Medium reaper", 2, 2, 2, 3, "UNIT", 250),
				new Card("Reaper king", "Strong reaper", 5, 5, 5, 3, "UNIT", 250),
				new Card("Fire Golem", "Weak fire golem", 1, 1, 1, 2, "UNIT", 250),
				new Card("Fire Golem", "Medium fire golem", 2, 2, 2, 3, "UNIT", 250),
				new Card("Golem King", "Strong golem", 5, 5, 5, 3, "UNIT", 250),
				new Card("Wizard", "Weak wizard", 1, 1, 1, 3, "UNIT", 650),
				new Card("Wizard", "Medium wizard", 2, 2, 2, 3, "UNIT", 650),
				new Card("Master wizard", "Strong wizard", 5, 5, 5, 5, "UNIT", 650)
		);

		cardsDao.saveAll(cards);
	}

	/**
	 * Gets card by name.
	 *
	 * @param cardName the card name
	 * @return the card by name
	 */
	public Card getCardByName(@Valid String cardName) {
		logger.info("getting card " + cardName);
		return cardsDao.getCardByName(cardName);
	}

	/**
	 * Adds card to database.
	 *
	 * @param card the card
	 */
	public void addCard(@Valid Card card) {
		logger.info("adding card" + card.getName());
		if (!cardsDao.existsById(card.getName())) {
			cardsDao.save(card);
		}
		else {
			throw new IllegalArgumentException("The inputted card already " +
					"exists");
		}
	}

	/**
	 * Update card.
	 *
	 * @param card the card
	 */
	public void updateCard(Card card) {
		logger.info("updating card " + card.getName());
		cardsDao.save(card);
	}

	/**
	 * Deletes card from database.
	 *
	 * @param cardName card to be deleted
	 */
	public void deleteCard(String cardName) {
		logger.info("deleting card " + cardName);
		cardsDao.deleteById(cardName);
	}
}
