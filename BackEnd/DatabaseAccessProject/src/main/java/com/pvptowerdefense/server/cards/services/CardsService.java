package com.pvptowerdefense.server.cards.services;

import com.pvptowerdefense.server.cards.dao.CardsDao;
import com.pvptowerdefense.server.cards.models.Card;
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
				new Card("Card 1", "Card 1 desc", 1, 1, 1, 1, "UNIT", 50),
				new Card("Card 2", "Card 2 desc", 2, 2, 2, 2, "SPELL", 40),
				new Card("Card 3", "Card 3 desc", 3, 3, 3, 3, "UNIT", 5),
				new Card("Card 4", "Card 4 desc", 4, 4, 4, 4, "SPELL", 100)
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
	public void addCardToDb(@Valid Card card) {
		if (!cardsDao.existsById(card.getName())) {
			cardsDao.save(card);
		}
		else {
			throw new IllegalArgumentException("The inputted card already " +
					"exists");
		}
	}

	public void updateCardInDb(Card card) {
		cardsDao.save(card);
	}

	/**
	 * Deletes card from database.
	 *
	 * @param cardName card to be deleted
	 */
	public void deleteCardFromDatabase(String cardName) {
		cardsDao.deleteById(cardName);
	}
}
