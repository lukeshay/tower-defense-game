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

@Service
public class UsersService {
    private static Logger logger =
            LoggerFactory.getLogger(UsersService.class.getName());
    private UsersDao usersDao;

    @Autowired
    public UsersService(UsersDao usersDao){
        this.usersDao = usersDao;
    }

    public List<User> getAllUsers(){
        logger.info("getting all users");
        List<User> users = new ArrayList<>();
        usersDao.findAll().forEach(users::add);

        return users;
    }

    public void loadPresetUsersToDatabase(){
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

    public User findUserById(String phoneId){
        logger.info("finding user " + phoneId);
        return usersDao.findUserByPhoneId(phoneId);
    }

    public void deleteUserById(String id){
        logger.info("deleting user " + id);
        usersDao.deleteById(id);
    }

    public void addUserToDb(User id){
        logger.info("adding user " + id);
        if(!usersDao.existsById(id.getPhoneId())) {
            usersDao.save(id);
        }
        else{ throw new IllegalArgumentException("This phone already has a user connected to it!"); }
    }
}
