package com.www.funone.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.www.funone.CoreApp;
import com.www.funone.R;
import com.www.funone.api.DataManager;
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

    protected void initToolBarSettingsButton() {
        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, SettingsActivity.class));
            }
        });
    }

    protected void loadFrg(Fragment fragment, int container) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(container, fragment, backStateName);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
        ///
    }

    protected void loadFrg(Fragment fragment, int container, boolean useBackStack) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(container, fragment, backStateName);
            if (useBackStack) {
                ft.addToBackStack(backStateName);
            }
            ft.commit();
        }
    }

    protected void loadFragment(Fragment fragment, int container, boolean useBackStack) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(container, fragment, backStateName);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * If SDK_INT version is >= LOLLIPOP than it changes status bar color
     */
    protected void setPrimaryStatusBarColor(int color) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getCompatColor(color));
        }
    }

    protected int getCompatColor(int id) {
        return ContextCompat.getColor(this, id);
    }

    protected void applyCustomFont(TextView tv, String typeFaceLocation) {
        tv.setTypeface(Typeface.createFromAsset(getAssets(), typeFaceLocation));
    }

    protected void TOAST(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void TOAST(int msg) {
        Toast.makeText(BaseActivity.this, getResString(msg), Toast.LENGTH_SHORT).show();
    }

    protected DataManager getDataManager() {
        return CoreApp.getInstance().getDataManager();
    }

    /**
     * Spans part of word
     *
     * @param view  - TextView to spanWord
     * @param start - start of desired word
     * @param end   - end of desired word
     * @param color - color to apply
     */
    protected void spanWord(TextView view, int start, int end, int color) {
        Spannable str = (Spannable) view.getText();
        str.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
