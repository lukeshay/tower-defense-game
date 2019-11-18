package com.example.towerDefender.Util;

import android.graphics.Canvas;
import android.graphics.Paint;

public class CanvasUtility {

    public static void drawCenteredText(Canvas canvas, String text, Paint textPaint){
        int xPos = (int)((canvas.getWidth() / 2) - textPaint.measureText(text) / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        canvas.drawText(text, xPos, yPos, textPaint);
    }
}
