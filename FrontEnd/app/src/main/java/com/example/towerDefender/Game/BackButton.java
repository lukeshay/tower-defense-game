package com.example.towerDefender.Game;

import android.graphics.Bitmap;

import com.example.towerDefender.Game.Sprite;

public class BackButton extends Sprite {

    public BackButton(Bitmap bitmap){
        super(Bitmap.createScaledBitmap(bitmap, normalizedButtonSize, normalizedButtonSize, false), 0, 0, 0, 0);
    }

    @Override
    public void update() {

    }
}
