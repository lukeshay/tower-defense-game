package com.example.towerDefender.Card;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OwnedDeck {
    @SerializedName("deck")
    private ArrayList<Card> _deck;
    @SerializedName("userId")
    private String _userId;
    @SerializedName("deckId")
    private int _deckId;
    @SerializedName("deckName")
    private String _deckName;

    public OwnedDeck(ArrayList<Card> deck, String userId, int deckId, String deckName){
        this._deck = deck;
        this._userId = userId;
        this._deckId = deckId;
        this._deckName = deckName;
    }

    public int get_deckId(){
        return this._deckId;
    }

    public String get_deckName(){
        return _deckName;
    }

    public String get_userId(){
        return _userId;
    }

    public ArrayList<Card> get_deck(){
        return _deck;
    }
}
