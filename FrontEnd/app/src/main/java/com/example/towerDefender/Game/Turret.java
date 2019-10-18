package com.example.towerDefender.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.towerDefender.R;

public class Turret extends Sprite{

    private int HP;

    public Turret(Bitmap bitmap, int x, int y){
        super(bitmap, x, y, 0, 0);
        image = Bitmap.createScaledBitmap(image, Sprite.normalizedInventorySize, Sprite.normalizedInventorySize, false);
        HP = 500;
    }

    @Override
    public void update(){

    }

    /**
     * Sets this tower's HP
     * @param hp the hp to set this tower's health to
     */
    public void setHP(int hp){
        this.HP = hp;
    }

    /**
     * @return this tower's HP
     */
    public int getHP(){
        return HP;
    }
}
