package com.nutsdev.materialtest;

import android.app.Application;
import android.content.Context;

/**
 * Created by n1ck on 22.02.2015.
 */
public class MyApplication extends Application {

    // my inactive key 7br8hd52ba57zheu4r4vw9rv
    public static final String API_KEY_ROTTEN_TOMATOES = "54wzfswsa4qmjg8hjwa64d4c";
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
