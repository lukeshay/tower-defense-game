package com.example.zachsexperiments;

import android.graphics.Bitmap;

public class CharacterSprite extends Sprite {

    public CharacterSprite(Bitmap bitmap){
        super(bitmap, 100, 100, 15, 15);
    }

    @Override
    public void update() {
        xStart += xVel;
        yStart += yVel;
        if (xStart >= screenWidth - image.getWidth() || xStart <= 0) {
            xVel = xVel * -1;
        }
        if (yStart >= screenHeight - image.getHeight() || yStart <= 0) {
            yVel = yVel * -1;
        }
    }

}
