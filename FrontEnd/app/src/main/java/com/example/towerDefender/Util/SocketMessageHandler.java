package com.example.towerDefender.Util;

import android.util.Log;

import com.example.towerDefender.Game.GameManager;
import com.example.towerDefender.SocketServices.SocketMessage;

public class SocketMessageHandler {

    public static void handleMessage(String message){
        if (message.contains("Message from opponent: ")) {
            Log.i("CHAT", "received message from opponent");
            ChatUtility.lastChatMessageReceived = message;
            ChatUtility.timeChatMessageReceived = System.currentTimeMillis();
        } else {
            SocketMessage socketMessage = JsonUtility.jsonToSocketMessage(message);
            if(!socketMessage.getWinner().trim().isEmpty()){
                if(socketMessage.getWinner().equals(GameManager.instance.getPlayer().getUserId())){
                    GameManager.instance.gameOver = true;
                    GameManager.instance.wonOrLost = true;
                } else {
                    GameManager.instance.gameOver = true;
                    GameManager.instance.wonOrLost = false;
                }
            }
            if(socketMessage.getGameState().equals("in-game") && !GameManager.instance.isConnected){
                Log.i("SOCKET_INFO", "Connected.");
                GameManager.instance.isConnected = true;
                GameManager.instance.initializeDeck();
            } else if(socketMessage.getGameState().equals("in-game")){
                try {
                    GameManager.instance.playedCards.addAll(socketMessage.getPlayedCards(), GameManager.instance);
                    //If the player side hasn't already been updated, go through and check
                    if(!GameManager.instance.playerSideSet){
                        if(socketMessage.getPlayerOneId().equals(GameManager.instance.getPlayer().getUserId())){
                            GameManager.instance.playerSide = "left";
                        } else{
                            GameManager.instance.playerSide = "right";
                        }
                        GameManager.instance.playerSideSet = true;
                    }
                } catch (Exception e){
                    Log.e("ERROR", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
