package com.pvptowerdefense.server.spring;

import com.pvptowerdefense.server.spring.models.User;
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
        successMap();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{password}/{userId}")
    public void deleteUserById(@PathVariable String password, @PathVariable String userId){
        if(password.equals("123456")) {
            usersService.deleteUserById(userId);
        }
        else{ throw new IllegalArgumentException("You do not have authority to delete!"); }
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public Map addUserToDb(@Valid @RequestBody User userId){
        usersService.addUserToDb(userId);
        return successMap();
    }

    public Map<String, Boolean> successMap(){
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

}
