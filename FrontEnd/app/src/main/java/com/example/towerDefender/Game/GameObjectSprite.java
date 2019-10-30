package com.example.towerDefender.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.towerDefender.R;


public class GameObjectSprite extends Sprite {

    private SpriteAnimation animation;

    //TODO: get rid of velocity altogether

    public GameObjectSprite(Bitmap bitmap, int xPos, int yPos){
        super(bitmap, xPos, yPos, 0, 0);
        this.animation = new SpriteAnimation(bitmap, bitmap.getWidth(), bitmap.getHeight(), 1);
    }

    public GameObjectSprite(Bitmap bitmap, int xPos, int yPos, boolean leftFacing){
        super(bitmap, xPos, yPos, 0, 0);
        if(!leftFacing){
            Matrix matrix = new Matrix();
            matrix.postScale(-1, 1, image.getWidth() / 2, image.getHeight() / 2);
            image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
            this.animation = new SpriteAnimation(image, bitmap.getWidth(), bitmap.getHeight(), 1);
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

    @Override
    public void draw(Canvas canvas){
        animation.draw(canvas, this.xStart, this.yStart);
    }

}
