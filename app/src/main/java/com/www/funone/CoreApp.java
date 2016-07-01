package com.www.funone;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.www.funone.api.DataManager;
import com.www.funone.api.RetrofitRequest;
import com.www.funone.managers.AuthenticationManager;
import com.www.funone.util.AppSettings;
import com.www.funone.util.Pref;

/**
 * Created by vitaliy.herasymchuk on 6/25/16.
 */
public class CoreApp extends Application {

    private static CoreApp instance;
    private AppSettings appSettings;
    private DataManager dataManager;
    private AuthenticationManager authenticationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        instance = this;
        dataManager = new DataManager(new RetrofitRequest());
        authenticationManager = AuthenticationManager.getInstance();

        if (Pref.getString(Pref.APP_SETTINGS_KEY) == null) {
            appSettings = new AppSettings();
        } else {
            appSettings = new Gson().fromJson(Pref.getString(Pref.APP_SETTINGS_KEY), AppSettings.class);
        }
    }

    public static CoreApp getInstance() {
        return instance;
    }

    public AppSettings getAppSettings() {
        return appSettings;
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
