package com.example.towerDefender.VolleyServices;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserServices {

    public static void createNewUser(String user){
        try {
            URL url = new URL("coms-309-ss-5.misc.iastate.edu/users");
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type", "application/json; utf-8");
            client.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
            osw.write("");
        } catch(Exception e){
            //Handle exception
        }
    }
}
