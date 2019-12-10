package com.pvptowerdefense.server.spring.users;

import com.pvptowerdefense.server.spring.cards.Card;
import com.pvptowerdefense.server.spring.decks.Deck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Users service.
 */
/*
 * User type service
 */
@Service
public class UsersService {
	private static Logger logger =
			LoggerFactory.getLogger(UsersService.class.getName());

	private UsersDao usersDao;

	/**
	 * Create a new instance of card service
	 *
	 * @param usersDao - the usersDao
	 */
	@Autowired
	public UsersService(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	/**
	 * Method to return all of the Users
	 *
	 * @return List of Users containing all the users
	 */
	public List<User> getAllUsers() {
		logger.info("getting all users");
		List<User> users = new ArrayList<>();
		usersDao.findAll().forEach(users::add);

		return users;
	}

	/**
	 * Method to get a user by their phoneId using the crud repository
	 *
	 * @param phoneId possibly for a User
	 * @return a User who's phoneID matches the given phoneID
	 */
	public User findUserById(String phoneId) {
		logger.info("finding user " + phoneId);
		return usersDao.findUserByPhoneId(phoneId);
	}

	/**
	 * Method to delete the user given their phoneID
	 *
	 * @param id possibly for a User
	 */
	public void deleteUserById(String id) {
		logger.info("deleting user " + id);
		usersDao.deleteById(id);
	}

	/**
	 * Method to add a User to the database
	 *
	 * @param id user to be added
	 */
	public void addUserToDb(User id) {
		logger.info("adding user " + id);
		if (!usersDao.existsById(id.getPhoneId())) {
			usersDao.save(id);
		}
		else {
			throw new IllegalArgumentException("This phone already has a user connected to it!");
		}
	}

	/**
	 * Method to update the user to the current version
	 *
	 * @param user - the user to be updated
	 */
	public void updateUser(User user) {
		logger.info("updating user: " + user.getPhoneId());
		usersDao.save(user);
	}

	/**
	 * Adds a card to list of owned cards
	 *
	 * @param userId - user's id
	 * @param card   - card to be added
	 */
	public void addCardToOwnedCards(String userId, Card card) {
		User user = usersDao.findUserByPhoneId(userId);
		user.getOwnedCards().add(card);
		usersDao.save(user);
	}

	/**
	 * Checks if user owns a card
	 *
	 * @param userId   - user's Id
	 * @param cardName - name of card
	 * @return true or false if owned or not
	 */
	public boolean userOwnsCard(String userId, String cardName) {
		User user = usersDao.findUserByPhoneId(userId);
		List<Card> userCards = user.getOwnedCards();
		for(Card cards : userCards){
			if(cards.getName().equals(cardName)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the user's trophy count
	 *
	 * @param userId - user's id
	 * @return # of trophies
	 */
	public int getTrophies(String userId) {
		return usersDao.findUserByPhoneId(userId).getTrophies();
	}

	/**
	 * Sets user's trophy count
	 *
	 * @param userId - user's id
	 * @param num    - trophies
	 */
	public void setTrophies(String userId, int num) {
		User user = usersDao.findUserByPhoneId(userId);
		user.setTrophies(num);
		usersDao.save(user);
	}

	/**
	 * Gets user's owned cards
	 *
	 * @param userId - user's id
	 * @return the users cards
	 */
	public List<Card> getUsersCards(String userId) {
		return usersDao.findUserByPhoneId(userId).getOwnedCards();
	}
}
