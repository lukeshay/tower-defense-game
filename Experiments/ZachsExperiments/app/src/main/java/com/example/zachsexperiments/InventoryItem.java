package com.example.zachsexperiments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class InventoryItem extends Sprite {

    public enum Status {
        READY, PLACING, NOT_READY;
    }

    //Used to create the inventory box behind our sprite
    private Rect background;
    public Paint paint;
    public int color;
    public Status status;
    //The time (in seconds) it will take this inventory item to recharge
    public static int loadTime = 3;
    public long startRechargeTime;

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
        if(this.status == Status.NOT_READY){
            if(System.currentTimeMillis() - startRechargeTime >= loadTime * 1000 ){
                this.setStatus(Status.READY);
            }
        }
    }

    public void setStatus(Status status){
        this.status = status;
        switch(status){
            case READY:
                this.color = Color.GREEN;
                break;
            case PLACING:
                this.color = Color.CYAN;
                break;
            case NOT_READY:
                this.color = Color.RED;
                this.startRechargeTime = System.currentTimeMillis();
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(color);
        canvas.drawRect(background, paint);
        super.draw(canvas);
    }
}
