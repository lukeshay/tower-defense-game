package com.example.zachsexperiments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class InventoryItem extends Sprite {
    //Used to create the inventory box behind our sprite
    private Rect background;
    public Paint paint;
    public int color;

    /**
     * Includes itemNumber, which counts up from 0. Used for spacing out the inventory items.
     */
    public InventoryItem(Bitmap bitmap, int itemNumber){
        super(bitmap, 150, Resources.getSystem().getDisplayMetrics().heightPixels - 250, 0, 0);
        image = Bitmap.createScaledBitmap(bitmap, normalizedInventorySize, normalizedInventorySize, false);
        xStart = 150 + itemNumber * normalizedInventorySize;
        xEnd = xStart + normalizedInventorySize;
        background = new Rect(this.xStart, this.yStart, this.xStart + image.getWidth(), this.yStart + image.getHeight());
        color = Color.GREEN;
        paint = new Paint(color);
    }


    @Override
    public void update(){
    }


    @Override
    public void draw(Canvas canvas) {
        paint.setColor(color);
        canvas.drawRect(background, paint);
        super.draw(canvas);
    }
}
