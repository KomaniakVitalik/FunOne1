package com.www.funone;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by vitaliy.herasymchuk on 6/25/16.
 */
public class CoreApp extends Application {

    private static CoreApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        instance = this;
    }

    public static CoreApp getInstance() {
        return instance;
    }
}
