package com.example.towerDefender.Util;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.towerDefender.Game.ChatBar;

public class ChatUtility {

    private static ChatBar chatBar;
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
     * @param canvas the canvas to draw the message on\
     */
    public static void drawChatMessage(Canvas canvas){
        if(lastChatMessageReceived != null){
            if(System.currentTimeMillis() - timeChatMessageReceived < 2500){
                Paint textPaint = CanvasUtility.textPaint;
                float tempTextSize = textPaint.getTextSize();
                int tempAlpha = textPaint.getAlpha();
                textPaint.setTextSize(75);
                textPaint.setAlpha(220);
                CanvasUtility.drawChat(canvas, lastChatMessageReceived, textPaint);
                textPaint.setTextSize(tempTextSize);
                textPaint.setAlpha(tempAlpha);
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
