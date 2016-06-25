package com.www.funone.util;

import android.view.View;

/**
 * Created by vitaliy.herasymchuk on 4/8/16.
 */
public class ViewUtil {

    public static void hideView(View view) {
        if (Validator.isObjectValid(view)) {
            view.setVisibility(View.GONE);
        }
    }

    public static void showView(View view) {
        if (Validator.isObjectValid(view)) {
            view.setVisibility(View.VISIBLE);
        }
    }

}
