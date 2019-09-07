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
    private Paint paint;

    public InventoryItem(Bitmap bitmap){
        super(bitmap, 150, Resources.getSystem().getDisplayMetrics().heightPixels - 250, 0, 0);
        background = new Rect(this.xPos, this.yPos, this.xPos + image.getWidth(), this.yPos + image.getHeight());
        paint = new Paint(Color.WHITE);
    }

    @Override
    public void update(){

    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        canvas.drawRect(background, paint);
        super.draw(canvas);
    }
}
