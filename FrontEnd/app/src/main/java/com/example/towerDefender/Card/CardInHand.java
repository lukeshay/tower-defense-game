package com.example.towerDefender.Card;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.towerDefender.Game.GameManager;
import com.example.towerDefender.Game.GameObjectSprite;
import com.example.towerDefender.Game.Player;
import com.example.towerDefender.Game.Sprite;
import com.example.towerDefender.Util.CardUtilities;

import static com.example.towerDefender.Game.Sprite.normalizedInventorySize;

public class CardInHand {

    /**
     * An enumeration that carries the status of this {@link CardInHand}.
     * Is it ready to be played, currently being played, just played, or not ready to be played?
     */
    public enum Status {
        READY, PLACING, NOT_READY, PLAYED
    }

    //Used to create the inventory box behind our Card's sprite
    private Rect background;
    /**
     * The color to paint the background of the {@link Sprite}, associated with the {@link CardInHand.Status} of this {@link CardInHand}.
     */
    public Paint statusColor;
    private Paint textPaint;
    private int color;
    private Status status;
    private Card card;
    //TODO: not always a character!
    private GameObjectSprite cardSprite;
    private Player player;
    private int cardIndex;
    private String playerSide;
    /**
     * @param player the player that whose hand this card is in
     * @param card the card currently represented by this object
     * @param cardIndex the placement of this card within the hand
     */
    public CardInHand(Player player, Card card, int cardIndex, String playerSide){
        this.cardIndex = cardIndex;
        this.player = player;
        this.playerSide = playerSide;
        this.updateCardAndImage(card);
        background = new Rect(this.cardSprite.xStart, this.cardSprite.yStart, this.cardSprite.xEnd, this.cardSprite.yEnd);
        color = Color.GREEN;
        statusColor = new Paint(color);
        textPaint = new Paint(Color.BLACK);
        textPaint.setColor(Color.WHITE); //TODO: synchronize with gamemanager's text paint
        textPaint.setTextSize(50);
    }

    /**
     * A constructor for use in unit tests, where we don't care about images.
     * @param player the player that whose hand this card is in
     * @param card the card currently represented by this object
     * @param cardIndex the placement of this card within the hand
     */
    public CardInHand(Player player, Card card, int cardIndex, boolean setImage, String playerSide){
        this.cardIndex = cardIndex;
        this.player = player;
        this.card = card;
        this.playerSide = playerSide;
        if(setImage){
            this.updateCardAndImage(card);
            background = new Rect(this.cardSprite.xStart, this.cardSprite.yStart, this.cardSprite.xEnd, this.cardSprite.yEnd);
            color = Color.GREEN;
            statusColor = new Paint(color);
            textPaint = new Paint(Color.BLACK);
            textPaint.setTextSize(50);
            textPaint.setColor(Color.WHITE); //TODO: synchronize with gamemanager's text paint
        }
    }

    /**
     * Updates the load time and updates the {@link CardInHand.Status} if necessary.
     */
    public void update(){
        updateCardAndImage(this.card);
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
        if(GameManager.instance.playerSide.contains("left")){
            this.cardSprite = CardUtilities.getGameObjectSprite(player.getPlayerContext(), card,
                    450 + cardIndex * normalizedInventorySize,Resources.getSystem().getDisplayMetrics().heightPixels - 250, true);
        } else {
            this.cardSprite = CardUtilities.getGameObjectSprite(player.getPlayerContext(), card,
                    450 + cardIndex * normalizedInventorySize,Resources.getSystem().getDisplayMetrics().heightPixels - 250, false);
        }
        this.card = card;
        background = new Rect(this.cardSprite.xStart, this.cardSprite.yStart, this.cardSprite.xEnd, this.cardSprite.yEnd);
    }

}
