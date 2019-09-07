package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

class TouchExampleView extends View {
    private float x = 50;
    private float y = 50;
    private float initalX;
    private float initalY;
    private float offsetX;
    private float offsetY;

    Paint paint = new Paint();
    Paint circ = new Paint();

    public TouchExampleView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        circ.setColor(Color.GREEN);
    }

    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                initalX = x;
                initalY = y;
                offsetX = event.getX();
                offsetY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                x = initalX + event.getX() - offsetX;
                y = initalY + event.getY() - offsetY;
                break;
        }
        return true;
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0,0,width,height, paint);
        canvas.drawCircle(x,y, 20,circ);
        invalidate();
    }
}