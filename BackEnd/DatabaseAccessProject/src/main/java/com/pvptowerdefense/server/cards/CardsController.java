package com.pvptowerdefense.server.cards;

import com.pvptowerdefense.server.cards.models.Card;
import com.pvptowerdefense.server.cards.services.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
	 *
	 *  @param card The card information in JSON format
	 */
	@RequestMapping(method = RequestMethod.POST, value = "")
	public String addCardToDb(@Valid @RequestBody Card card) {
		cardsService.addCardToDb(card);
		return "{\"success\": true}";
	}

	/**
	 * Used to get all the card types from the database. This database will be
	 * static, meaning cards cannot be added, removed, or changed unless done
	 * so for game balancing. Returns the cards in the format that follows.
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
	public String loadCardsToDatabase() {
		cardsService.loadCardsToDatabase();
		return "{\"success\": true}";
	}

	/**
	 * Returns the card with the given name. The format of the body is as
	 * follows.
	 *
	 * @param cardName The name of the card you are looking for
	 * @return List of the cards
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{cardName}")
	public Card getCardByName(@Valid @PathVariable String cardName) {
		return cardsService.getCardByName(cardName);
	}
}
