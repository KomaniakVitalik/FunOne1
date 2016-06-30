package com.www.funone.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.www.funone.CoreApp;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vitaliy.herasymchuk on 4/6/16.
 */
public class Validator {


    public static boolean isListValid(List<?> list) {
        return list != null && !list.isEmpty();
    }

    public static boolean isStringValid(String text) {
        return !TextUtils.isEmpty(text);
    }

    public static boolean isObjectValid(Object object) {
        return object != null;
    }

    public static String stringNotNull(String str) {
        String string = "";
        if (isStringValid(str)) {
            string = str;
        }
        return string;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static synchronized int UID() {
        int uid = 1;
        if (Pref.getInt(Pref.PREF_UID) != -1) {
            uid = Pref.getInt(Pref.PREF_UID);
            uid += 1;
        }
        Pref.setInt(Pref.PREF_UID, uid);
        return uid;
    }

    public static long TIMESTAMP() {
        return System.currentTimeMillis() / 1000;
    }

    public static boolean canResolveIntent(Intent intent) {
        PackageManager packageManager = CoreApp.getInstance().getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            return true;
        }
        return false;
    }


}
