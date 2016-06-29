package com.www.funone.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.www.funone.CoreApp;

import java.util.Set;

/**
 * Created by andrew.volosko on 3/18/15.
 */
public class Pref {

    private static final String TAG = Pref.class.getSimpleName();

    private static final String PREF_NAME = "fun_one";

    public static final String PREF_UID = "uid";
    public static final String APP_SETTINGS_KEY = "APP_SETTINGS_KEY";
    public static final String PREF_USER_LOGGED_IN = "user_logged_in";

    private static SharedPreferences sPref;
    private static SharedPreferences.Editor edit;

    public static void init() {
        sPref = CoreApp.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        edit = sPref.edit();
    }

    public static SharedPreferences getPrefs() {
        if (edit == null) init();
        return sPref;
    }

    public static void setStringSet(String key, Set<String> value) {
        if (edit == null) init();

        Logger.i(TAG, "setString " + key + "=" + value);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            edit.putStringSet(key, value);
            edit.commit();
        }
    }

    public static Set<String> getStringSet(String key) {
        if (edit == null) init();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            return sPref.getStringSet(key, null);
        }
        return null;
    }

    public static void setString(String key, String value) {
        if (edit == null) init();

        Logger.i(TAG, "setString " + key + "=" + value);
        edit.putString(key, value);
        edit.commit();
    }


    public static void setLong(String key, long value) {
        if (edit == null) init();

        Logger.i(TAG, "setLong " + key + "=" + value);
        edit.putLong(key, value);
        edit.commit();
    }

    public static void setBoolean(String key, boolean value) {
        if (edit == null) init();

        Logger.i(TAG, "setBoolean " + key + "=" + value);
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void setInt(String key, int value) {
        if (edit == null) init();

        Logger.i(TAG, "setInt " + key + "=" + value);
        edit.putInt(key, value);
        edit.commit();
    }

    public static void setPrefFloat(String key, float value) {
        if (edit == null) init();

        Logger.i(TAG, "setPrefFloat " + key + "=" + value);
        edit.putFloat(key, value);
        edit.commit();
    }

    public static String getString(String key) {
        if (sPref == null) init();

        return sPref.getString(key, null);
    }

    public static String getString(String key, String def) {
        if (sPref == null) init();

        return sPref.getString(key, def);
    }

    public static boolean getBoolean(String key) {
        if (sPref == null) init();

        return sPref.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (sPref == null) init();

        return sPref.getBoolean(key, defValue);
    }

    public static long getLong(String key) {
        if (sPref == null) init();

        return sPref.getLong(key, -1);
    }

    public static int getInt(String key) {
        if (sPref == null) init();
        return sPref.getInt(key, -1);
    }

    public static int getInt(String key, int def) {
        if (sPref == null) init();

        return sPref.getInt(key, def);
    }

    public static float getFloat(String key) {
        if (sPref == null) init();

        return sPref.getFloat(key, -1);
    }


}