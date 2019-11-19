package com.example.towerDefender.Game;

import com.example.towerDefender.Card.PlayedCard;

import junit.framework.TestCase;

import org.junit.Assert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameManagerTests extends TestCase {

    private static final String connectedMessage = "{\"connected\":\"true\",\"side\":\"right\",\"matchUp\":\"false\"}";
    private static final String playedCardMessage = "[{\"name\":\"TEST-CARD-DO-NOT-USE\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"hitPoints\":5,\"speed\":0,\"type\":\"UNIT\",\"range\":100,\"xValue\":50,\"yValue\":390,\"player\":\"af70847f9f47804b\"}," +
            "{\"name\":\"TEST-CARD-DO-NOT-USE2\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"hitPoints\":5,\"speed\":0,\"type\":\"UNIT\",\"range\":100,\"xValue\":150,\"yValue\":120,\"player\":\"af70847f9f47804b\"}," +
            "{\"name\":\"TEST-CARD-DO-NOT-USE3\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"hitPoints\":5,\"speed\":0,\"type\":\"UNIT\",\"range\":100,\"xValue\":150,\"yValue\":660,\"player\":\"af70847f9f47804b\"}," +
            "{\"name\":\"TEST-CARD-DO-NOT-USE\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"hitPoints\":5,\"speed\":0,\"type\":\"UNIT\",\"range\":100,\"xValue\":1620,\"yValue\":390,\"player\":\"eed3b385f85ce0a4\"}," +
            "{\"name\":\"TEST-CARD-DO-NOT-USE2\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"hitPoints\":5,\"speed\":0,\"type\":\"UNIT\",\"range\":100,\"xValue\":1520,\"yValue\":120,\"player\":\"eed3b385f85ce0a4\"}," +
            "{\"name\":\"TEST-CARD-DO-NOT-USE3\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"hitPoints\":5,\"speed\":0,\"type\":\"UNIT\",\"range\":100,\"xValue\":1520,\"yValue\":660,\"player\":\"eed3b385f85ce0a4\"}]";
    private static final String gameOverMessage = "{game=loss}";


    public void testConnectMessageAndSetSide(){
        GameView gameView = mock(GameView.class);
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        Assert.assertFalse(manager.isConnected());
        manager.passMessageToManager(connectedMessage);
        Assert.assertTrue(manager.isConnected());
        Assert.assertTrue(manager.getPlayerSide().contains("right"));
    }

    public void testConvertPlayedCard(){
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        when(player.getUserId()).thenReturn("test User");
        manager.passMessageToManager(connectedMessage);
        Assert.assertEquals(0, manager.getPlayedCards().getWrappedCards().size());
        manager.passMessageToManager(playedCardMessage);
        for(PlayedCard card : manager.getPlayedCards().getPlayedCards()){
            System.out.println(card.getPlayer()+ ": " + card.getName());
        }
        Assert.assertEquals(6, manager.getPlayedCards().getPlayedCards().size());
    }

    public void testGameOverMessage(){
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        Assert.assertFalse(manager.isGameOver());
        manager.passMessageToManager(gameOverMessage);
        Assert.assertTrue(manager.isGameOver());
    }

}
