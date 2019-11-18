package com.example.towerDefender.Util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class CanvasUtility {

    /**
     * All draws to the canvas for information with position sent from the server must be normalized against this bound.
     */
    public static final int SERVER_X_BOUND = 1920;

    /**
     * Draws the provided text on the center of the provided {@link Canvas}
     * @param canvas the {@link Canvas} to draw on
     * @param text the text to draw
     * @param textPaint the {@link Paint} to use for the text
     */
    public static void drawCenteredText(Canvas canvas, String text, Paint textPaint){
        int xPos = (int)((canvas.getWidth() / 2) - textPaint.measureText(text) / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        canvas.drawText(text, xPos, yPos, textPaint);
    }

    /**
     * Draws the chat box
     * @param canvas the canvas to draw on
     * @param textPaint the paint ot use for the text
     */
    public static void drawChatPrompt(Canvas canvas, Paint textPaint){
        int xPos = (int)((canvas.getWidth() / 2) - textPaint.measureText("CHAT") / 2);
        int yPos = 25;
        Rect rect = new Rect(xPos, yPos, xPos + (int)textPaint.measureText("CHAT"), yPos + 200);
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        canvas.drawRect(rect, redPaint);
        canvas.drawText("CHAT", rect.left, rect.bottom, textPaint);
    }
}
