package com.example.towerDefender.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Sprite {
    public Bitmap image;
    public int xStart, yStart, xEnd, yEnd;
    protected float xVel, yVel;
    protected static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    protected static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static int normalizedInventorySize = (screenWidth - 300) / 4;
    public static int normalizedButtonSize = (screenWidth - 500) / 5;

    public Sprite(Bitmap bitmap, int xStart, int yPos, int xVel, int yVel){
        image = bitmap;
        this.xStart = xStart;
        this.yStart = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        xEnd = xStart + image.getWidth();
        yEnd = yStart + image.getHeight();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, xStart, yStart, null);
    }

    public abstract void update();

    public int getxStart(){ return xStart; }
    public int getyStart(){ return yStart; }
    public int getxEnd(){ return xEnd; }
    public int getyEnd(){ return yEnd; }

}
