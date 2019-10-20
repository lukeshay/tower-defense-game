package com.example.towerDefender.VolleyServices;

import android.content.Context;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.Deck;
import com.example.towerDefender.Game.Player;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import shared.PlayedCard;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonUtilsTests extends TestCase {
    private static String response = "[{\"name\":\"Card 1\",\"description\":\"Card 1 desc\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":50}," +
            "{\"name\":\"Card 2\",\"description\":\"Card 2 desc\",\"cost\":2,\"damage\":2,\"hitPoints\":2,\"speed\":2,\"type\":\"SPELL\",\"range\":40}," +
            "{\"name\":\"Card 3\",\"description\":\"Card 3 desc\",\"cost\":3,\"damage\":3,\"hitPoints\":3,\"speed\":3,\"type\":\"UNIT\",\"range\":5}," +
            "{\"name\":\"Card 4\",\"description\":\"Card 4 desc\",\"cost\":4,\"damage\":4,\"hitPoints\":4,\"speed\":4,\"type\":\"SPELL\",\"range\":100}," +
            "{\"name\":\"demo\",\"description\":\"Card 1 desc\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":0}]";


    public void testDeckFromJson(){
        Deck deck;
        Player player = mock(Player.class);
        Context context = mock(Context.class);
        when(player.getPlayerContext()).thenReturn(context);
        deck = new Deck(player, context, new ArrayList<Card>());
        deck.setDeck(new ArrayList<>(JsonUtils.jsonToCardArray(response)));
        Assert.assertTrue(deck.size() == 5);
    }

    public void testJsonFromPlayedCard(){
        try{
            Card card = new Card("Reaper", "basicReaper", 5, 5, 5, 5, "UNIT", 5);
            PlayedCard playedCard = new PlayedCard(card, 5, 15, "test Player");
            Assert.assertNotNull(playedCard);
            JsonUtils.playedCardToJson(playedCard);
            //{"name":"Reaper","description":"basicReaper","cost":5,"damage":5,"hitPoints":5,"speed":5,"type":"UNIT","range":5,"xValue":5,"yValue":15,"player":"test Player"}
            Assert.assertNotNull(JsonUtils.playedCardToJson(playedCard));
        } catch(Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    public void testPlayedCardFromJson(){
        String json = "{\"name\":\"Reaper\"," +
                "\"description\":\"basicReaper\"," +
                "\"cost\":5,\"damage\":5," +
                "\"hitPoints\":5," +
                "\"speed\":5," +
                "\"type\":" +
                "\"UNIT\"," +
                "\"range\":5," +
                "\"xValue\":5," +
                "\"yValue\":15," +
                "\"player\":\"test Player\"}";
        PlayedCard card = JsonUtils.jsonToPlayedCard(json);
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getName().equals("Reaper"));
        Assert.assertTrue(card.getDescription().equals("basicReaper"));
        Assert.assertTrue(card.getPlayer().equals("test Player"));
    }

    public void testPlayedCardArrayFromJson(){
        String json = "[{\"name\":\"Reaper\"," +
                "\"description\":\"basicReaper\"," +
                "\"cost\":5," +
                "\"damage\":5," +
                "\"hitPoints\":5," +
                "\"speed\":5," +
                "\"type\": \"UNIT\"," +
                "\"range\":5," +
                "\"xValue\":5," +
                "\"yValue\":15," +
                "\"player\":\"test Player\"}]";
        Collection<PlayedCard> cards = JsonUtils.jsonToPlayedCardArray(json);
        Assert.assertNotNull(cards);
    }
}
