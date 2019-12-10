package com.example.towerDefender.VolleyServices;

import android.content.Context;
import android.util.Log;

import com.example.towerDefender.Util.JsonUtility;

import java.util.ArrayList;

public class UserRestServices {
    public static final String BASE_URL = "http://coms-309-ss-5.misc.iastate.edu:8080/users";
    private static boolean responseReceived;
    static ArrayList<User> users;
    static User user;
    /**
     * Gets all the {@link User}s from the database and returns them in an {@link ArrayList}
     * @param context the Context to use when grabbing users
     * @return an {@link ArrayList} of {@link User}s
     */
    public static ArrayList<User> getAllUsers(Context context){
        getUsersVolley(context);
        return users;
    }

    public static User getUser(Context context, String userId){
        getUserById(context, userId);
        return user;
    }

    private static void getUsersVolley(Context context){
        VolleyUtilities.getRequest(context, UserRestServices.BASE_URL, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("ERROR", "Encountered an error while grabbing users from database. " + message);
            }

            @Override
            public void onResponse(Object response) {
                setResponse(true);
                setUsers(new ArrayList<>(JsonUtility.jsonToUserList(response.toString())));
            }
        });
    }

    private static void getUserById(Context context, String string){
        VolleyUtilities.getRequest(context, UserRestServices.BASE_URL + "/" + string, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.e("ERROR", "Encountered an error while grabbing user from database. " + message);
            }

            @Override
            public void onResponse(Object response) {
                Log.i("VOLLEY_USER_RESPONSE", response.toString());
                setResponse(true);
                setUser(JsonUtility.jsonToUser(response.toString()));
            }
        });
    }

    private static void setResponse(boolean response){
        responseReceived = response;
    }

    private static void setUsers(ArrayList<User> usersToSet){
        users = usersToSet;
    }

    private static void setUser(User user){
        UserRestServices.user = user;
    }
}
