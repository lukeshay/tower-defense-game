package com.example.towerDefender.Utility;

import android.content.Context;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.Deck;
import com.example.towerDefender.Game.Player;

import junit.framework.TestCase;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.towerDefender.Card.PlayedCard;
import com.example.towerDefender.SocketServices.SocketMessage;
import com.example.towerDefender.Util.JsonUtility;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonUtilityTest extends TestCase {
    private static String response = "[{\"name\":\"Card 1\",\"description\":\"Card 1 desc\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":50}," +
            "{\"name\":\"Card 2\",\"description\":\"Card 2 desc\",\"cost\":2,\"damage\":2,\"hitPoints\":2,\"speed\":2,\"type\":\"SPELL\",\"range\":40}," +
            "{\"name\":\"Card 3\",\"description\":\"Card 3 desc\",\"cost\":3,\"damage\":3,\"hitPoints\":3,\"speed\":3,\"type\":\"UNIT\",\"range\":5}," +
            "{\"name\":\"Card 4\",\"description\":\"Card 4 desc\",\"cost\":4,\"damage\":4,\"hitPoints\":4,\"speed\":4,\"type\":\"SPELL\",\"range\":100}," +
            "{\"name\":\"demo\",\"description\":\"Card 1 desc\",\"cost\":1,\"damage\":1,\"hitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":0}]";

    private static String socketMessageResponse = "{\"playerOneId\":\"fc6acf074a497842\",\"playerTwoId\":\"4f0077cc4d8874ec\"," +
            "\"playerOneMana\":5,\"playerTwoMana\":5,\"winner\":\"\",\"gameState\":\"in-game\",\"turnState\":\"move\"," +
            "\"currentTime\":20857,\"playedCards\":[{\"name\":\"tower1\",\"description\":\"tower\",\"cost\":0,\"damage\":1," +
            "\"currentHitPoints\":98,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":50," +
            "\"yValue\":390,\"player\":\"fc6acf074a497842\",\"attacking\":false,\"cardAttacking\":\"Lesser Fire Golem@2\"" +
            ",\"cardAttackingDistance\":2147483647},{\"name\":\"tower2\",\"description\":\"tower\",\"cost\":0,\"damage\":1," +
            "\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":150,\"yValue\":120," +
            "\"player\":\"fc6acf074a497842\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"tower3\",\"description\":" +
            "\"tower\",\"cost\":0,\"damage\":1,\"currentHitPoints\":70,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300," +
            "\"xValue\":150,\"yValue\":660,\"player\":\"fc6acf074a497842\",\"attacking\":false,\"cardAttacking\":\"Lesser Minotaur@0\"," +
            "\"cardAttackingDistance\":2147483647},{\"name\":\"tower4\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"currentHitPoints\":100," +
            "\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":1620,\"yValue\":390,\"player\":\"4f0077cc4d8874ec\"," +
            "\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"tower5\",\"description\":\"tower\",\"cost\":0,\"damage\":1," +
            "\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":1520,\"yValue\":120,\"player\"" +
            ":\"4f0077cc4d8874ec\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"tower6\",\"description\":\"tower\",\"cost\":0," +
            "\"damage\":1,\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":1520,\"yValue\":660," +
            "\"player\":\"4f0077cc4d8874ec\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"demo@3\",\"description\":\"Card 1 desc\"," +
            "\"cost\":1,\"damage\":1,\"currentHitPoints\":1,\"totalHitPoints\":1,\"speed\":1,\"type\":\"UNIT\",\"range\":0,\"xValue\":908,\"yValue\":599,\"player\"" +
            ":\"4f0077cc4d8874ec\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"Fire Golem@4\",\"description\":\"Medium fire golem." +
            "Medium damage, high HP, medium range\",\"cost\":2,\"damage\":2,\"currentHitPoints\":4,\"totalHitPoints\":4,\"speed\":2,\"type\":\"UNIT\",\"range\":250," +
            "\"xValue\":457,\"yValue\":254,\"player\":\"4f0077cc4d8874ec\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"Blob@5\",\"description\":" +
            "\"Weak ground troop.\",\"cost\":1,\"damage\":2,\"currentHitPoints\":5,\"totalHitPoints\":5,\"speed\":2,\"type\":\"UNIT\",\"range\":250,\"xValue\":647,\"yValue\"" +
            ":817,\"player\":\"4f0077cc4d8874ec\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"Golem King@6\",\"description\":\"Strong golem. " +
            "Medium damage, medium range, high hp\",\"cost\":2,\"damage\":2,\"currentHitPoints\":15,\"totalHitPoints\":15,\"speed\":2,\"type\":\"UNIT\",\"range\":250," +
            "\"xValue\":895,\"yValue\":365,\"player\":\"4f0077cc4d8874ec\",\"attacking\":false,\"cardAttackingDistance\":2147483647}],\"serverMessage\":\"\"}";


    public void testDeckFromJson(){
        Deck deck;
        Player player = mock(Player.class);
        Context context = mock(Context.class);
        when(player.getPlayerContext()).thenReturn(context);
        deck = new Deck(player, context, new ArrayList<Card>());
        deck.setDeck(new ArrayList<>(JsonUtility.jsonToCardArray(response)));
        Assert.assertTrue(deck.size() == 5);
    }

    public void testJsonFromPlayedCard(){
        try{
            Card card = new Card("Reaper", "basicReaper", 5, 5, 5, 5, "UNIT", 5);
            PlayedCard playedCard = new PlayedCard(card, 5, 15, "test Player");
            Assert.assertNotNull(playedCard);
            JsonUtility.playedCardToJson(playedCard);
            Assert.assertNotNull(JsonUtility.playedCardToJson(playedCard));
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
        PlayedCard card = JsonUtility.jsonToPlayedCard(json);
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
        Collection<PlayedCard> cards = JsonUtility.jsonToPlayedCardArray(json);
        Assert.assertNotNull(cards);
    }

    public void testSocketMessageComponentsFromResponse(){
        SocketMessage socketMessage = JsonUtility.jsonToSocketMessage(socketMessageResponse);
        Assert.assertEquals(socketMessage.getGameState(), "in-game");
        Assert.assertEquals(socketMessage.getPlayerOneId(), "fc6acf074a497842");
        Assert.assertEquals(socketMessage.getPlayerTwoId(), "4f0077cc4d8874ec");
        Assert.assertTrue(socketMessage.getServerMessage().trim().isEmpty());
    }

    public void testSocketMessagePlayedCards(){
        SocketMessage socketMessage = JsonUtility.jsonToSocketMessage(socketMessageResponse);
        List<PlayedCard> cards = socketMessage.getPlayedCards();
        Assert.assertFalse(cards.isEmpty());
        Assert.assertEquals(cards.size(), 10);
    }
}
