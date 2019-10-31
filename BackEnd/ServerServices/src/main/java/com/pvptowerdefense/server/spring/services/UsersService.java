package com.pvptowerdefense.server.spring.services;

import com.pvptowerdefense.server.spring.models.Card;
import com.pvptowerdefense.server.spring.daos.UsersDao;
import com.pvptowerdefense.server.spring.models.Deck;
import com.pvptowerdefense.server.spring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
	 * Method to create a hard-coded list of users and add them to the database
	 */
	public void loadPresetUsersToDatabase() {
		logger.info("loading users to database");
		List<User> users = Arrays.asList(
				new User("user1", "phoneId1", "firstName1", "lastName1",
						"email1", "User"),
				new User("user2", "phoneId2", "firstName2", "lastName2", "email2", "User"),
				new User("user3", "phoneId3", "firstName3", "lastName3", "email3", "User"),
				new User("user4", "phoneId4", "firstName4", "lastName4", "email4", "User")
		);

		usersDao.saveAll(users);
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
	 * Method to add an empty deck of cards for the user
	 *
	 * @param deckName - name for the deck
	 * @param deckId   - id for the deck
	 */
	public void addEmptyDeck(String deckName, int deckId) {
		new Deck(new ArrayList<Card>(), deckName, deckId);
	}

	/**
	 * Method to update the user to the current version
	 *
	 * @param user - the user to be updated
	 */
	public void updateUser(User user) {
		usersDao.save(user);
	}

	/**
	 * Returns whether the id is found in the database.
	 *
	 * @param id the id
	 * @return boolean boolean
	 */
	public boolean isUserInDatabase(String id) {
		return usersDao.findUserByPhoneId(id) != null;
	}
}
