package com.pvptowerdefense.server.spring;

import com.pvptowerdefense.server.spring.models.Card;
import com.pvptowerdefense.server.spring.models.Deck;
import com.pvptowerdefense.server.spring.models.User;
import com.pvptowerdefense.server.spring.services.CardsService;
import com.pvptowerdefense.server.spring.services.DecksService;
import com.pvptowerdefense.server.spring.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    /*
     * Creates an instance of usersService which does the majority of the functionality
     */
    private UsersService usersService;

    private DecksService decksService;

    private CardsService cardsService;

    /*
     * Constructs a usersService to access Users database table
     * @param UsersService autoWiredUsersService - autowired instance of the UsersService
     */
    @Autowired
    public UsersController(final UsersService autowiredUsersService, final DecksService autowiredDecksService) {
        this.usersService = autowiredUsersService;
        this.decksService = autowiredDecksService;
    }

    /*
     * Returns a list of all the users
     * @return list of all users
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<User> getAllUsers () {
        return usersService.getAllUsers();
    }

    /*
     * Returns a list of all the users
     * @return list of all users
     */
    @RequestMapping(method = RequestMethod.GET, value = "/decks")
    public List<Deck> getAllDecks () {
        return decksService.getAllDecks();
    }

    /**
     * Updates the given user in the database.
     *
     * @param user The user information in JSON format
     * @return Success message
     */
    @RequestMapping(method = RequestMethod.PUT, value = "")
    public Map<String, Boolean> updateCardInDb(@RequestBody User user) {
        usersService.updateUser(user);
        return successMap();
    }

    /*
     * Loads a hardcoded list of users to the database
     * @return Success message
     */
    @RequestMapping(method = RequestMethod.POST, value = "/load")
    public void loadPresetUsersToDatabase(){
        usersService.loadPresetUsersToDatabase();
        successMap();
    }

    /*
     * Deletes a user given their phoneId and the correct password
     * @param password - String containing a password needed to delete
     * @param userId - String containing the user's phoneId
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{password}/{userId}")
    public void deleteUserById(@PathVariable String password, @PathVariable String userId){
        if(password.equals("123456")) {
            usersService.deleteUserById(userId);
        }
        else{ throw new IllegalArgumentException("You do not have authority to delete!"); }
    }

    /*
     * Adds a user to the database
     * @param userId - User's phoneId
     * @return - Success message
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    public Map addUserToDb(@Valid @RequestBody User userId){
        usersService.addUserToDb(userId);
        return successMap();
    }

    /*
     * Adds an empty deck to the database
     * @param deckName - name for the deck
     * @param deckId - ID for the deck
     */
    @RequestMapping(method = RequestMethod.POST, value = "{userId}/deck/{deckName}")
    public void addEmptyDeck(@PathVariable String deckName, @PathVariable String userId){
        decksService.addEmptyDeck(deckName, userId);
    }

    /**
     * Returns how many decks a user has
     * @param userId - the user's Id
     * @return total amount of decks
     */
    @RequestMapping(method = RequestMethod.GET, value = "{userId}/totalDecks")
    public int getTotalDecksPerUser(@PathVariable String userId){
        return decksService.getTotalDecksPerUser(userId);
    }

    /**
     * Adds a card to a specific deck
     * @param deckId - deckId for the deck
     * @param cardName - name of the card
     */
    @RequestMapping(method = RequestMethod.POST, value = "/deck/{deckId}/{cardName}")
    public void addCardToDeck(@PathVariable int deckId, @PathVariable String cardName){
        Card card = cardsService.getCardByName(cardName);
        decksService.addCardToDeck(card, deckId);
    }

    /*
     * Helper method to create a success message in JSON format
     * @return map containing a success message
     */
    public Map<String, Boolean> successMap(){
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

}
