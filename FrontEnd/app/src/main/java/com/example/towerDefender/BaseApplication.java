package com.example.towerDefender;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    private static volatile Context context;

    public void onCreate() {
        super.onCreate();
        BaseApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BaseApplication.context;
    }

}
