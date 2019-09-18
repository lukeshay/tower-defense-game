package com.example.myapplication;

import java.util.ArrayList;
import java.util.Random;


public class Deck {
    //TODO: most of this logic should probably be moved to the back-end. This is dummy logic so that the game can run with randomized cards pulling from a deck
    private ArrayList<Card> deck;
    private int index;
    private Player player; // the player that owns this deck

    public Deck(Player player, ArrayList<Card> cards){
        deck = cards;
        index = 0;
        this.player = player;
    }

    public Deck(Player player){
        deck = new ArrayList<>();
        index = 0;
        this.player = player;
    }

    public CardInHand drawCard(int cardInHandIndex){
        //Players can never run out of cards in their deck. When the index is maxed out, we will shuffle the deck and start index back at 0.
        index++;
        if(index >= deck.size()-1){
            this.shuffle();
            index = 0;
        }
        return new CardInHand(player, deck.get(index),cardInHandIndex);
    }

    public void add(Card card){
        this.deck.add(card);
    }

    public void shuffle(){
        Random random = new Random();
        for(int i = 0; i < deck.size(); i++){
            int index = random.nextInt(deck.size());
            Card temp = deck.get(index);
            deck.set(index, deck.get(i));
            deck.set(i, temp);
        }
    }
}
