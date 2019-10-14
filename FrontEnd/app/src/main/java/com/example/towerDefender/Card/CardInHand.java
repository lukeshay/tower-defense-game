package com.example.towerDefender.Card;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.towerDefender.Game.Character;
import com.example.towerDefender.Game.Player;
import com.example.towerDefender.Game.Sprite;
import static com.example.towerDefender.Game.Sprite.normalizedInventorySize;

public class CardInHand {

    public enum Status {
        READY, PLACING, NOT_READY, PLAYED
    }

    //Used to create the inventory box behind our Card's sprite
    private Rect background;
    public Paint statusColor;
    private Paint textPaint;
    private int color;
    private Status status;
    private Card card;
    //TODO: not always a character!
    private Character cardSprite;
    private Player player;
    private int cardIndex;

    /**
     * @param player the player that whose hand this card is in
     * @param card the card currently represented by this object
     * @param cardIndex the placement of this card within the hand
     */
    public CardInHand(Player player, Card card, int cardIndex){
        this.cardIndex = cardIndex;
        this.player = player;
        this.updateCardAndImage(card);
        background = new Rect(this.cardSprite.xStart, this.cardSprite.yStart, this.cardSprite.xEnd, this.cardSprite.yEnd);
        color = Color.GREEN;
        statusColor = new Paint(color);
        textPaint = new Paint(Color.BLACK);
        textPaint.setTextSize(50);
    }

    /**
     * A constructor for use in unit tests, where we don't care about images.
     * @param player the player that whose hand this card is in
     * @param card the card currently represented by this object
     * @param cardIndex the placement of this card within the hand
     */
    public CardInHand(Player player, Card card, int cardIndex, boolean setImage){
        this.cardIndex = cardIndex;
        this.player = player;
        this.card = card;
        if(setImage){
            this.updateCardAndImage(card);
            background = new Rect(this.cardSprite.xStart, this.cardSprite.yStart, this.cardSprite.xEnd, this.cardSprite.yEnd);
            color = Color.GREEN;
            statusColor = new Paint(color);
            textPaint = new Paint(Color.BLACK);
            textPaint.setTextSize(50);
        }
    }

    /**
     * Updates the load time and updates the {@link CardInHand.Status} if necessary.
     */
    public void update(){
        if(this.status != Status.PLACING && player.getCurrentMana() >= this.card.castingCost){
            setStatus(Status.READY);
        } else if(this.status != Status.PLACING){
            setStatus(Status.NOT_READY);
        }
    }

    /**
     * Sets the status of this card. The {@link com.example.towerDefender.Card.CardInHand.Status} is
     * used for determining the color of the background for the card when drawn to the canvas.
     * @param status the status to set this card's status to
     */
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
                break;
            case PLAYED:
                this.updateCardAndImage(player.getDeck().drawCard(this.cardIndex).getCard());
                break;
        }
    }

    /**
     * @return the {@link CardInHand.Status} for this object
     */
    public Status getStatus(){
        return status;
    }

    /**
     * @return the {@link Sprite} to display when displaying this {@link CardInHand}
     */
    public Sprite getSprite(){
        return this.cardSprite;
    }

    /**
     * Draws the {@link Card} represented by this object to the provided canvas, as well as a backing square with a color corresponding to the current {@link CardInHand.Status} of this object
     * @param canvas the {@link Canvas} to draw to
     */
    public void draw(Canvas canvas) {
        statusColor.setColor(color);
        canvas.drawRect(background, statusColor);
        this.cardSprite.draw(canvas);
        canvas.drawText(this.card.cardName, this.cardSprite.xStart, this.cardSprite.yStart - 20, textPaint);

    }

    /**
     * @return the {@link Card} that this {@link CardInHand} is wrapping
     */
    public Card getCard(){
        return card;
    }

    /**
     * @return the cost of this {@link CardInHand}
     */
    public int getCardManaCost(){
        return card.castingCost;
    }

    /**
     * Updates the cardSprite for this {@link CardInHand} based upon the provided {@link Card}. Makes a call to {@link CardUtilities}
     * @param card the card to update the {@link Sprite} for
     */
    private void updateCardAndImage(Card card){
        this.cardSprite = (Character)CardUtilities.getBitmapForCard(player.getPlayerContext(), card);
        this.card = card;
        this.cardSprite.xStart = 450 + cardIndex * normalizedInventorySize;
        this.cardSprite.yStart = Resources.getSystem().getDisplayMetrics().heightPixels - 250;
        this.cardSprite.image = Bitmap.createScaledBitmap(this.cardSprite.image, normalizedInventorySize, normalizedInventorySize, false);
        this.cardSprite.xEnd = this.cardSprite.xStart + normalizedInventorySize;
        this.cardSprite.yEnd = this.cardSprite.yStart + normalizedInventorySize;
        background = new Rect(this.cardSprite.xStart, this.cardSprite.yStart, this.cardSprite.xEnd, this.cardSprite.yEnd);
    }

}
