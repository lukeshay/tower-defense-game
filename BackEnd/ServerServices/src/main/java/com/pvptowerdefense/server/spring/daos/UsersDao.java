package com.pvptowerdefense.server.spring.daos;

import com.pvptowerdefense.server.spring.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/*
 * Repository for the Users which also uses Crud Repository
 */
@Repository
public interface UsersDao extends CrudRepository<User, String> {
    public User findUserByPhoneId(String phoneId);
}
