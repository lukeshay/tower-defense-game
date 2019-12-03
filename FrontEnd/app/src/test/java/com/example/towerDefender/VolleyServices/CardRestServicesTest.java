package com.example.towerDefender.VolleyServices;

import com.example.towerDefender.Card.Card;

import junit.framework.TestCase;

import org.junit.Assert;


public class CardRestServicesTest extends TestCase {

    public void testCardByName(){
        Card card = CardRestServices.getCardByName("Blob");
        Assert.assertNotNull(card);
        Assert.assertEquals("Blob", card.cardName);
    }

    public void testNonExistantCardByName(){
        Card card = CardRestServices.getCardByName("this card doesn't exist");
        Assert.assertNull(card);
    }

}
