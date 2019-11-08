package com.example.towerDefender.VolleyServices;

import android.util.Log;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.SocketServices.SocketMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import com.example.towerDefender.Card.PlayedCard;

public class JsonUtils {

    /**
     * Converts a json String to a {@link Card} object
     * @param json the json {@link String} to construct a {@link Card} from
     * @return a {@link Card} described by the json
     */
    public static Card jsonToCard(String json){
        Gson test = new Gson();
        return test.fromJson(json, Card.class);
    }

    /**
     * Converts a json String to a collection of cards
     * @param json the json {@link String} to construct the {@link Collection} from
     * @return a {@link Collection} of {@link Card}s described by the provided json
     */
    public static Collection<Card> jsonToCardArray(String json){
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Card>>(){}.getType();
        Collection<Card> enums = gson.fromJson(json, collectionType);
        return enums;
    }

    /**
     * Converts a card to a {@link JSONObject}
     * @param card the initial {@link Card}
     * @return a {@link JSONObject} corresponding to the original {@link Card}
     */
    public static JSONObject cardtoJson(Card card){
        Gson gson = new Gson();
        String json = gson.toJson(card);
        try {
            return new JSONObject(json);
        } catch(Exception e){
            Log.e("ERROR", "Encountered a parse exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Converts a {@link PlayedCard} to a {@link JSONObject}
     * @param playedCard the {@link PlayedCard} to convert
     * @return the {@link JSONObject} corresponding to the original played card
     */
    public static JSONObject playedCardToJson(PlayedCard playedCard){
        Gson gson = new Gson();
        String json = gson.toJson(playedCard);
        try {
            return new JSONObject(json);
        } catch(Exception e){
            Log.e("ERROR", "Encountered a parse exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Converts a json {@link String} to a {@link PlayedCard}
     * @param json the initial json string
     * @return the {@link PlayedCard} corresponding to the json
     */
    public static PlayedCard jsonToPlayedCard(String json){
        Gson test = new Gson();
        return test.fromJson(json, PlayedCard.class);
    }

    /**
     * Converts a json String to a collection of played cards
     * @param json the json {@link String} to construct the {@link Collection} from
     * @return a {@link Collection} of {@link Card}s described by the provided json
     */
    public static Collection<PlayedCard> jsonToPlayedCardArray(String json){
        Collection<PlayedCard> cards;
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<PlayedCard>>(){}.getType();
        try{
            cards = gson.fromJson(json, collectionType);
        } catch (Exception e){
            cards = new ArrayList<>();
            cards.add(gson.fromJson(json, PlayedCard.class));
        }

        return cards;
    }

    public static SocketMessage jsonToSocketMessage(String json){
        Gson test = new Gson();
        return test.fromJson(json, SocketMessage.class);
    }
}
