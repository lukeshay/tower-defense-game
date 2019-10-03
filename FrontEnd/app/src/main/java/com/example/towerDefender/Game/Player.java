package com.example.towerDefender.Game;

import android.content.Context;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.Card.CardInHand;
import com.example.towerDefender.Card.Deck;

import java.util.ArrayList;

public class Player {
    public Deck deck;
    public CardInHand[] hand;
    private Context context;

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
}
