package com.example.zachsexperiments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite extends Sprite {

    public CharacterSprite(Bitmap bitmap){
        super(bitmap, 100, 100, 15, 15);
    }

    @Override
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
