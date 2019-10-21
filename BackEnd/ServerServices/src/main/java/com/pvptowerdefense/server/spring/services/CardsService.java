package com.pvptowerdefense.server.spring.services;

import com.pvptowerdefense.server.spring.daos.CardsDao;
import com.pvptowerdefense.server.spring.models.Card;
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
		List<Card> cards = new ArrayList<>();
		cardsDao.findAll().forEach(cards::add);
		return cards;
	}

	/**
	 * Load cards to database.
	 */
	public void loadCardsToDatabase() {
		List<Card> cards = Arrays.asList(
				new Card("Reaper", "Weak reaper", 1, 1, 1, 1, "UNIT", 50),
				new Card("Reaper", "Medium reaper", 2, 2, 2, 2, "UNIT", 40),
				new Card("Reaper king", "Strong reaper", 5, 5, 5, 5, "UNIT", 50),
				new Card("Fire Golem", "Weak fire golem", 1, 1, 1, 1, "UNIT", 50),
				new Card("Fire Golem", "Medium fire golem", 2, 2, 2, 2, "UNIT", 40),
				new Card("Golem King", "Strong golem", 5, 5, 5, 5, "UNIT", 50),
				new Card("Wizard", "Weak wizard", 1, 1, 1, 1, "UNIT", 50),
				new Card("Wizard", "Medium wizard", 2, 2, 2, 2, "UNIT", 40),
				new Card("Master wizard", "Strong wizard", 5, 5, 5, 5, "UNIT", 50)
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
		return cardsDao.getCardByName(cardName);
	}

	/**
	 * Adds card to database.
	 *
	 * @param card the card
	 */
	public void addCard(@Valid Card card) {
		if (!cardsDao.existsById(card.getName())) {
			cardsDao.save(card);
		}
		else {
			throw new IllegalArgumentException("The inputted card already " +
					"exists");
		}
	}

	public void updateCard(Card card) {
		cardsDao.save(card);
	}

	/**
	 * Deletes card from database.
	 *
	 * @param cardName card to be deleted
	 */
	public void deleteCard(String cardName) {
		cardsDao.deleteById(cardName);
	}
}
