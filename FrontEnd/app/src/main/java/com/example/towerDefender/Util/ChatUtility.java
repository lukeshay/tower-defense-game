package com.example.towerDefender.Util;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.towerDefender.Game.ChatBar;

public class ChatUtility {

    private static ChatBar chatBar;
    //todo: make a separate ChatUtility manager
    public static long timeChatMessageReceived;
    public static String lastChatMessageReceived;

    public static ChatBar getChatBar(){
        if(chatBar == null){
            chatBar = new ChatBar(CanvasUtility.getCanvas());
        }
        return chatBar;
    }

    public static void initializeChatBar(Canvas canvas){
        chatBar = new ChatBar(canvas);
    }

    /**
     * Draws the last received chat message to the canvas if it was received within the last 2.5 seconds
     * @param canvas the canvas to draw the message on
     * @param textPaint the text paint to use
     */
    public static void drawChatMessage(Canvas canvas, Paint textPaint){
        if(lastChatMessageReceived != null){
            if(System.currentTimeMillis() - timeChatMessageReceived < 2500){
                //TODO: make new manager with its own paint stored so we don't need to rely on textPaint parameter
                float temp = textPaint.getTextSize();
                textPaint.setTextSize(75);
                CanvasUtility.drawCenteredText(canvas, lastChatMessageReceived, textPaint);
                textPaint.setTextSize(temp);
            }
        }
    }

    /**
     * Draws the chat box
     * @param canvas the canvas to draw on
     */
    public static void drawChatPrompt(Canvas canvas){
        if(chatBar == null){
            chatBar = new ChatBar(canvas);
        }
        chatBar.draw(canvas);
    }
}
