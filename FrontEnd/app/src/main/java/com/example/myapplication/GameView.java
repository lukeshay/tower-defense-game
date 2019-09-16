package com.example.myapplication;

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

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;
    private List<CharacterSprite> characters;
    private BackButton backButton;
    private CardInHand[] hand;
    Paint paint;
    private boolean isPlacingSprite;
    private int spriteToPlace;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);
        setFocusable(true);
        hand = new CardInHand[4];
        paint = new Paint(Color.LTGRAY);
        characters = new ArrayList<>();
        isPlacingSprite = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        mainThread.setRunning(true);
        mainThread.start();
        hand[0] = new CardInHand(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(getResources(), R.drawable.reaper)), 0);
        hand[1] = new CardInHand(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(getResources(), R.drawable.reaper2)), 1);
        hand[2] = new CardInHand(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(getResources(), R.drawable.reaper)), 2);
        hand[3] = new CardInHand(new Card(Card.CardType.UNIT, BitmapFactory.decodeResource(getResources(), R.drawable.reaper2)), 3);
        backButton = new BackButton(BitmapFactory.decodeResource(getResources(),R.drawable.back_button));
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
            for(CardInHand item : hand){
                item.draw(canvas);
            }
            backButton.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(isPlacingSprite){
                if(spriteToPlace != -1){
                    characters.add(new CharacterSprite(hand[spriteToPlace].card.sprite.image, (int)event.getX(), (int)event.getY()));
                    hand[spriteToPlace].setStatus(CardInHand.Status.NOT_READY);
                    isPlacingSprite = false;
                }
            } else {
                if(event.getX() <= backButton.getxEnd() &&
                    event.getX() >= backButton.getxStart() &&
                    event.getY() <= backButton.getyEnd() &&
                    event.getY() >= backButton.getyStart()){
                    this.backToNavigation(this);
                }
                //Is the event within the bounds of one of our CardInHand objects?
                if (event.getX() <= hand[0].card.sprite.getxEnd() &&
                        event.getX() >= hand[0].card.sprite.getxStart() &&
                        event.getY() <= hand[0].card.sprite.getyEnd() &&
                        event.getY() >= hand[0].card.sprite.getyStart() &&
                        hand[0].paint.getColor() == Color.GREEN) {
                    spriteToPlace = 0;
                    isPlacingSprite = true;
                    hand[spriteToPlace].setStatus(CardInHand.Status.PLACING);
                } else if (event.getX() <= hand[1].card.sprite.getxEnd() &&
                        event.getX() >= hand[1].card.sprite.getxStart() &&
                        event.getY() <= hand[1].card.sprite.getyEnd() &&
                        event.getY() >= hand[1].card.sprite.getyStart() &&
                        hand[1].paint.getColor() == Color.GREEN) {
                    spriteToPlace = 1;
                    isPlacingSprite = true;
                    hand[spriteToPlace].setStatus(CardInHand.Status.PLACING);
                } else if (event.getX() <= hand[2].card.sprite.getxEnd() &&
                        event.getX() >= hand[2].card.sprite.getxStart() &&
                        event.getY() <= hand[2].card.sprite.getyEnd() &&
                        event.getY() >= hand[2].card.sprite.getyStart() &&
                        hand[2].paint.getColor() == Color.GREEN) {
                    spriteToPlace = 2;
                    isPlacingSprite = true;
                    hand[spriteToPlace].setStatus(CardInHand.Status.PLACING);
                } else if (event.getX() <= hand[3].card.sprite.getxEnd() &&
                        event.getX() >= hand[3].card.sprite.getxStart() &&
                        event.getY() <= hand[3].card.sprite.getyEnd() &&
                        event.getY() >= hand[3].card.sprite.getyStart() &&
                        hand[3].paint.getColor() == Color.GREEN) {
                    spriteToPlace = 3;
                    isPlacingSprite = true;
                    hand[spriteToPlace].setStatus(CardInHand.Status.PLACING);
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
        for(CardInHand item : hand){
            item.update();
        }
    }

    public void backToNavigation(View view){
        Intent intent = new Intent(this.getContext(), NavigationActivity.class);
        this.getContext().startActivity(intent);
    }


}
