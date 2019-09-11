package com.pvptowerdefense.database.cards.dao;

import com.pvptowerdefense.database.cards.models.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardsDao extends CrudRepository<Card, String> {
}
