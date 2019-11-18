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
        normalText = "Chat";
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(75);
        xPos = (int)((canvas.getWidth() / 2) - textPaint.measureText(normalText) / 2);
        yPos = 25;
        Rect temp = new Rect();
        textPaint.getTextBounds(normalText, 0, normalText.length(),  temp);
        unclickedBackgroundRect = new Rect(xPos, yPos, xPos + temp.width(), yPos + temp.height());
        String measureString = CHAT_OPTIONS[0] + " " + CHAT_OPTIONS[1] + " " + CHAT_OPTIONS[2];
        xPos = (int)((canvas.getWidth() / 2) - textPaint.measureText(measureString) / 2);
        yPos = 25;
        textPaint.getTextBounds(measureString, 0, measureString.length(), temp);
        clickedBackgroundRect = new Rect(xPos, yPos, xPos + temp.width(), yPos + temp.height());
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

    /**
     * Clicks on the chat bar, either opening up chat options if it has not been clicked, or sending the selected option if it has been.
     * @param xPos the xPosition of the click. Used for determining which chat option will be sent as a message
     */
    public void click(int xPos){
        if(clickedOn){
            clickedOn = false;
            //TODO: calculate which option.
            SocketUtilities.sendMessage("Message from opponent:" + CHAT_OPTIONS[0]);
            Log.i("CHATBAR", "sent message: " + CHAT_OPTIONS[0]);
        } else {
            clickedOn = true;
        }
    }

    public Rect getBoundingRectangle(){
        if(clickedOn){
            return clickedBackgroundRect;
        } else {
            return unclickedBackgroundRect;
        }
    }
}
