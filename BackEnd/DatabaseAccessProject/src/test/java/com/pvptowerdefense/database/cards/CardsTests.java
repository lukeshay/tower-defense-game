package com.pvptowerdefense.database.cards;

import com.pvptowerdefense.database.DatabaseApplicationTests;
import com.pvptowerdefense.database.cards.models.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardsTests extends DatabaseApplicationTests {

    @Test
    public void testRequest() {
        template.postForObject("/cards/load", "", Card.class);
        Card card = template.getForObject("/cards/Card 1", Card.class);

        Assertions.assertAll(() -> Assertions.assertEquals("Card 1",
                card.getName()),
                () -> Assertions.assertEquals(1, card.getAd()),
                () -> Assertions.assertEquals(1, card.getHp()),
                () -> Assertions.assertEquals(1, card.getSpeed()),
                () -> Assertions.assertEquals(1, card.getCost())
        );
    }
}
