package com.pvptowerdefense.database.users.services;

import com.pvptowerdefense.database.users.dao.UsersDao;
import com.pvptowerdefense.database.users.models.User;
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
                new User("user1", "phoneId1", "firstName1", "lastName1", "email1", 1, 1),
                new User("user2", "phoneId2", "firstName2", "lastName2", "email2", 2, 2),
                new User("user3", "phoneId3", "firstName3", "lastName3", "email3", 3, 3),
                new User("user4", "phoneId4", "firstName4", "lastName4", "email4", 4, 4)
        );

        usersDao.saveAll(users);
    }

    public User getUserByPhoneId(String phoneId){
        return usersDao.getUserByPhoneId(phoneId);
    }

}
