package com.example.zachsexperiments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;
    private CharacterSprite character;
    private InventoryItem[] inventory;
    Paint paint;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);
        setFocusable(true);
        inventory = new InventoryItem[4];
        paint = new Paint(Color.LTGRAY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        mainThread.setRunning(true);
        mainThread.start();
        character = new CharacterSprite(BitmapFactory.decodeResource(getResources(), R.drawable.reaper));
        inventory[0] = new InventoryItem(BitmapFactory.decodeResource(getResources(), R.drawable.reaper));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try {
                mainThread.setRunning(false);
                mainThread.join();
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
            canvas.drawRect(new Rect(150, Resources.getSystem().getDisplayMetrics().heightPixels - 250, Resources.getSystem().getDisplayMetrics().widthPixels - 150, Resources.getSystem().getDisplayMetrics().heightPixels), paint);
            character.draw(canvas);
            inventory[0].draw(canvas);
        }
    }

    public void update(){
        character.update();
    }
}
