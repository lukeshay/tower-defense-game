package com.example.towerDefender.Game;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.towerDefender.Activities.NavigationActivity;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public GameView instance;
    private MainThread mainThread;
    private Paint paint;
    private GameManager manager;
    private Player player;

    public GameView(Context context, Player player){
        super(context);
        instance = this;
        this.player = player;
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);
        setFocusable(true);
        paint = new Paint(Color.LTGRAY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        mainThread.setRunning(true);
        mainThread.start();
        manager = new GameManager(this, player);
        manager.getPlayer().drawHand();
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
            //Manager will drawAsFriendly the characters and hand
            manager.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(manager.isPlayingCard()){
                if(manager.getCardToPlayIndex() != -1){
                    manager.playCard((int)event.getX(), (int)event.getY());
                    manager.setPlayingCard(-1, false);
                }
            } else {
                //Is the event within the bounds of one of our CardInHand objects?
                for(int i = 0; i < 4; i++){
                    if (event.getX() <= manager.getPlayer().getCardInHand(i).getSprite().getxEnd() &&
                            event.getX() >= manager.getPlayer().getCardInHand(i).getSprite().getxStart() &&
                            event.getY() <= manager.getPlayer().getCardInHand(i).getSprite().getyEnd() &&
                            event.getY() >= manager.getPlayer().getCardInHand(i).getSprite().getyStart() &&
                            manager.getPlayer().getCardInHand(i).statusColor.getColor() == Color.GREEN) {
                        if(manager.getPlayer().getCurrentMana() >= manager.getPlayer().getCardInHand(i).getCardManaCost()){
                            manager.setPlayingCard(i, true);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Updates the {@link GameManager} and consequently all game variables, characters, etc.
     */
    public void update(){
        manager.update();
    }

    /**
     * @return this {@link GameView}'s {@link GameManager}
     */
    public GameManager getManager(){
        return this.manager;
    }

}
