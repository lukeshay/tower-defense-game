package com.example.towerDefender.Game;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.example.towerDefender.Game.Sprite;

import java.util.Random;

public class GameObjectSprite extends Sprite {

    public GameObjectSprite(Bitmap bitmap, int xPos, int yPos){
        super(bitmap, xPos, yPos, 15, new Random().nextInt() % 15);
    }

    public GameObjectSprite(Bitmap bitmap, int xPos, int yPos, boolean friendly){
        super(bitmap, xPos, yPos, 15, new Random().nextInt() % 15);
        if(!friendly){
            Matrix matrix = new Matrix();
            matrix.postScale(-1, 1, image.getWidth() / 2, image.getHeight() / 2);
            image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        }
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
