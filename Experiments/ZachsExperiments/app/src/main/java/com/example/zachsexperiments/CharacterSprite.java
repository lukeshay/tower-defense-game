package com.example.zachsexperiments;

import android.graphics.Bitmap;

import java.util.Random;

public class CharacterSprite extends Sprite {

    public CharacterSprite(Bitmap bitmap){
        super(bitmap, 100, 100, 15, new Random().nextInt() % 15);
    }

    public CharacterSprite(Bitmap bitmap, int xPos, int yPos){
        super(bitmap, xPos, yPos, 15, new Random().nextInt() % 15);
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
