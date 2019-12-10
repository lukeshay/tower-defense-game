package com.pvptowerdefense.server.spring.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Cards controller.
 */
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
	 * @param cardsService Autowired instance of CardService
	 */
	@Autowired
	public CardsController(CardsService cardsService) {
		this.cardsService = cardsService;
	}

	/**
	 * Adds a card to the database by sending a post request to the /cards
	 * url with the card in the request body.
	 *
	 * @param card The card information in JSON format
	 * @return the map
	 */
	@RequestMapping(method = RequestMethod.POST, value = "")
	public Map<String, Boolean> addCardToDb(@Valid @RequestBody Card card) {
		cardsService.addCard(card);
		return getSuccessMap();
	}

	/**
	 * Updates the given card in the database.
	 *
	 * @param card The card information in JSON format
	 * @return Success message
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "")
	public Map<String, Boolean> updateCardInDb(@RequestBody Card card) {
		cardsService.updateCard(card);
		return getSuccessMap();
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

	/**
	 * Returns a map with in the form {"success": true}
	 *
	 * @return {"success": true}
	 */
	private static Map<String, Boolean> getSuccessMap() {
		HashMap<String, Boolean> map = new HashMap<>();
		map.put("success", true);

		return map;
	}
}
