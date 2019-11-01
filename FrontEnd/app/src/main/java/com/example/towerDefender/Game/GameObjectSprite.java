package com.example.towerDefender.Game;

import android.graphics.Bitmap;
import android.graphics.Matrix;


public class GameObjectSprite extends Sprite {

    //TODO: get rid of velocity altogether

    /**
     * Constructs a new {@link GameObjectSprite}.
     * @param bitmap the {@link Bitmap} to use as the image
     * @param xPos the x position of the sprite
     * @param yPos the y position of the sprite
     */
    public GameObjectSprite(Bitmap bitmap, int xPos, int yPos){
        super(bitmap, xPos, yPos, 0, 0);
    }

    /**
     * Constructs a new {@link GameObjectSprite}
     * @param bitmap the {@link Bitmap} to use as the image
     * @param xPos the x position of the sprite
     * @param yPos the y position of the sprite
     * @param leftFacing a boolean that determines if the sprite faces left (true) or right (false)
     */
    public GameObjectSprite(Bitmap bitmap, int xPos, int yPos, boolean leftFacing){
        super(bitmap, xPos, yPos, 0, 0);
        if(!leftFacing){
            Matrix matrix = new Matrix();
            matrix.postScale(-1, 1, image.getWidth() / 2, image.getHeight() / 2);
            image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        }
    }

    /**
     * Updates the x and y values based upon its velocity.
     */
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
