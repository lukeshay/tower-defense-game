package com.example.towerDefender.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.towerDefender.Card.CardUtilities;


public class GameObjectSprite extends Sprite {

    private SpriteAnimation moveAnimation;
    private SpriteAnimation attackAnimation;
    private SpriteAnimation idleAnimation;
    private SpriteAnimation deathAnimation;

    @Deprecated
    public GameObjectSprite(Bitmap bitmap, int xPos, int yPos, boolean leftFacing){
        super(bitmap, xPos, yPos);
        this.status = SPRITE_STATUS.MOVING;
        if(!leftFacing){
            Matrix matrix = new Matrix();
            matrix.postScale(-1, 1, image.getWidth() / 2, image.getHeight() / 2);
            image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        }
        this.moveAnimation = new SpriteAnimation(image, 1);
    }

    /**
     * Constructs a new GameObjectSprite based off of a spritesheet
     * @param bitmap the {@link Bitmap} to base this {@link GameObjectSprite}'s walking animation on
     * @param xPos the x position of the sprite
     * @param yPos the y position of the sprite
     * @param leftFacing is this sprite facing left?
     * @param frameCount the number of frames present in the spritesheet provided as a {@link Bitmap}
     */
    public GameObjectSprite(Bitmap bitmap, int xPos, int yPos, boolean leftFacing, int frameCount){
        super(bitmap, xPos, yPos);
        this.status = SPRITE_STATUS.MOVING;
        if(!leftFacing){
            Matrix matrix = new Matrix();
            matrix.postScale(-1, 1, image.getWidth() / 2, image.getHeight() / 2);
            image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        }
        this.moveAnimation = new SpriteAnimation(image, frameCount);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas){
        if(this.status.equals(SPRITE_STATUS.MOVING)){
            moveAnimation.draw(canvas, this.xStart, this.yStart);
        } else if (this.status.equals(SPRITE_STATUS.ATTACKING)){
            attackAnimation.draw(canvas, this.xStart, this.yStart);
        }
    }

    /**
     * Sets the move animation to the provided animation
     * @param animation the {@link SpriteAnimation} to use as the move animation
     */
    public void setMoveAnimation(SpriteAnimation animation){
        moveAnimation = animation;
    }

    /**
     * Sets the attack animation to the provided animation
     * @param animation the {@link SpriteAnimation} to use as the attack animation
     */
    public void setAttackAnimation(SpriteAnimation animation){
        moveAnimation = animation;
    }

}
