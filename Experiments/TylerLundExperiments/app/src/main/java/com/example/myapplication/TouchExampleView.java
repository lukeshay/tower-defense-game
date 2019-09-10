package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

class TouchExampleView extends View {
    private float x = 50;
    private float y = 50;
    private ArrayList<imgObject> pictureArray = new ArrayList<imgObject>();
    private boolean canDraw = false;

    Paint paint = new Paint();
    Paint circ = new Paint();

    public TouchExampleView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        circ.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        Bitmap test = BitmapFactory.decodeResource(getResources(),R.drawable.knight_run_anim_f0);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x1 = event.getX();
        float y1 = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //verify that the inital tap was within bounds (i.e they click on the part you wanted)
                if (x1 >= 0 && x1 < (100) && y1 >= 0 && y1 < (100)) {
                    canDraw = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //prevents image from appearing when moving
                if(canDraw) {
                    //bind object to the location of the finger
                    x = event.getX();
                    y = event.getY();
                }
                break;
                //user has lifted their finger
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //add image to list for each canvas redraw
                if(canDraw == true){
                        Bitmap v = BitmapFactory.decodeResource(getResources(), R.drawable.knight_run_anim_f0);
                        pictureArray.add(new imgObject(x, y, v));
                canDraw = false;

                }
                break;

        }
        return true;
    }
    public void draw(Canvas canvas){
        super.draw(canvas);
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.knight_run_anim_f0);
        canvas.drawBitmap(b, x, y, circ);
        canvas.drawBitmap(b,50,50,circ);
        canvas.drawRect(0,0,100,100,paint);
         for(imgObject z: pictureArray){
             if(!(z.getX() >= 0 && z.getX() < (100) && z.getY() >= 0 && z.getY() < (100))) {
                 canvas.drawBitmap(z.getDraw(), z.getX(), z.getY(), circ);
             }
         }
        invalidate();
    }
}