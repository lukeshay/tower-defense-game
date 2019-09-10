package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class imgObject {

    private float x;
    private float y;
    private Bitmap draw;

    public imgObject(float xcoord, float ycoord, Bitmap drawResource){
        x = xcoord;
        y= ycoord;
        draw = drawResource;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Bitmap getDraw(){
        return draw;
    }
}
