package com.pvptowerdefense.server.spring.users;

import com.pvptowerdefense.server.spring.cards.Card;
import com.pvptowerdefense.server.spring.decks.Deck;
import com.pvptowerdefense.server.spring.users.User;
import com.pvptowerdefense.server.spring.cards.CardsService;
import com.pvptowerdefense.server.spring.decks.DecksService;
import com.pvptowerdefense.server.spring.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Users controller.
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    /**
     * Creates an instance of usersService which does the majority of the functionality
     */
    private UsersService usersService;

    private DecksService decksService;

    private CardsService cardsService;

	/**
	 * Constructs a usersService to access Users database table
	 *
	 * @param autowiredUsersService - autowired instance of the UsersService
	 * @param autowiredDecksService the autowired decks service
	 * @param autowiredCardsService the autowired cards service
	 */
	@Autowired
    public UsersController(final UsersService autowiredUsersService, final DecksService autowiredDecksService, final CardsService autowiredCardsService) {
		usersService = autowiredUsersService;
		decksService = autowiredDecksService;
		cardsService = autowiredCardsService;
    }

	/**
	 * Returns a list of all the users
	 *
	 * @return list of all users
	 */
	@RequestMapping(method = RequestMethod.GET, value = "")
    public List<User> getAllUsers () {
        return usersService.getAllUsers();
    }

	/**
	 * Gets user by id.
	 *
	 * @param userId the user id
	 * @return the user by id
	 */

	/**
	 * Loads a hardcoded list of users to the database
	 *
	 * @return Success message
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/load")
	public Map<String, Boolean> loadPresetUsersToDatabase(){
		usersService.loadPresetUsersToDatabase();
		return successMap();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public User getUserById(@PathVariable String userId) {
		return usersService.findUserById(userId);
    }


	/**
	 * Gets all decks.
	 *
	 * @return the all decks
	 */
	/*
     * Returns a list of all the users
     * @return list of all users
     */
    @RequestMapping(method = RequestMethod.GET, value = "/deck")
    public List<Deck> getAllDecks() {
        return decksService.getAllDecks();
    }

	/**
	 * Updates the given user in the database.
	 *
	 * @param user The user information in JSON format
	 * @return Success message
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "")
    public Map<String, Boolean> updateUserInDb(@RequestBody User user) {
        usersService.updateUser(user);
        return successMap();
    }

	/**
	 * Deletes a user given their phoneId and the correct password
	 *
	 * @param password - String containing a password needed to delete
	 * @param userId   - String containing the user's phoneId
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{password}/{userId}")
    public void deleteUserById(@PathVariable String password, @PathVariable String userId){
        if(password.equals("123456")) {
            usersService.deleteUserById(userId);
        }
        else{ throw new IllegalArgumentException("You do not have authority to delete!"); }
    }

	/**
	 * Adds a user to the database
	 *
	 * @param userId - User's phoneId
	 * @return - Success message
	 */
	@RequestMapping(method = RequestMethod.POST, value = "")
    public Map addUserToDb(@Valid @RequestBody User userId){
        usersService.addUserToDb(userId);
        usersService.addCardToOwnedCards(userId.getPhoneId(), cardsService.getCardByName("Blob"));
        return successMap();
    }

	/**
	 * Adds an empty deck to the database
	 *
	 * @param deckName - name for the deck
	 * @param userId   - ID for the user
	 * @return the map
	 */
	@RequestMapping(method = RequestMethod.POST, value = "{userId}/deck/{deckName}")
    public Map addEmptyDeck(@PathVariable String deckName, @PathVariable String userId){
        if(decksService.getTotalDecksPerUser(userId) >= 3){
            throw new IllegalArgumentException("User already has 3 decks!");
        }
        else {
            Card card = cardsService.getCardByName("Blob");
            decksService.addEmptyDeck(deckName, userId, card);
        }
        return successMap();
    }

	/**
	 * Returns how many decks a user has
	 *
	 * @param userId - the user's Id
	 * @return total amount of decks
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{userId}/totalDecks")
    public int getTotalDecksPerUser(@PathVariable String userId){
        return decksService.getTotalDecksPerUser(userId);
    }

	/**
	 * Adds a card to a specific deck
	 *
	 * @param deckId   - deckId for the deck
	 * @param cardName - name of the card
	 * @return the map
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/deck/{deckId}/{cardName}")
    public Map addCardToDeck(@PathVariable int deckId, @PathVariable String cardName){
        if(usersService.userOwnsCard(decksService.getUserByDeckId(deckId), cardName)){
            decksService.addCardToDeck(cardsService.getCardByName(cardName), deckId);
        }
        else{
            throw new IllegalArgumentException("User does not own this card!");
        }
        return successMap();
    }

	/**
	 * Deletes a deck
	 *
	 * @param deckId - deckId to be deleted
	 * @return - success message
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/deck/delete/{deckId}")
    public Map deleteDeck(@PathVariable int deckId){
        decksService.deleteDeck(deckId);
        return successMap();
    }

	/**
	 * Deletes a card from a deck
	 *
	 * @param deckId   - deck id
	 * @param cardName - name of card
	 * @return success message
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "deck/deleteCard/{deckId}/{cardName}")
    public Map<String, Boolean> deleteCardFromDeck(@PathVariable int deckId, @PathVariable String cardName){
        Card card = cardsService.getCardByName(cardName);
        decksService.deleteCardFromDeck(deckId, card);
        return successMap();
    }

	/**
	 * Returns  a list of cards (deck)
	 *
	 * @param deckId - id of the deck
	 * @return - deck
	 */
	@RequestMapping(method = RequestMethod.GET, value = "deck/{deckId}")
    public List<Card> getDeck(@PathVariable int deckId){
        return decksService.getDeck(deckId);
    }

	/**
	 * Get the decks for a certain user
	 *
	 * @param userId - user's id
	 * @return list of decks
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{userId}/deck")
    public List<Deck> getUsersDeck(@PathVariable String userId){
        return decksService.getUsersDeck(userId);
    }

	/**
	 * Adds a card to user's list of owned cards
	 *
	 * @param userId   - user's Id
	 * @param cardName - name of card
	 * @return success message
	 */
	@RequestMapping(method = RequestMethod.POST, value = "{userId}/ownedCard/{cardName}")
    public Map addCardToOwnedCards(@PathVariable String userId, @PathVariable String cardName){
        Card card = cardsService.getCardByName(cardName);
        if(!usersService.userOwnsCard(userId, cardName)){
			usersService.addCardToOwnedCards(userId, card);
		}
        else{
        	throw new IllegalArgumentException("User already owns this card.");
		}
        return successMap();
    }

	/**
	 * Gets the number of trophies for a user
	 *
	 * @param userId - user's id
	 * @return number of trophies
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{userId}/trophies")
    public int getUsersTrophies(@PathVariable String userId){
        return usersService.getTrophies(userId);
    }

	/**
	 * Sets the user's trophy count
	 *
	 * @param userId - user's id
	 * @param num    - trophies
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "{userId}/trophies/{num}")
    public void setUsersTrophies(@PathVariable String userId, @PathVariable int num){
        usersService.setTrophies(userId, num);
    }

	/**
	 * Gets user's owned cards
	 *
	 * @param userId - user's id
	 * @return list of cards
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{userId}/ownedCards")
    public List<Card> getUsersCards(@PathVariable String userId){
        return usersService.getUsersCards(userId);
    }

	/**
	 * Helper method to create a success message in JSON format
	 *
	 * @return map containing a success message
	 */
	private Map<String, Boolean> successMap(){
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

}
