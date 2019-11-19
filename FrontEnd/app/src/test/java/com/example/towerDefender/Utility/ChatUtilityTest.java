package com.example.towerDefender.Utility;

import com.example.towerDefender.Game.GameManager;
import com.example.towerDefender.Game.Player;
import com.example.towerDefender.Util.ChatUtility;

import junit.framework.TestCase;

import org.junit.Assert;

import static org.mockito.Mockito.mock;

public class ChatUtilityTest extends TestCase {
    private final String goodGameMessage = "Message from opponent: Good game.";
    private final String niceMessage = "Message from opponent: Nice!";

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

    public void testGoodGameMessage(){
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        Assert.assertFalse(manager.isGameOver());
        manager.passMessageToManager(goodGameMessage);
        Assert.assertTrue(ChatUtility.lastChatMessageReceived.equals(goodGameMessage));
    }

    public void testNiceMessage(){
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        Assert.assertFalse(manager.isGameOver());
        manager.passMessageToManager(niceMessage);
        Assert.assertTrue(ChatUtility.lastChatMessageReceived.equals(niceMessage));
    }

    public void testCardMessage(){
        ChatUtility.lastChatMessageReceived = null; //other tests may overwrite value
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        Assert.assertFalse(manager.isGameOver());
        manager.passMessageToManager(playedCardMessage);
        //Message should NOT be updated
        Assert.assertNull(ChatUtility.lastChatMessageReceived);
    }
}
