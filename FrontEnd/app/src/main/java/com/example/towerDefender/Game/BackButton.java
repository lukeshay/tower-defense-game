package com.example.towerDefender.Game;

import android.graphics.Bitmap;

import com.example.towerDefender.Game.Sprite;

public class BackButton extends Sprite {

    /**
     * Constructs a new {@link BackButton} with the provided {@link Bitmap}as its image}
     * @param bitmap the image to use for this {@link BackButton}
     */
    public BackButton(Bitmap bitmap){
        super(Bitmap.createScaledBitmap(bitmap, normalizedButtonSize, normalizedButtonSize, false), 0, 0);
    }

    @Override
    public void update() {
        //nothing to update
    }
}
