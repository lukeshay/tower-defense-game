package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.VolleyServices.VolleyResponseListener;
import com.example.myapplication.VolleyServices.VolleyUtilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

/**
 * A class used for posting cards to the back-end and for grabbing them.
 */
public class CardRestServices {
    private static String baseUrl = "http://coms-309-ss-5.misc.iastate.edu:8080/cards";

    //TODO: finish POST request
    public static void sendCardToDB(Context context, Card card){
        VolleyUtilities.postRequest(context, baseUrl, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                //TODO: handle error
                System.out.println("Encountered an error while sending card to the database. " + message);
            }

            @Override
            public void onResponse(Object response) {

            }
        }, JsonUtils.cardtoJson(card));
    }

    public static Collection<Card> getAllCards(){
        try {
            URL url = new URL(baseUrl);
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
            //System.out.println(response.toString()); log
            return JsonUtils.jsonToCardArray(response.toString());

        } catch(Exception e){
            //TODO: Handle exception
            System.out.println("Encountered error");
            return null;
        }

    }

    public static Card getCardByName(String name){
        try {
            String newURL = baseUrl + "/" + name;
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
            //System.out.println(response.toString()); log
            return JsonUtils.jsonToCard(response.toString());
        } catch(Exception e){
            //Handle exception
            System.out.println("Encountered error");
            return null;
        }
    }

    public static void main(String[] args){
        //Test connections
        Card card = getCardByName("Card 1");
        System.out.println("Card title: " + card.cardName);
        System.out.println("List of all cards: ");
        for(Card card1 : getAllCards()){
            System.out.println(card1.cardName);
        }
    }



}
