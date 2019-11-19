package com.example.towerDefender.Utility;

import com.example.towerDefender.Game.GameManager;
import com.example.towerDefender.Game.Player;
import com.example.towerDefender.Util.ChatUtility;

import junit.framework.TestCase;

import org.junit.Assert;

import static org.mockito.Mockito.mock;

public class ChatUtilityTest extends TestCase {
    private final String goodGameMessage = "Message from opponent: Good game.";
    private static final String connectedMessage = "{\"connected\":\"true\",\"side\":\"right\",\"matchUp\":\"true\"}";

    public void testGameOverMessage(){
        Player player = mock(Player.class);
        GameManager manager = new GameManager(player, true);
        Assert.assertFalse(manager.isGameOver());
        manager.passMessageToManager(goodGameMessage);
        Assert.assertTrue(ChatUtility.lastChatMessageReceived.equals(goodGameMessage));
    }
}
