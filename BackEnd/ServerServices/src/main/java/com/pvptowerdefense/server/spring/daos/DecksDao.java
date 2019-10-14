package com.pvptowerdefense.server.spring.daos;

import com.pvptowerdefense.server.spring.models.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecksDao extends CrudRepository<Deck, String> {
}
