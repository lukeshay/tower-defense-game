package com.example.towerDefender.Util;

import android.content.Context;
import android.provider.Settings;
import com.example.towerDefender.BaseApplication;
import com.example.towerDefender.VolleyServices.User;
import com.example.towerDefender.VolleyServices.UserRestServices;

import static android.provider.Settings.Secure.ANDROID_ID;

public class UserUtility {

    /**
     * @return the user id for this device
     */
    public static String getUserId(){
        return Settings.Secure.getString(BaseApplication.getAppContext().getContentResolver(), ANDROID_ID).substring(0, 5);
    }

    /**
     * @param context the context to use
     * @return the user id for this device
     */
    public static String getUserId(Context context){
        return Settings.Secure.getString(context.getContentResolver(), ANDROID_ID).substring(0, 5);
    }

    /**
     * @return a {@link User} object for the active user
     */
    public static User getUser(){
        return UserRestServices.getUser(BaseApplication.getAppContext(), getUserId());
    }

    /**
     * @param context the context to use
     * @return a {@link User} object for the active user
     */
    public static User getUser(Context context){
        return UserRestServices.getUser(context, getUserId(context));
    }
}
