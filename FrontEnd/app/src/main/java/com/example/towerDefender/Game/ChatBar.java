package com.example.towerDefender.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.towerDefender.SocketServices.SocketUtilities;

public class ChatBar {
    private String normalText;
    private static final String[] CHAT_OPTIONS = {"Hello!", "Nice!", "Good game."};
    private int xPos, yPos;
    private Paint textPaint, backgroundPaint;
    private Rect unclickedBackgroundRect, clickedBackgroundRect;
    private boolean clickedOn = false;

    public ChatBar(Canvas canvas){
        normalText = "CHAT";
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(150);
        xPos = (int)((canvas.getWidth() / 2) - textPaint.measureText(normalText) / 2);
        yPos = 25;
        unclickedBackgroundRect = new Rect(xPos, yPos, xPos + (int)textPaint.measureText(normalText),
                175);
        xPos = (int)((canvas.getWidth() / 2) - textPaint.measureText(CHAT_OPTIONS[0] + " " + CHAT_OPTIONS[1] + " " + CHAT_OPTIONS[2]) / 2);
        yPos = 25;
        clickedBackgroundRect = new Rect(xPos, yPos, xPos + (int)textPaint.measureText(CHAT_OPTIONS[0] + " " + CHAT_OPTIONS[1] + " " + CHAT_OPTIONS[2]),
                175);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLUE);
    }

    /**
     * Draws the chat bar to the canvas
     * @param canvas the canvas to draw on
     */
    public void draw(Canvas canvas){
        if(!clickedOn){
            canvas.drawRect(unclickedBackgroundRect, backgroundPaint);
            canvas.drawText(normalText, unclickedBackgroundRect.left, unclickedBackgroundRect.bottom, textPaint);
        } else {
            canvas.drawRect(clickedBackgroundRect, backgroundPaint);
            //todo: inidividual box for each chat word
            canvas.drawText(CHAT_OPTIONS[0] + " " + CHAT_OPTIONS[1] + " " + CHAT_OPTIONS[2],
                    clickedBackgroundRect.left, clickedBackgroundRect.bottom, textPaint);
        }

    }

    public void click(){
        SocketUtilities.sendMessage("Message from opponent:" + normalText);
        Log.i("CHATBAR", "sent message");
        if(clickedOn){
            clickedOn = false;
        } else {
            clickedOn = true;
        }
    }

    public Rect getBoundingRectangle(){
        return unclickedBackgroundRect;
    }
}
