package com.fgj.commonitemdecoration.decoration;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by FGJ on 2018/7/25.
 */
public class DecorationUtil {

    private static Application APP;

    private DecorationUtil() {}

    public static void init(Application application) {
        APP = application;
    }

    public static int getDimensionPixelSize(@DimenRes int id){
        return APP.getResources().getDimensionPixelSize(id);
    }

    public static Drawable getDrawable(@DrawableRes int drawableResId) {
        return ContextCompat.getDrawable(APP, drawableResId);
    }

    public static int getColor(@ColorRes int colorResId) {
        return ContextCompat.getColor(APP, colorResId);
    }

    public static boolean isEmptyString(String... str) {
        if (str == null) {
            return true;
        }
        for (String s : str) {
            if (s == null || s.isEmpty() || s.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
