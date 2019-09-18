package com.example.myapplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * A class used for posting cards to the back-end and for grabbing them.
 */
public class CardRestServices {
    private static String baseUrl = "http://coms-309-ss-5.misc.iastate.edu:8080/cards";

    public static void sendCardToDB(Card card){
        try {
            URL url = new URL("coms-309-ss-5.misc.iastate.edu/cards");
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type", "application/json; utf-8");
            client.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
            osw.write("{"
                    + "name: " + card.cardName
                    + "description: " + card.description
                    + "cost: " + card.castingCost
                    + "type:" + card.cardType.toString()+
                    "}");
        } catch(Exception e){
            //Handle exception
        }
    }

    public static List<Card> getAllCards(){
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
            //Debug
            System.out.println(response.toString());
        } catch(Exception e){
            //Handle exception
            System.out.println("Encountered error");
        }
        return new ArrayList<Card>();
    }

    public static void main(String[] args){
        //Test connections
        getAllCards();
    }



}
