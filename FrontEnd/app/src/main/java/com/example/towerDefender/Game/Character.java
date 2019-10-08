package com.example.towerDefender.Game;

import android.graphics.Bitmap;

import com.example.towerDefender.Game.Sprite;

import java.util.Random;

public class Character extends Sprite {

    public Character(Bitmap bitmap, int xPos, int yPos){
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
