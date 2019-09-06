package com.example.zachsexperiments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.zachsexperiments.MainThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Sprite character;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();
        character = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.reaper));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){
                    e.printStackTrace();
                }
            retry = false;
            }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            canvas.drawColor(Color.BLUE);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            //canvas.drawRect(500, 500, 600, 600, paint);
            character.draw(canvas);
        }
    }

    public void update(){
        character.update();
    }
}
