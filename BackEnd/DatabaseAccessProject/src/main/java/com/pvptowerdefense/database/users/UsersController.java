package com.pvptowerdefense.database.users;

import com.pvptowerdefense.database.users.services.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {


    private UsersService usersService;

    @Autowired
    public UsersController(final UsersService autowiredUsersService) {
        this.usersService = autowiredUsersService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<User> getAllUsers () {
        return usersService.getAllUsers();
    }
}
