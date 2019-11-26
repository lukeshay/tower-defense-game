package com.example.towerDefender.Util;

import android.provider.Settings;
import com.example.towerDefender.BaseApplication;
import static android.provider.Settings.Secure.ANDROID_ID;

public class UserUtility {


    /**
     * @return the user id for this device
     */
    public static String getUserId(){
        return Settings.Secure.getString(BaseApplication.getAppContext().getContentResolver(), ANDROID_ID).substring(0, 5);
    }
}
