package com.pvptowerdefense.database.cards;

import com.pvptowerdefense.database.cards.models.Card;
import com.pvptowerdefense.database.cards.services.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsController {
	/**
	 * CardsService is a class that is used to perform the majority of the
	 * functionality.
	 */
	private CardsService cardsService;

	/**
	 * Controller class for the cards service. Used to access the cards database
	 * table.
	 *
	 * @param autowiredCardsService Autowired instance of CardService
	 */
	@Autowired
	public CardsController(final CardsService autowiredCardsService) {
		this.cardsService = autowiredCardsService;
	}

	/**
	 * Adds a card to the database by sending a post request to the /cards
	 * url with the card in the request body.
	 * <p>
	 * Example of format
	 * <p>
	 * { <br>
	 * "name": "Card 1", <br>
	 * "description": "Card 1 desc", <br>
	 * "cost": 1, <br>
	 * "damage": 1, <br>
	 * "hitPoints": 1, <br>
	 * "speed": 1, <br>
	 * "type": "melee" <br>
	 * }
	 *
	 * @param card The card information in JSON format
	 */
	@RequestMapping(method = RequestMethod.POST, value = "")
	public void addCardToDB(@RequestBody Card card) {
		cardsService.addCardToDB(card);
	}

	/**
	 * Used to get all the card types from the database. This database will be
	 * static, meaning cards cannot be added, removed, or changed unless done
	 * so for game balancing.
	 *
	 * @return List of the cards
	 */
	@RequestMapping(method = RequestMethod.GET, value = "")
	public List<Card> getAllCards() {
		return cardsService.getAllCards();
	}

	/**
	 * Loads a list of temporary cards into the cards database.
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/load")
	public void loadCardsToDatabase() {
		cardsService.loadCardsToDatabase();
	}

	/**
	 * Returns the card with the given name.
	 *
	 * @param cardName The name of the card you are looking for
	 * @return List of the cards
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{cardName}")
	public Card getCardByName(@PathVariable String cardName) {
		return cardsService.getCardByName(cardName);
	}
}
