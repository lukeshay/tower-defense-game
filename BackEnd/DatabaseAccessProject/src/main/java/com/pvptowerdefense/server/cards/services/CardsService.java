package com.pvptowerdefense.server.cards.services;

import com.pvptowerdefense.server.cards.dao.CardsDao;
import com.pvptowerdefense.server.cards.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Cards service.
 */
@Service
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
				new Card("Card 1", "Card 1 desc", 1, 1, 1, 1, "ranged"),
				new Card("Card 2", "Card 2 desc", 2, 2, 2, 2, "melee"),
				new Card("Card 3", "Card 3 desc", 3, 3, 3, 3, "spell"),
				new Card("Card 4", "Card 4 desc", 4, 4, 4, 4, "melee")
		);

		cardsDao.saveAll(cards);
	}

	/**
	 * Gets card by name.
	 *
	 * @param cardName the card name
	 * @return the card by name
	 */
	public Card getCardByName(String cardName) {
		return cardsDao.getCardByName(cardName);
	}

	/**
	 * Adds card to database.
	 *
	 * @param card the card
	 */
	public void addCardToDb(Card card) {
		if (!cardsDao.existsById(card.getName())) {
			cardsDao.save(card);
		}
		else {
			throw new IllegalArgumentException("The inputted card already " +
					"exists");
		}
	}
}
