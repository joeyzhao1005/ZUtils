package com.kit.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by Zhao on 16/7/17.
 */
public class ResWrapper {
    private Context context;

    private static ResWrapper resWrapper;

    private static Context applicationContext;

    public static ResWrapper getInstance() {

        if (resWrapper == null)
            resWrapper = new ResWrapper();

        return resWrapper;
    }


    public ResWrapper setContext(Context context) {
        this.context = context;
        return resWrapper;
    }


    public Context getApplicationContext() {
        if (applicationContext == null)
            applicationContext = context.getApplicationContext();
        return applicationContext;
    }

    public Context getContext() {
        return context;
    }

    public void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else
            view.setBackgroundDrawable(drawable);
    }


    public int getColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getResources().getColor(colorId, null);
        else
            return ContextCompat.getColor(context, colorId);
    }

    public float getDimension(int dimensionId) {
        return context.getResources().getDimension(dimensionId);
    }

    public Drawable getDrawable(int drawableId, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            return context.getResources().getDrawable(drawableId, theme);
        else
            return getDrawable(drawableId);
    }

    public Drawable getDrawable(int drawableId) {
        return context.getResources().getDrawable(drawableId);
    }


    public Bitmap getBitmap(int drawableId) {
        return BitmapFactory.decodeResource(context.getResources(), drawableId);
    }


    public String getString(int stringId) {
        return context.getResources().getString(stringId);
    }


    public String[] getStringArray(int stringId) {
        return context.getResources().getStringArray(stringId);
    }


    public String getText4ResStringArray(int intR) {
        String[] hibernate_copy = context.getResources().getStringArray(intR);
        String textStr = (String) ArrayUtils.getOne(hibernate_copy);
        return textStr;

    }

    public Resources getResources() {
        return context.getResources();
    }
}
