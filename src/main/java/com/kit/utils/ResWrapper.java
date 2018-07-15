package com.kit.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.kit.app.application.AppMaster;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Zhao on 16/7/17.
 */
public class ResWrapper {
    private ResWrapper() {
    }


    public static Context getApplicationContext() {
        return AppMaster.getInstance().getAppContext();
    }

//    public Context getContext() {
//        return applicationContext;
//    }

    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }


    public static int getColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(colorId, null);
        } else {
            return ContextCompat.getColor(getApplicationContext(), colorId);
        }
    }

    public static float getDimension(int dimensionId) {
        return getResources().getDimension(dimensionId);
    }

    public static Drawable getDrawable(int drawableId, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(drawableId, theme);
        } else {
            return getDrawable(drawableId);
        }
    }

    public static Drawable getDrawable(int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getDrawable(drawableId, null);
        } else {
            return ContextCompat.getDrawable(getApplicationContext(), drawableId);
        }
    }


    public static Bitmap getBitmap(int drawableId) {
        return BitmapFactory.decodeResource(getResources(), drawableId);
    }


    public static String getString(@StringRes int stringId) {
        return getResources().getString(stringId);
    }


    public static String getString(@StringRes int stringId, Object... formatArgs) {
        return getResources().getString(stringId, formatArgs);
    }


    public static String[] getStringArray(int arrayId) {
        return getResources().getStringArray(arrayId);
    }


    public static int[] getIntArray(int arrayId) {
        return getResources().getIntArray(arrayId);
    }

    public static List<String> getStringList(int stringId) {
        try {
            return ArrayUtils.toList(getResources().getStringArray(stringId));
        } catch (Exception e) {
            return null;
        }
    }


    public String getText4ResStringArray(int intR) {
        String[] hibernate_copy = getResources().getStringArray(intR);
        String textStr = (String) ArrayUtils.getOne(hibernate_copy);
        return textStr;

    }

    public static Resources getResources() {
        return getApplicationContext().getResources();
    }
}
