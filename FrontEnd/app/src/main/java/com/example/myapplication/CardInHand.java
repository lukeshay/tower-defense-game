package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.example.myapplication.Sprite.normalizedInventorySize;

public class CardInHand {

    public enum Status {
        READY, PLACING, NOT_READY
    }
    //Used to create the inventory box behind our Card's sprite
    private Rect background;
    public Paint paint;
    public int color;
    public Status status;
    public Card card;
    public Character cardSprite;
    private Player player;
    //The time (in seconds) it will take this inventory item to recharge
    public static int loadTime = 3;
    //The time that this card started recharging
    public long startRechargeTime;
    private int cardIndex;

    /**
     *
     * @param player the player that whose hand this card is in
     * @param card the card currently represented by this object
     * @param cardIndex the placement of this card within the hand
     */
    public CardInHand(Player player, Card card, int cardIndex){
        this.cardSprite = (Character)CardUtilities.getBitmapForCard(card);
        this.card = card;
        this.cardIndex = cardIndex;
        this.player = player;
        this.cardSprite.image = Bitmap.createScaledBitmap(CardUtilities.getBitmapForCard(card).image, normalizedInventorySize, normalizedInventorySize, false);
        this.cardSprite.xStart = 150 + cardIndex * normalizedInventorySize;
        this.cardSprite.xEnd = CardUtilities.getBitmapForCard(card).xStart + normalizedInventorySize;
        this.cardSprite.yStart = Resources.getSystem().getDisplayMetrics().heightPixels - 250;
        this.cardSprite.yEnd = this.cardSprite.yStart + this.cardSprite.image.getHeight();
        background = new Rect(this.cardSprite.xStart, CardUtilities.getBitmapForCard(card).yStart, this.cardSprite.xStart + CardUtilities.getBitmapForCard(card).image.getWidth(),
                this.cardSprite.yStart + this.cardSprite.image.getHeight());
        color = Color.GREEN;
        paint = new Paint(color);
    }

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
                this.card = player.deck.drawCard(this.cardIndex).getCard();
                break;
        }
    }

    public void draw(Canvas canvas) {
        paint.setColor(color);
        canvas.drawRect(background, paint);
        card.draw(canvas);
    }

    public Card getCard(){
        return card;
    }

}
