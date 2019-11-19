package com.example.towerDefender.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.Card.Deck;

import java.util.ArrayList;

import static android.provider.Settings.Secure.ANDROID_ID;

public class Player {
    //The amount of mana to regenerate every second
    private static double manaRegenRate = 1;
    private static Paint textPaint;

    private String userId;
    private Deck deck;
    private CardInHand[] hand;
    private Context context;
    private int maxMana;
    private int currentMana;
    private long millisOfLastManaRecharge = 0;

    /**
     * Constructs a new {@link Player} belonging to the provided {@link Context}, with the provided startingHand.
     * @param context the {@link Context} to use as the playerContext
     * @param startingHand the {@link ArrayList} of {@link Card}s that will serve as the first hand
     */
    public Player(Context context, ArrayList<Card> startingHand){
        this.context = context;
        hand = new CardInHand[4];
        this.maxMana = 5;
        this.currentMana = 5;
        deck = new Deck(this, context, startingHand);
        textPaint = new Paint(Color.BLACK);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.WHITE);
        this.userId = Settings.Secure.getString(context.getContentResolver(), ANDROID_ID).substring(0, 5);
    }

    /**
     * Draws a new hand of {@link Card}s.
     */
    public void drawHand(){
        hand[0] = deck.drawCard(0, true);
        hand[1] = deck.drawCard(1, true);
        hand[2] = deck.drawCard(2, true);
        hand[3] = deck.drawCard(3, true);
    }

    /**
     * @return the {@link Context} this {@link Player} uses
     */
    public Context getPlayerContext(){
        return context;
    }

    /**
     * @return this {@link Player}'s {@link Deck}
     */
    public Deck getDeck(){
        return deck;
    }

    /**
     * Sets this {@link Player}'s {@link Deck} to the provided one
     * @param deck the {@link Deck} to set this object's deck variable to
     */
    public void setDeck(Deck deck){
        this.deck = deck;
    }

    /**
     * @return an array of all the {@link CardInHand}s in the player's hand
     */
    public CardInHand[] getHand(){
        return hand;
    }

    /**
     * Returns the {@link CardInHand} at the provided index
     * @param index the index of the {@link CardInHand} to return
     * @return a {@link CardInHand} for this {@link Player}'s hand at the provided index
     * @throws ArrayIndexOutOfBoundsException if the provided index is not within 0-4
     */
    public CardInHand getCardInHand(int index) throws ArrayIndexOutOfBoundsException{
        return hand[index];
    }

    /**
     * Sets the maxMana variable.
     * @param mana the amount of mana to use as maxMana
     */
    public void setMaxMana(int mana){
        this.maxMana = mana;
    }

    /**
     * Sets the currentMana variable.
     * @param mana the amount of mana to use as currentMana
     */
    public void setCurrentMana(int mana){
        this.currentMana = mana;
    }

    /**
     * @return this {@link Player}'s maxMana
     */
    public int getMaxMana(){
        return maxMana;
    }

    /**
     * @return this {@link Player}'s currentMana
     */
    public int getCurrentMana(){
        return currentMana;
    }

    /**
     * Regnerates the user's mana.
     */
    public void update(){
        if((System.currentTimeMillis() - millisOfLastManaRecharge) / 1000 >= 1){
            millisOfLastManaRecharge = System.currentTimeMillis();
            currentMana += manaRegenRate;
            if(currentMana > maxMana){
                currentMana = maxMana;
            }
        }
    }

    /**
     * Draws the player's userName and current mana to the canvas
     * @param canvas the {@link Canvas} to draw info on
     */
    public void draw(Canvas canvas){
        canvas.drawText( "MANA:" + currentMana + "/" + maxMana, 0, Sprite.screenHeight - 15, textPaint);
    }

    /**
     * @return this player's user id
     */
    public String getUserId(){
        return userId;
    }

}
