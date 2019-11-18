package com.example.towerDefender.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.towerDefender.SocketServices.SocketUtilities;

public class ChatBar {
    private String text;
    private int xPos, yPos;
    private Paint textPaint, backgroundPaint;
    private Rect backgroundRect;
    private boolean clickedOn = false;

    public ChatBar(Canvas canvas){
        text = "CHAT";
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(150);
        xPos = (int)((canvas.getWidth() / 2) - textPaint.measureText(text) / 2);
        yPos = 25;
        backgroundRect = new Rect(xPos, yPos, xPos + (int)textPaint.measureText(text),
                175);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLUE);
    }

    /**
     * Draws the chat bar to the canvas
     * @param canvas the canvas to draw on
     */
    public void draw(Canvas canvas){
        canvas.drawRect(backgroundRect, backgroundPaint);
        canvas.drawText(text, backgroundRect.left, backgroundRect.bottom, textPaint);
    }

    public void click(){
        SocketUtilities.sendMessage("From opponent:" + text);
    }

    public Rect getBoundingRectangle(){
        return backgroundRect;
    }
}
