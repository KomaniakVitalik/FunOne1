package com.www.funone;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.www.funone.api.DataManager;
import com.www.funone.api.RetrofitRequest;
import com.www.funone.managers.AuthenticationManager;
import com.www.funone.util.AppSettings;
import com.www.funone.util.Pref;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by vitaliy.herasymchuk on 6/25/16.
 */
public class CoreApp extends Application {

    private static CoreApp instance;

    private DataManager dataManager;
    private AuthenticationManager authenticationManager;

    @Override
    public void onCreate() {
        super.onCreate();
            initRealm();

        FacebookSdk.sdkInitialize(this);
        instance = this;
        dataManager = new DataManager(new RetrofitRequest());
        authenticationManager = AuthenticationManager.getInstance();
    }

    private RealmConfiguration initRealm(){
        RealmConfiguration myConfig = new RealmConfiguration.Builder(this)
                .name("funone.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(myConfig);
        return myConfig;
    }

    public static CoreApp getInstance() {
        return instance;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else {
            if (ni.isConnected()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
