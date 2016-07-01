package com.www.funone.managers;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import com.www.funone.CoreApp;
import com.www.funone.util.Validator;

/**
 * Created by vitaliy.herasymchuk on 7/1/16.
 */
public class PermissionsManager {

    private static PermissionsManager instance = new PermissionsManager();

    private PermissionsManager() {
    }

    public static PermissionsManager getInstance() {
        if (!Validator.isObjectValid(instance)) {
            instance = new PermissionsManager();
        }
        return instance;
    }

    public static boolean hasPermissions(Activity activity, int requestCode, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && CoreApp.getInstance() != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(CoreApp.getInstance(), permission) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(activity, permission, requestCode);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param activity     - Activity
     * @param permissions  - Permissions array
     * @param grantResults - grant results array
     */
    public static void handleDenyAndNeverAskAgain(Activity activity, String[] permissions, int[] grantResults, int requestCode, OnNeverAskAgainListener listener) {
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (shouldShowPermissionRationale(activity, permissions[0])) {
                requestPermission(activity, permissions[0], requestCode);
            } else {
                listener.onNeverAskAgain();
            }
        }
    }

    /**
     * Requests desired permission
     *
     * @param activity    - Activity
     * @param permission  - String permission value
     * @param requestCode - user's request code
     */
    public static void requestPermission(Activity activity, String permission, int requestCode) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permission},
                requestCode);
    }


    public static boolean shouldShowPermissionRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public interface OnNeverAskAgainListener{
        void onNeverAskAgain();
    }


}
