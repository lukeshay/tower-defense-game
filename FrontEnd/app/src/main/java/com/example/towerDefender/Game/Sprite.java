package com.example.towerDefender.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public abstract class Sprite {
    public Bitmap image;
    public int xStart, yStart, xEnd, yEnd;
    //TODO: get rid of velocities altogether, only updating position when server updates card list
    protected float xVel, yVel;
    public static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static int normalizedInventorySize = (screenWidth - 900) / 4;
    public static int normalizedButtonSize = screenWidth / 15;

    public Sprite(Bitmap bitmap, int xStart, int yPos, int xVel, int yVel){
        image = bitmap;
        this.xStart = xStart;
        this.yStart = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        xEnd = this.xStart + image.getWidth();
        yEnd = this.yStart + image.getHeight();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, xStart, yStart, null);
    }

    public abstract void update();

    /**
     * @return the starting x value of this {@link Sprite}'s {@link Bitmap}
     */
    public int getxStart(){ return xStart; }

    /**
     * @return the starting y value of this {@link Sprite}'s {@link Bitmap}
     */
    public int getyStart(){ return yStart; }

    /**
     * @return the ending x value of this {@link Sprite}'s {@link Bitmap}
     */
    public int getxEnd(){ return xEnd; }

    /**
     * @return the ending y value of this {@link Sprite}'s {@link Bitmap}
     */
    public int getyEnd(){ return yEnd; }

}
