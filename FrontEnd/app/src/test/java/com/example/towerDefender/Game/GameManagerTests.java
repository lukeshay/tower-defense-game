package com.example.towerDefender.Game;

import junit.framework.TestCase;

import org.junit.Assert;

import static org.mockito.Mockito.mock;

public class GameManagerTests extends TestCase {

    private static final String connectedMessage = "{\"connected\":\"true\",\"side\":\"right\",\"matchUp\":\"false\"}";
    private static final String playedCardMessage = "{\"playerOneId\":\"fc6ac\",\"playerTwoId\":\"4f007\",\"playerOneMana\":5," +
            "\"playerTwoMana\":5,\"playerOneTrophies\":105,\"playerTwoTrophies\":65,\"winner\":\"\",\"gameState\":" +
            "\"post-game\",\"turnState\":\"\",\"currentTime\":1574185717914,\"playedCards\":[{\"name\":\"tower1\",\"description\"" +
            ":\"tower\",\"cost\":0,\"damage\":1,\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\"," +
            "\"range\":300,\"xValue\":50,\"yValue\":390,\"player\":\"fc6ac\",\"attacking\":false,\"cardAttackingDistance\":2147483647}," +
            "{\"name\":\"tower2\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"currentHitPoints\":100,\"totalHitPoints\":100," +
            "\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":150,\"yValue\":120,\"player\":\"fc6ac\",\"attacking\":false," +
            "\"cardAttackingDistance\":2147483647},{\"name\":\"tower3\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"currentHitPoints\":100" +
            ",\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":150,\"yValue\":660,\"player\":\"fc6ac\"," +
            "\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"tower4\",\"description\":\"tower\",\"cost\":0,\"damage\":1" +
            ",\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":1620,\"yValue\":390,\"player\"" +
            ":\"4f007\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"tower5\",\"description\":\"tower\",\"cost\":0,\"damage\":" +
            "1,\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":1520,\"yValue\":120,\"player\":" +
            "\"4f007\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"tower6\",\"description\":\"tower\",\"cost\":0,\"damage\":1," +
            "\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":1520,\"yValue\":660,\"player\":\"4f007\"," +
            "\"attacking\":false,\"cardAttackingDistance\":2147483647}],\"serverMessage\":\"the game is over\"}\n";

    private static final String gameOverMessage = "{\"playerOneId\":\"fc6ac\",\"playerTwoId\":\"4f007\",\"playerOneMana\":5," +
            "\"playerTwoMana\":5,\"playerOneTrophies\":105,\"playerTwoTrophies\":65,\"winner\":\"fc6ac\",\"gameState\":" +
            "\"post-game\",\"turnState\":\"\",\"currentTime\":1574185717914,\"playedCards\":[{\"name\":\"tower1\",\"description\"" +
            ":\"tower\",\"cost\":0,\"damage\":1,\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\"," +
            "\"range\":300,\"xValue\":50,\"yValue\":390,\"player\":\"fc6ac\",\"attacking\":false,\"cardAttackingDistance\":2147483647}," +
            "{\"name\":\"tower2\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"currentHitPoints\":100,\"totalHitPoints\":100," +
            "\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":150,\"yValue\":120,\"player\":\"fc6ac\",\"attacking\":false," +
            "\"cardAttackingDistance\":2147483647},{\"name\":\"tower3\",\"description\":\"tower\",\"cost\":0,\"damage\":1,\"currentHitPoints\":100" +
            ",\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":150,\"yValue\":660,\"player\":\"fc6ac\"," +
            "\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"tower4\",\"description\":\"tower\",\"cost\":0,\"damage\":1" +
            ",\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":1620,\"yValue\":390,\"player\"" +
            ":\"4f007\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"tower5\",\"description\":\"tower\",\"cost\":0,\"damage\":" +
            "1,\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":1520,\"yValue\":120,\"player\":" +
            "\"4f007\",\"attacking\":false,\"cardAttackingDistance\":2147483647},{\"name\":\"tower6\",\"description\":\"tower\",\"cost\":0,\"damage\":1," +
            "\"currentHitPoints\":100,\"totalHitPoints\":100,\"speed\":0,\"type\":\"UNIT\",\"range\":300,\"xValue\":1520,\"yValue\":660,\"player\":\"4f007\"," +
            "\"attacking\":false,\"cardAttackingDistance\":2147483647}],\"serverMessage\":\"the game is over\"}\n";

/* TODO: fix because of new protocol
    public void testConvertPlayedCard(){
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        when(player.getUserId()).thenReturn("test User");
        Assert.assertEquals(0, manager.getPlayedCards().getWrappedCards().size());
        manager.passMessageToManager(playedCardMessage);
        for(PlayedCard card : manager.getPlayedCards().getPlayedCards()){
            System.out.println(card.getPlayer()+ ": " + card.getName());
        }
        Assert.assertEquals(6, manager.getPlayedCards().getPlayedCards().size());
    }
*/
    public void testGameOverMessage(){
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        Assert.assertFalse(manager.isGameOver());
        manager.passMessageToManager(gameOverMessage);
        Assert.assertTrue(manager.isGameOver());
    }

    public void testGameNotOverMessage(){
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        Assert.assertFalse(manager.isGameOver());
        manager.passMessageToManager(playedCardMessage);
        Assert.assertFalse(manager.isGameOver());
    }
}
