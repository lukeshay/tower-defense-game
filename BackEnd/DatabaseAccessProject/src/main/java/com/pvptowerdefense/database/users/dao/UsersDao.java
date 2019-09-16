package com.pvptowerdefense.database.users.dao;

import com.pvptowerdefense.database.users.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersDao extends CrudRepository<User, String> {
    public User getUserByPhoneId(String phoneId);
}
