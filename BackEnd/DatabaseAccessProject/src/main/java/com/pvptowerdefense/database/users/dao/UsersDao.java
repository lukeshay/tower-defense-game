package com.pvptowerdefense.database.users.dao;

import com.pvptowerdefense.database.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDao extends JpaRepository<User, String> {
}
