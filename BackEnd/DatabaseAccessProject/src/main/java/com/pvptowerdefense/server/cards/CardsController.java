package com.pvptowerdefense.server.cards;

import com.pvptowerdefense.server.cards.models.Card;
import com.pvptowerdefense.server.cards.services.CardsService;
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

    @RequestMapping(method = RequestMethod.POST, value="")
    public String addCardToDB(@RequestBody Card card) {
        return cardsService.addCardToDB(card);
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
     * @return List of the cards
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{cardName}")
    public Card getCardByName(@PathVariable String cardName) {
        return cardsService.getCardByName(cardName);
    }
}