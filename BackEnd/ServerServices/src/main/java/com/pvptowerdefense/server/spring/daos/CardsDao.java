package com.pvptowerdefense.server.spring.daos;

import com.pvptowerdefense.server.spring.models.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsDao extends CrudRepository<Card, String> {
    Card getCardByName(String name);
}
