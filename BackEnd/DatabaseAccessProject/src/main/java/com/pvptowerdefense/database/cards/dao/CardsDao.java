package com.pvptowerdefense.database.cards.dao;

import com.pvptowerdefense.database.cards.models.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsDao extends CrudRepository<Card, Long> {
    Card getCardByName(String name);
}
