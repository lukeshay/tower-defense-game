package com.pvptowerdefense.server.cards.dao;

import com.pvptowerdefense.server.cards.models.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsDao extends CrudRepository<Card, String> {
    Card getCardByName(String name);
}
