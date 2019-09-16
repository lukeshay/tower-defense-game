package com.pvptowerdefense.database.cards.services;

import com.pvptowerdefense.database.cards.dao.CardsDao;
import com.pvptowerdefense.database.cards.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CardsService {
    private CardsDao cardsDao;

    @Autowired
    public CardsService(CardsDao cardsDao) {
        this.cardsDao = cardsDao;
    }

    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        cardsDao.findAll().forEach(cards::add);

        return cards;
    }

    public void loadCardsToDatabase() {
        List<Card> cards = Arrays.asList(
                new Card("Card 1", 1, 1, 1, 1),
                new Card("Card 2", 2, 2, 2, 2),
                new Card("Card 3", 3, 3, 3, 3),
                new Card("Card 4", 4, 4, 4, 4)
        );

        cardsDao.saveAll(cards);
    }

    public Card getCardByName(String cardName) {
        return cardsDao.getCardByName(cardName);
    }
}
