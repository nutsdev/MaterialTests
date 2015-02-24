package com.nutsdev.materialtest;

import android.app.Application;
import android.content.Context;

/**
 * Created by n1ck on 22.02.2015.
 */
public class MyApplication extends Application {

    public static final String API_KEY_ROTTEN_TOMATOES = "7br8hd52ba57zheu4r4vw9rv";
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
