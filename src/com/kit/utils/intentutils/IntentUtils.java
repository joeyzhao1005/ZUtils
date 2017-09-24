package com.kit.utils.intentutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.kit.app.ActivityManager;
import com.kit.utils.DensityUtils;
import com.kit.utils.StringUtils;
import com.kit.utils.log.ZogUtils;

import java.net.URISyntaxException;

public class IntentUtils extends IntentBaseUtils {


    public static Intent getIntentFromString(String string) {
        if (string == null || string.isEmpty()) {
            return new Intent();
        } else {
            try {
                return new Intent().parseUri(string, 0);
            } catch (URISyntaxException e) {
                return new Intent();
            }
        }
    }

    public static String getIntentAsString(Intent intent) {
        if (intent == null) {
            return "";
        } else {
            return intent.toUri(0);
        }
    }

    /**
     * 发送广播
     *
     * @param packageContext
     * @param data
     * @param action
     */
    public static void sendBroadcast(Context packageContext,
                                     BundleData data, String action) {
        Intent intent = new Intent();//创建Intent对象
        pushData(intent, data);
        intent.setAction(action);
        packageContext.sendBroadcast(intent);
    }

    /**
     * 发送广播
     *
     * @param activity
     * @param data
     * @param resultCode
     */
    public static void setResult(Activity activity, int resultCode,
                                 BundleData data) {
        Intent intent = new Intent();//创建Intent对象
        pushData(intent, data);
        activity.setResult(resultCode);
        activity.finish();
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public static void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                              BundleData data) {


        Intent intent = new Intent();
        pushData(intent, data);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }
    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        BundleData data) {
        Intent intent = new Intent();//创建Intent对象
        pushData(intent, data);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     * @param isCloseThis    是否关闭当前界面
     */
    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        BundleData data, boolean isCloseThis) {

        Intent intent = new Intent();//创建Intent对象
        pushData(intent, data);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);

        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     * @param isCloseThis    是否关闭当前界面
     */
    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        BundleData data, ActivityOptionsCompat transitionActivityOptions, boolean isCloseThis) {

        Intent intent = new Intent();//创建Intent对象
        pushData(intent, data);
        intent.setClass(packageContext, cls);

        ActivityCompat.startActivity(packageContext,
                intent, transitionActivityOptions.toBundle());

        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }
    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        BundleData data, int requestFlag) {
        Intent intent = new Intent();//创建Intent对象
        pushData(intent, data);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);

    }





    public static ActivityOptions getActivityOptions(Context context, View v, int enterResId, int exitResId) {
        ActivityOptions opts = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int left = 0, top = 0;
            int width = DensityUtils.dip2px(100);
            int height = DensityUtils.dip2px(100);


            if (v != null) {
                width = v.getMeasuredWidth();
                height = v.getMeasuredHeight();
                top = v.getPaddingTop();
                left = v.getPaddingLeft();
            }

            if (v instanceof ImageView) {
                Drawable icon = ((ImageView) v).getDrawable();
                if (icon != null) {
                    Rect bounds = icon.getBounds();
                    left = (width - bounds.width()) / 2;
                    top = v.getPaddingTop();
                    width = bounds.width();
                    height = bounds.height();
                }
            }

            opts = ActivityOptions.makeClipRevealAnimation(v, left, top, width, height);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Below L, we use a scale up animation
            opts = ActivityOptions.makeScaleUpAnimation(v, 0, 0,
                    v.getMeasuredWidth(), v.getMeasuredHeight());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // On L devices, we use the device default slide-up transition.
            // On L MR1 devices, we a custom version of the slide-up transition witch
            // doesn't have the delay present in the device default.
            opts = ActivityOptions.makeCustomAnimation(context,
                    enterResId, exitResId);
        }

        if (opts == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                // On L devices, we use the device default slide-up transition.
                // On L MR1 devices, we a custom version of the slide-up transition witch
                // doesn't have the delay present in the device default.
                opts = ActivityOptions.makeCustomAnimation(context,
                        enterResId, exitResId);
            }
        }
        return opts;
    }

    /**
     * 跳转界面并传值
     *
     * @param fragment
     * @param cls
     * @param data
     */
    public static void gotoNextActivity(Fragment fragment, Class<?> cls,
                                        BundleData data, int requestFlag) {
        Intent intent = new Intent();//创建Intent对象
        pushData(intent, data);
        intent.setClass(fragment.getActivity(), cls);
        fragment.startActivityForResult(intent, requestFlag);

    }


    /**
     * 跳转界面
     *
     * @param packageContext
     * @param cls
     * @param isCloseThis
     */
    public static void gotoSingleNextActivity(Context packageContext, Class<?> cls, boolean isCloseThis) {

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivity(intent);

        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

    }


    /**
     * 从Service到Activity
     *
     * @param packageContext
     * @param cls
     */
    public static void gotoSingleNextActivityFromReceiver(Context packageContext,
                                                          Class<?> cls, BundleData data, boolean isCloseThis) {

        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pushData(intent, data);
            intent.setClass(packageContext, cls);
            packageContext.startActivity(intent);

            if (isCloseThis && (packageContext instanceof Activity)) {
                ((Activity) packageContext).finish();
            }
        } catch (Exception e) {
            ZogUtils.showException(e);
        }

    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public static void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                              BundleData data, boolean isCloseThis) {

        Intent intent = new Intent();
        pushData(intent, data);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivity(intent);

        if (isCloseThis && (packageContext instanceof Activity)) {
            ((Activity) packageContext).finish();
        }

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }

    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public static void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                              BundleData data, int requestFlag) {

        Intent intent = new Intent();
        pushData(intent, data);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }

    }


    /**
     * 跳转界面并传值
     *
     * @param fragment
     * @param cls
     * @param data
     */
    public static void gotoSingleNextActivity(Fragment fragment, Class<?> cls,
                                              BundleData data, int requestFlag) {


        Intent intent = new Intent();
        pushData(intent, data);
        intent.setClass(fragment.getActivity(), cls);
        fragment.startActivityForResult(intent, requestFlag);

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public static void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                              BundleData data, boolean isCloseThis, int requestFlag) {


        Intent intent = new Intent();
        pushData(intent, data);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);


        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }
    }


    /**
     * 跳转界面并传值
     *
     * @param fragment
     * @param cls
     * @param data
     */
    public static void gotoSingleNextActivity(Fragment fragment, Class<?> cls,
                                              BundleData data, int requestFlag, boolean isCloseThis) {


        Intent intent = new Intent();
        pushData(intent, data);
        intent.setClass(fragment.getActivity(), cls);
        fragment.startActivityForResult(intent, requestFlag);


        if (isCloseThis) {
            fragment.getActivity().finish();
        }

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     * @param isCloseThis    是否关闭当前界面
     */
    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        BundleData data, boolean isCloseThis, int requestFlag) {


        Intent intent = new Intent();
        pushData(intent, data);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);

        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }
    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param data
     * @param isCloseThis    是否关闭当前界面
     */
    public static void gotoNextActivity(Context packageContext, String action, String uri,
                                        BundleData data, boolean isCloseThis) {


        Intent intent;
        if (!StringUtils.isEmptyOrNullStr(uri)) {
            Uri realUri = Uri.parse(uri);
            intent = new Intent(action, realUri);

        } else {
            intent = new Intent(action);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pushData(intent, data);
        packageContext.startActivity(intent);

        if (packageContext instanceof Activity) {
            if (isCloseThis) {
                ((Activity) packageContext).finish();
            }
        }
    }


    /**
     * 跳转界面并传值
     *
     * @param fragment
     * @param cls
     * @param data
     * @param isCloseThis 是否关闭当前界面
     */
    public static void gotoNextActivity(Fragment fragment, Class<?> cls,
                                        BundleData data, boolean isCloseThis, int requestFlag) {

        Intent intent = new Intent();
        pushData(intent, data);
        intent.setClass(fragment.getActivity(), cls);
        fragment.startActivityForResult(intent, requestFlag);

        if (isCloseThis) {
            (fragment.getActivity()).finish();
        }

    }

    public static Bundle gotoNextActivity4JellyBean(Context context, Class<?> cls, View v, int enterResId, int exitResId) {

        Intent intent = new Intent();//创建Intent对象
        intent.setClass(context, cls);

        ActivityOptions activityOptions = getActivityOptions(context, v, enterResId, exitResId);

        Bundle bundle = null;
        if (activityOptions != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            bundle = activityOptions.toBundle();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, bundle);
        }else {
            context.startActivity(intent);
        }
        return bundle;
    }

    public static Bundle gotoNextActivity4JellyBean(Context context, Class<?> cls, BundleData data, View v, int enterResId, int exitResId) {

        Intent intent = new Intent();//创建Intent对象
        pushData(intent, data);
        intent.setClass(context, cls);

        ActivityOptions activityOptions = getActivityOptions(context, v, enterResId, exitResId);

        Bundle bundle = null;
        if (activityOptions != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            bundle = activityOptions.toBundle();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, bundle);
        }else {
            context.startActivity(intent);
        }
        return bundle;
    }

}
