package com.example.towerDefender.VolleyServices;

import android.content.Context;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.Deck;
import com.example.towerDefender.Game.Player;

import junit.framework.TestCase;

import org.junit.Assert;

import java.util.ArrayList;

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
}
