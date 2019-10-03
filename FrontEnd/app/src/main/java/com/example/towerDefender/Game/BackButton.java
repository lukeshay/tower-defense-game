package com.example.towerDefender.Game;

import android.graphics.Bitmap;

import com.example.towerDefender.Game.Sprite;

public class BackButton extends Sprite {

    public BackButton(Bitmap bitmap){
        super(bitmap, 0, 0, 0, 0);
        image = Bitmap.createScaledBitmap(bitmap, normalizedButtonSize, normalizedButtonSize, false);
    }

    @Override
    public void update() {

    }
}
