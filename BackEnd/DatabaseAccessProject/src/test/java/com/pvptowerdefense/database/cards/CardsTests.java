package com.pvptowerdefense.database.cards;

import com.pvptowerdefense.database.DatabaseApplicationTests;
import com.pvptowerdefense.database.cards.models.Card;
import org.junit.jupiter.api.Test;

public class CardsTests extends DatabaseApplicationTests {

    @Test
    public void testRequest() {
        Card card = template.getForObject("/cards/Card 1", Card.class);

    }
}
