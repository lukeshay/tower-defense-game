package com.example.towerDefender.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.Spinner;

public class SpriteAnimation {
    /**
     * The type of animation this is. Is it for moving, attacking, etc.?
     */
    public Sprite.SPRITE_STATUS animationType;

    private static final int FRAME_LENGTH_MILLIS = 200;
    private int frameWidth;
    private int frameHeight;
    private int frameCount;
    private int currentFrame;
    public Bitmap spriteSheet;
    private long lastFrameChange;
    private Rect frameToDraw;
    /**
     * Constructs a new sprite animation based on the provided spritesheet.
     * The spritesheet must consist of the provided number of frames, EACH A SQUARE.
     * By default, this constructor assumes a moving animation. The other constructor for {@link SpriteAnimation} should be used if the animation is not a walking animation.
     * @param bitmap the spritesheet
     * @param frameCount the number of frames
     */
    public SpriteAnimation(Bitmap bitmap, int frameCount){
        this.animationType = Sprite.SPRITE_STATUS.MOVING;
        this.frameWidth = Sprite.normalizedInventorySize;
        this.frameHeight =  Sprite.normalizedInventorySize;
        this.frameCount = frameCount;
        this.currentFrame = 0;
        this.spriteSheet = bitmap;
        this.frameToDraw = new Rect(
                0,
                0,
                this.frameWidth,
                this.frameHeight);
        lastFrameChange = System.currentTimeMillis();
        this.spriteSheet = Bitmap.createScaledBitmap(spriteSheet, this.frameWidth * this.frameCount, this.frameHeight, false);
    }


    /**
     * Constructs a new sprite animation based on the provided spritesheet.
     * The spritesheet must consist of the provided number of frames, EACH A SQUARE.
     * @param bitmap the spritesheet
     * @param frameCount the number of frames
     * @param animationType the {@link Sprite.SPRITE_STATUS} associated with this animation
     */
    public SpriteAnimation(Bitmap bitmap, int frameCount, Sprite.SPRITE_STATUS animationType){
        this.animationType = animationType;
        this.frameWidth = Sprite.normalizedInventorySize;
        this.frameHeight =  Sprite.normalizedInventorySize;
        this.frameCount = frameCount;
        this.currentFrame = 0;
        this.spriteSheet = bitmap;
        this.frameToDraw = new Rect(
                0,
                0,
                this.frameWidth,
                this.frameHeight);
        lastFrameChange = System.currentTimeMillis();
        this.spriteSheet = Bitmap.createScaledBitmap(spriteSheet, this.frameWidth * this.frameCount, this.frameHeight, false);
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

    /**
     * Returns the next frame that should be drawn.
     */
    private void getCurrentFrame(){
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
