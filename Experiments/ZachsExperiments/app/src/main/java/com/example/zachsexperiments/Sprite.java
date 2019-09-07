package com.example.zachsexperiments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {
    private Bitmap image;
    private int xPos, yPos;
    private float xVel, yVel;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Sprite(Bitmap bitmap){
        image = bitmap;
        xPos = 100;
        yPos = 100;
        xVel = 15;
        yVel = 15;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, xPos, yPos, null);
    }

    public void update() {
        xPos += xVel;
        yPos += yVel;
        if (xPos >= screenWidth - image.getWidth() || xPos <= 0) {
            xVel = xVel * -1;
        }
        if (yPos >= screenHeight - image.getHeight() || yPos <= 0) {
            yVel = yVel * -1;
        }

    }
}
