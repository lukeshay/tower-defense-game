package com.example.towerDefender.Game;

import android.content.Context;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.Card.Deck;

import java.util.ArrayList;

public class Player {
    private Deck deck;
    private CardInHand[] hand;
    private Context context;
    private int maxMana;
    private int currentMana;

    public Player(Context context, ArrayList<Card> startingHand){
        this.context = context;
        deck = new Deck(this, context, startingHand);
        hand = new CardInHand[4];
    }

    public void drawHand(){
        hand[0] = deck.drawCard(0);
        hand[1] = deck.drawCard(1);
        hand[2] = deck.drawCard(2);
        hand[3] = deck.drawCard(3);
    }

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

}
