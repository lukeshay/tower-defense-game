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

@Service
public class UsersService {
    private UsersDao usersDao;

    @Autowired
    public UsersService(UsersDao usersDao){
        this.usersDao = usersDao;
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        usersDao.findAll().forEach(users::add);

        return users;
    }

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

    public User findUserById(String phoneId){
        return usersDao.findUserByPhoneId(phoneId);
    }

    public void deleteUserById(String id){
        usersDao.deleteById(id);
    }

    public void addUserToDb(User id){
        if(!usersDao.existsById(id.getPhoneId())) {
            usersDao.save(id);
        }
        else{ throw new IllegalArgumentException("This phone already has a user connected to it!"); }
    }

    public void addEmptyDeck(List<Card> deck, String deckName, int deckId){
        new Deck(deck, deckName, deckId);
    }

}
