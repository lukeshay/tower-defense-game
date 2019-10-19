package com.example.towerDefender.VolleyServices;

import com.example.towerDefender.Card.Card;

import junit.framework.TestCase;

import org.junit.Assert;


public class CardRestServicesTest extends TestCase {

    public void testCardByName(){
        Card card = CardRestServices.getCardByName("Card 1");
        Assert.assertNotNull(card);
        Assert.assertEquals("Card 1", card.cardName);
    }

}
