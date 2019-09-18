package com.example.myapplication;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A class used for posting cards to the back-end and for grabbing them.
 */
public class CardRestServices {

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



}
