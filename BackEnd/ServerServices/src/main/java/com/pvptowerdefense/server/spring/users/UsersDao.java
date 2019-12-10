package com.pvptowerdefense.server.spring.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Users dao.
 */
/*
 * Repository for the Users which also uses Crud Repository
 */
@Repository
public interface UsersDao extends CrudRepository<User, String> {
	/**
	 * Find user by phone id user.
	 *
	 * @param phoneId the phone id
	 * @return the user
	 */
	User findUserByPhoneId(String phoneId);
}
