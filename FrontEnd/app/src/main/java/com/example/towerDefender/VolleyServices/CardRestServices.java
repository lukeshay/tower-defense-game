package com.example.towerDefender.VolleyServices;

import android.content.Context;
import android.util.Log;

import com.example.towerDefender.Card.Card;
import com.example.towerDefender.VolleyServices.JsonUtils;
import com.example.towerDefender.VolleyServices.VolleyResponseListener;
import com.example.towerDefender.VolleyServices.VolleyUtilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A class used for posting cards to the back-end and for grabbing them.
 */
public class CardRestServices {
    public static final String BASE_URL = "http://coms-309-ss-5.misc.iastate.edu:8080/cards";
    public boolean responseReceived = false;
    public ArrayList<Card> cardResponse;

    public static void sendCardToDB(Context context, Card card){
        VolleyUtilities.postRequest(context, BASE_URL, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                //TODO: handle error
                Log.e("ERROR", "Encountered an error while sending card to the database. " + message);
            }

            @Override
            public void onResponse(Object response) {

            }
        }, JsonUtils.cardtoJson(card));
    }

    public void getAllCardsVolley(Context context){

        VolleyUtilities.getRequest(context, BASE_URL, new VolleyResponseListener() {
            Collection<Card> cards;
            @Override
            public void onError(String message) {
                Log.e("ERROR", "encountered an error while grabbing cards from database. " + message);
            }

            @Override
            public void onResponse(Object response) {
                setResponse(true);
                setCardResponse(new ArrayList<>(JsonUtils.jsonToCardArray(response.toString())));
            }
        });
    }

    public void setCardResponse(ArrayList<Card> cards){
        cardResponse = cards;
    }

    /**
     * Sets the current CardService's response value to true. Helps the method calls know when the request responses have been received.
     * @param response the value to set 'responseReceived' equal to
     */
    public void setResponse(boolean response){
        responseReceived = response;
    }

    public ArrayList<Card> getAllCards(Context context){
        try {
            this.getAllCardsVolley(context);
            return cardResponse; //response is received, cardResponse should be updated

        } catch(Exception e){
            //TODO: Handle exception
            Log.e("ERROR", "Encountered error: " + e.getMessage());
            return null;
        }

    }

    public static Card getCardByName(String name){
        try {
            String newURL = BASE_URL + "/" + name;
            URL url = new URL(newURL.replace(" ", "%20"));
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            URLConnection con = url.openConnection();
            client.setRequestMethod("GET");
            client.setRequestProperty("Content-Type", "application/json; utf-8");
            int responseCode = client.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input;
            StringBuffer response = new StringBuffer();
            while((input = in.readLine()) != null){
                response.append(input);
            }
            in.close();
            return JsonUtils.jsonToCard(response.toString());
        } catch(Exception e){
            //Handle exception
            Log.e("ERROR", "Encountered error getting card by name from database.");
            return null;
        }
    }

}
