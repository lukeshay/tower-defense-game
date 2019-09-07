package com.example.zachsexperiments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Sprite {
    protected Bitmap image;
    protected int xPos, yPos;
    protected float xVel, yVel;
    protected int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    protected int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Sprite(Bitmap bitmap, int xPos, int yPos, int xVel, int yVel){
        image = bitmap;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, xPos, yPos, null);
    }

    public abstract void update();
}
