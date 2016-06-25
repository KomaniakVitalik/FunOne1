package com.www.funone.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.www.funone.util.Logger;
import com.www.funone.util.Validator;

/**
 * Created by vitaliy.herasymchuk on 6/25/16.
 */
public class BaseActivity extends AppCompatActivity {

    private BroadcastReceiver mOfflineReceiver;
    private OnInternetConnectionListener mInternetConnectionListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        registerInternetChecker();
    }

    protected int getCompatColor(int id) {
        return ContextCompat.getColor(this, id);
    }

    protected void applyCustomFont(TextView tv, String typeFaceLocation) {
        tv.setTypeface(Typeface.createFromAsset(getAssets(), typeFaceLocation));
    }

    protected String getResString(int id) {
        return getResources().getString(id);
    }

    public void hideKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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

    private void registerInternetChecker() {
        mOfflineReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getExtras() != null) {
                    try {
                        NetworkInfo info = (NetworkInfo) intent.getExtras().get("networkInfo");
                        if (info.getState() == NetworkInfo.State.DISCONNECTED) {
                            notifyNetwork(false);
                        } else {
                            notifyNetwork(true);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mOfflineReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mOfflineReceiver);
        } catch (Exception e) {
            Logger.e("BaseActivity", e.toString());
        }
    }

    private void notifyNetwork(boolean on) {
        if (Validator.isObjectValid(mInternetConnectionListener)) {
            if (on) {
                mInternetConnectionListener.internetOn();
            } else {
                mInternetConnectionListener.internetOff();
            }
        }
    }

    protected void addInternetListener(OnInternetConnectionListener listener) {
        this.mInternetConnectionListener = listener;
    }

    public interface OnInternetConnectionListener {

        void internetOff();

        void internetOn();

    }

}
