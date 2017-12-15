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

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Zhao on 16/7/17.
 */
public class ResWrapper {
    private WeakReference<Context> context;

    private static ResWrapper resWrapper;

    private static Context applicationContext;

    private ResWrapper() {
    }

    public static ResWrapper getInstance() {

        if (resWrapper == null)
            resWrapper = new ResWrapper();

        return resWrapper;
    }


    public ResWrapper setContext(Context context) {
        this.context = new WeakReference<Context>(context);
        getApplicationContext();
        return resWrapper;
    }


    public Context getApplicationContext() {
        if (applicationContext == null && context != null && context.get() != null) {
            applicationContext = context.get().getApplicationContext();
        }
        return applicationContext;
    }

//    public Context getContext() {
//        return applicationContext;
//    }

    public void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else
            view.setBackgroundDrawable(drawable);
    }


    public int getColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return getResources().getColor(colorId, null);
        else
            return ContextCompat.getColor(getApplicationContext(), colorId);
    }

    public float getDimension(int dimensionId) {
        return getResources().getDimension(dimensionId);
    }

    public Drawable getDrawable(int drawableId, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            return getResources().getDrawable(drawableId, theme);
        else
            return getDrawable(drawableId);
    }

    public Drawable getDrawable(int drawableId) {
        return getResources().getDrawable(drawableId);
    }


    public Bitmap getBitmap(int drawableId) {
        return BitmapFactory.decodeResource(getResources(), drawableId);
    }


    public String getString(@StringRes int stringId) {
        return getResources().getString(stringId);
    }


    public String getString(@StringRes int stringId, Object... formatArgs) {
        return getResources().getString(stringId, formatArgs);
    }


    public String[] getStringArray(int stringId) {
        return getResources().getStringArray(stringId);
    }

    public List<String> getStringList(int stringId) {
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

    public Resources getResources() {
        return getApplicationContext().getResources();
    }
}
