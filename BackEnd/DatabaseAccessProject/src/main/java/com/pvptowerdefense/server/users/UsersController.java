package com.pvptowerdefense.server.users;

import com.pvptowerdefense.server.users.models.User;
import com.pvptowerdefense.server.users.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @RequestMapping(method = RequestMethod.POST, value = "/load")
    public void loadPresetUsersToDatabase(){
        usersService.loadPresetUsersToDatabase();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/load/{password}/{userId}")
    public void deleteUserById(@PathVariable String password, @PathVariable String userId){
        if(password.equals("123456")) {
            usersService.deleteUserById(userId);
        }
        else{ throw new IllegalArgumentException("You do not have authority to delete!"); }
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<String> addUserToDb(@Valid @RequestBody User userId){
        return ResponseEntity.ok("User add is a success");
    }

}
