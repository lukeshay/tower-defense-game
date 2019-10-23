package com.pvptowerdefense.server.spring.services;

import com.pvptowerdefense.server.spring.models.Card;
import com.pvptowerdefense.server.spring.daos.UsersDao;
import com.pvptowerdefense.server.spring.models.Deck;
import com.pvptowerdefense.server.spring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/*
* User type service
 */
@Service
public class UsersService {

    private UsersDao usersDao;
    /*
    * Create a new instance of card service
    * @param usersDao - the usersDao
     */
    @Autowired
    public UsersService(UsersDao usersDao){
        this.usersDao = usersDao;
    }

    /*
    * Method to return all of the Users
    * @return List of Users containing all the users
     */
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        usersDao.findAll().forEach(users::add);

        return users;
    }

    /*
    * Method to create a hard-coded list of users and add them to the database
     */
    public void loadPresetUsersToDatabase(){
        List<User> users = Arrays.asList(
                new User("user1", "phoneId1", "firstName1", "lastName1", 
                        "email1", "User"),
                new User("user2", "phoneId2", "firstName2", "lastName2", "email2", "User"),
                new User("user3", "phoneId3", "firstName3", "lastName3", "email3", "User"),
                new User("user4", "phoneId4", "firstName4", "lastName4", "email4", "User")
        );

        usersDao.saveAll(users);
    }

    /*
    * Method to get a user by their phoneId using the crud repository
    * @param String phoneId - phoneID possibly for a User
    * @return a User who's phoneID matches the given phoneID
     */
    public User findUserById(String phoneId){
        return usersDao.findUserByPhoneId(phoneId);
    }

    /*
     * Method to delete the user given their phoneID
     * @param String phoneID - phoneID possibly for a User
     */
    public void deleteUserById(String id){
        usersDao.deleteById(id);
    }

    /*
     * Method to add a User to the database
     * @param User id - user to be added
     */
    public void addUserToDb(User id){
        if(!usersDao.existsById(id.getPhoneId())) {
            usersDao.save(id);
        }
        else{ throw new IllegalArgumentException("This phone already has a user connected to it!"); }
    }

    /*
     * Method to add an empty deck of cards for the user
     * @param String deckname - name for the deck
     * @param int deckId - id for the deck
     */
    public void addEmptyDeck(String deckName, int deckId){
        new Deck(new ArrayList<Card>(), deckName, deckId);
    }

}
