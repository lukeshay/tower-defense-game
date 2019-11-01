package com.example.towerDefender.Card;

import android.content.Context;
import android.util.Log;

import com.example.towerDefender.Game.GameManager;
import com.example.towerDefender.VolleyServices.CardRestServices;
import com.example.towerDefender.Game.Player;
import com.example.towerDefender.VolleyServices.JsonUtils;
import com.example.towerDefender.VolleyServices.VolleyResponseListener;
import com.example.towerDefender.VolleyServices.VolleyUtilities;

import java.util.ArrayList;


public class Deck {
    private ArrayList<Card> deck;
    private int index;
    private Player player; // the player that owns this deck
    private String playerSide;
    /**
     * Constructs a new {@link Deck} for the provided {@link Player}, consisting of the provided {@link Card}s.
     * @param player the {@link Player} whose deck this is
     * @param context the {@link Context} that this deck belongs in
     * @param cards an {@link ArrayList} of {@link Card}s to put in the deck
     */
    public Deck(Player player, Context context, ArrayList<Card> cards){
        deck = cards;
        index = 0;
        this.player = player;
    }

    /**
     * Draws and returns a {@link CardInHand} from this deck.
     * @param cardInHandIndex the index to store the card in
     * @return a {@link CardInHand} representing the next card in this deck
     */
    @Deprecated
    public CardInHand drawCard(int cardInHandIndex){
        //Players can never run out of cards in their deck. When the index is maxed out, we will shuffle the deck and start index back at 0.
        if(++index == deck.size()){
            index = 0;
        }
        return new CardInHand(player, deck.get(index),cardInHandIndex, this.playerSide);
    }

    /**
     * Draws and returns a {@link CardInHand} from this deck.
     * @param cardInHandIndex the index to store the card in
     * @param setImage whether or not the image of the {@link CardInHand} should be set
     * @return a {@link CardInHand} representing the next card in this deck
     */
    public CardInHand drawCard(int cardInHandIndex, boolean setImage){
        //Players can never run out of cards in their deck. When the index is maxed out, we will shuffle the deck and start index back at 0.
        if(++index == deck.size()){
            index = 0;
        }
        return new CardInHand(player, deck.get(index),cardInHandIndex, setImage, this.playerSide);
    }

    public void add(Card card){
        this.deck.add(card);
    }

    //public for testing purposes

    /**
     * Sets this {@link Deck}'s list of {@link Card}s to the provided {@link Card}s
     * @param cards the {@link ArrayList} of {@link Card}s to set the deck to
     */
    public void setDeck(ArrayList<Card> cards){
        this.deck = cards;
    }

    /**
     * Gets the deck from the server
     * @param context the current context
     */
    public void getDeckFromServer(Context context){
        VolleyUtilities.getRequest(context, CardRestServices.BASE_URL, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("ERROR", "Encountered an error while grabbing cards from database. " + message);
            }

            @Override
            public void onResponse(Object response) {
                setDeck(new ArrayList<>(JsonUtils.jsonToCardArray(response.toString())));
            }
        });
    }

    /**
     * @return the size of this deck
     */
    public int size(){
        return deck.size();
    }

}
