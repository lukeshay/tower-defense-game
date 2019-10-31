package com.example.towerDefender.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class SpriteAnimation {
    private static final int FRAME_LENGTH_MILLIS = 500;
    private int frameWidth;
    private int frameHeight;
    private int frameCount;
    private int currentFrame;
    private Bitmap spriteSheet;
    private long lastFrameChange;
    private Rect frameToDraw;
    /**
     * Constructs a new sprite animation based on the provided spritesheet
     * @param bitmap the spritesheet
     * @param frameWidth the width of each frame
     * @param frameHeight the height of each frame
     * @param frameCount the number of frames
     */
    public SpriteAnimation(Bitmap bitmap, int frameWidth, int frameHeight, int frameCount){
        this.frameWidth = Sprite.normalizedInventorySize;
        this.frameHeight = (frameWidth / frameHeight) * Sprite.normalizedInventorySize;
        this.frameCount = frameCount;
        this.currentFrame = 0;
        Bitmap.createScaledBitmap(bitmap, this.frameWidth, this.frameHeight, false);
        this.spriteSheet = bitmap;
        this.frameToDraw = new Rect(
                0,
                0,
                this.frameWidth,
                this.frameHeight);
        lastFrameChange = System.currentTimeMillis();
        this.spriteSheet = Bitmap.createScaledBitmap(spriteSheet, this.frameWidth * frameCount, this.frameHeight, false);
    }

    /**
     * Draws the currrent frame of this animation to the provided canvas at the provided position
     * @param canvas the {@link Canvas} to draw on
     * @param x the x coordinate to draw at
     * @param y the y coordinate to draw at
     */
    public void draw(Canvas canvas, int x, int y){
        getCurrentFrame();
        RectF whereToDraw = new RectF(x, y, x + frameWidth, y + frameHeight);
        canvas.drawBitmap(spriteSheet, frameToDraw, whereToDraw, null);
    }

    public void getCurrentFrame(){
        long time = System.currentTimeMillis();
        if(time > lastFrameChange + FRAME_LENGTH_MILLIS){
            lastFrameChange = time;
            currentFrame++;
            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
        }
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
    }

}
