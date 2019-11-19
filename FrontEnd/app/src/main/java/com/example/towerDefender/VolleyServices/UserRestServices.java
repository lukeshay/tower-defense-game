package com.example.towerDefender.VolleyServices;

import android.content.Context;
import android.util.Log;

import com.example.towerDefender.Card.Card;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UserRestServices {
    public static final String BASE_URL = "http://coms-309-ss-5.misc.iastate.edu:8080/users";
    private static boolean responseReceived;
    static ArrayList<User> users;

    public static ArrayList<User> getAllUsers(Context context){
        getUsersVolley(context);
        return users;
    }

    private static void getUsersVolley(Context context){
        VolleyUtilities.getRequest(context, CardRestServices.BASE_URL, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("ERROR", "Encountered an error while grabbing cards from database. " + message);
            }

            @Override
            public void onResponse(Object response) {
                setResponse(true);
                setUsers(new ArrayList<>(JsonUtils.jsonToUserList(response.toString())));
            }
        });
    }


    private static void setResponse(boolean response){
        responseReceived = response;
    }

    private static void setUsers(ArrayList<User> usersToSet){
        users = usersToSet;
    }
}
