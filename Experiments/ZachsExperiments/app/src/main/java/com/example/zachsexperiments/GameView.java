package com.example.zachsexperiments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;
    private List<CharacterSprite> characters;
    private InventoryItem[] inventory;
    Paint paint;
    private boolean isPlacingSprite;
    private int spriteToPlace;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);
        setFocusable(true);
        inventory = new InventoryItem[4];
        paint = new Paint(Color.LTGRAY);
        characters = new ArrayList<CharacterSprite>();
        isPlacingSprite = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        mainThread.setRunning(true);
        mainThread.start();
        inventory[0] = new InventoryItem(BitmapFactory.decodeResource(getResources(), R.drawable.reaper), 0);
        inventory[1] = new InventoryItem(BitmapFactory.decodeResource(getResources(), R.drawable.reaper2), 1);
        inventory[2] = new InventoryItem(BitmapFactory.decodeResource(getResources(), R.drawable.reaper), 2);
        inventory[3] = new InventoryItem(BitmapFactory.decodeResource(getResources(), R.drawable.reaper), 3);
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
            for(CharacterSprite character : characters){
                character.draw(canvas);
            }
            for(Sprite item : inventory){
                item.draw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(isPlacingSprite){
                if(spriteToPlace != -1){
                    characters.add(new CharacterSprite(inventory[spriteToPlace].image, (int)event.getX(), (int)event.getY()));
                    inventory[spriteToPlace].setStatus(InventoryItem.Status.NOT_READY);
                    isPlacingSprite = false;
                }
            } else {
                //Is the event within the bounds of one of our inventory objects?
                if (event.getX() <= inventory[0].getxEnd() &&
                        event.getX() >= inventory[0].getxStart() &&
                        event.getY() <= inventory[0].getyEnd() &&
                        event.getY() >= inventory[0].getyStart() &&
                        inventory[0].paint.getColor() == Color.GREEN) {
                    spriteToPlace = 0;
                    isPlacingSprite = true;
                    inventory[spriteToPlace].setStatus(InventoryItem.Status.PLACING);
                } else if (event.getX() <= inventory[1].getxEnd() &&
                        event.getX() >= inventory[1].getxStart() &&
                        event.getY() <= inventory[1].getyEnd() &&
                        event.getY() >= inventory[1].getyStart() &&
                        inventory[1].paint.getColor() == Color.GREEN) {
                    spriteToPlace = 1;
                    isPlacingSprite = true;
                    inventory[spriteToPlace].setStatus(InventoryItem.Status.PLACING);
                } else if (event.getX() <= inventory[2].getxEnd() &&
                        event.getX() >= inventory[2].getxStart() &&
                        event.getY() <= inventory[2].getyEnd() &&
                        event.getY() >= inventory[2].getyStart() &&
                        inventory[2].paint.getColor() == Color.GREEN) {
                    spriteToPlace = 2;
                    isPlacingSprite = true;
                    inventory[spriteToPlace].setStatus(InventoryItem.Status.PLACING);
                } else if (event.getX() <= inventory[3].getxEnd() &&
                        event.getX() >= inventory[3].getxStart() &&
                        event.getY() <= inventory[3].getyEnd() &&
                        event.getY() >= inventory[3].getyStart() &&
                        inventory[3].paint.getColor() == Color.GREEN) {
                    spriteToPlace = 3;
                    isPlacingSprite = true;
                    inventory[spriteToPlace].setStatus(InventoryItem.Status.PLACING);
                }
            }
            return true;
        }
        return false;
    }

    public void update(){
        for(CharacterSprite character : characters){
            character.update();
        }
        for(InventoryItem item : inventory){
            item.update();
        }
    }
}
