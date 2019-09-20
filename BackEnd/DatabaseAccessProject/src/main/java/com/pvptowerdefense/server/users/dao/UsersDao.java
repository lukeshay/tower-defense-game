package com.pvptowerdefense.server.users.dao;

import com.pvptowerdefense.server.users.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDao extends CrudRepository<User, String> {
    public User findUserById(String phoneId);
}