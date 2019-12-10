package com.example.towerDefender.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.Log;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.Card.Deck;
import com.example.towerDefender.Card.PlayedCard;
import com.example.towerDefender.SocketServices.SocketUtilities;
import com.example.towerDefender.Util.CanvasUtility;
import com.example.towerDefender.Util.JsonUtility;
import com.example.towerDefender.Util.UserUtility;

import java.util.ArrayList;

import static android.provider.Settings.Secure.ANDROID_ID;

public class Player {
    //The amount of mana to regenerate every second
    private static double manaRegenRate = 1;
    private static Paint textPaint;

    //whether or not a card in the player's hand currently has status CardInHand.Status.PLACING
    private boolean isPlayingCard;
    //The index of the CardInHand to play from the player's CardInHand
    private int cardToPlayIndex;
    private int cardsSent = 0;
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
        this.maxMana = 10;
        this.currentMana = 10;
        isPlayingCard = false;
        cardToPlayIndex = 0;
        deck = new Deck(this, context, startingHand);
        textPaint = new Paint(Color.BLACK);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.WHITE);
        this.userId = UserUtility.getUserId();
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

    /**
     * Updates the card that is about to played (has been clicked on but not deployed)
     * @param index the index of this {@link GameManager}'s {@link Player}'s {@link CardInHand} to play
     * @param currentlyPlaying whether or not there is a {@link Card} in the process of being played
     */
    public void setPlayingCard(int index, boolean currentlyPlaying){
        this.cardToPlayIndex = index;
        if(index != -1){
            getCardInHand(cardToPlayIndex).setStatus(CardInHand.Status.PLACING);
        }
        this.isPlayingCard = currentlyPlaying;
    }


    /**
     * @return the {@link CardInHand} the manager is set to play
     */
    public CardInHand getPlayingCard(){
        return getCardInHand(cardToPlayIndex);
    }

    /**
     * Plays the {@link Card} represented by cardToPlayIndex.
     * @param eventX the X value of the event causing the card to be played
     * @param eventY the Y value of the event playing this card
     */
    public void playCard(int eventX, int eventY){
        try {
            Card toSend = new Card(getCardInHand(cardToPlayIndex).getCard());
            toSend.cardName = toSend.cardName + "@" + cardsSent++;
            SocketUtilities.sendMessage(JsonUtility.playedCardToJson(new PlayedCard(toSend,
                    CanvasUtility.convertCanvasPositionToServerPosition(CanvasUtility.getCanvas(),
                            eventX), eventY, getUserId())).toString()  );
            setCurrentMana(getCurrentMana() -getCardInHand(cardToPlayIndex).getCardManaCost());
            getCardInHand(cardToPlayIndex).setStatus(CardInHand.Status.PLAYED);
        } catch (Exception e){
            Log.e("ERROR", "Encountered an error sending card over socket.");
        }
    }

    /**
     * @return true if a card is currently set to {@link CardInHand.Status} = Status.PLACING
     */
    public boolean isPlayingCard(){
        return isPlayingCard;
    }

    /**
     * @return the index of the {@link CardInHand} from the {@link Player}'s hand to play
     */
    public int getCardToPlayIndex(){
        return cardToPlayIndex;
    }

}
