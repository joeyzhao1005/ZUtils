package com.kit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kit.app.ActivityManager;

public class IntentUtils extends IntentBaseUtils {

    private static IntentUtils instance;

    private static Object object;
//    private static Object[] objects;


    public static IntentUtils getInstance() {
        if (null == instance) {
            instance = new IntentUtils();
        }
        return instance;
    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param obj
     */
    public void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                 Object obj) {
        if(ActivityManager.getInstance().isExistActivity(cls)){
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        object = obj;
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param obj
     */
    public void gotoNextActivity(Context packageContext, Class<?> cls,
                                 Object obj) {
        Intent intent = new Intent();
        object = obj;
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param obj
     * @param isCloseThis    是否关闭当前界面
     */
    public void gotoNextActivity(Context packageContext, Class<?> cls,
                                 Object obj, boolean isCloseThis) {
        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        object = obj;
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);

        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param obj
     */
    public void gotoNextActivity(Context packageContext, Class<?> cls,
                                 Object obj, int requestFlag) {
        Intent intent = new Intent();
        object = obj;
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);

    }



    /**
     * 跳转界面
     *
     * @param packageContext
     * @param cls
     * @param isCloseThis
     */
    public void gotoSingleNextActivity(Context packageContext, Class<?> cls, boolean isCloseThis) {
        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        if(ActivityManager.getInstance().isExistActivity(cls)){
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivity(intent);
    }
    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param obj
     */
    public void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                       Object obj, boolean isCloseThis) {
        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        if(ActivityManager.getInstance().isExistActivity(cls)){
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        object = obj;
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivity(intent);
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param obj
     */
    public void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                       Object obj, int requestFlag) {
        if(ActivityManager.getInstance().isExistActivity(cls)){
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        object = obj;
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);
    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param obj
     */
    public void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                       Object obj, boolean isCloseThis, int requestFlag) {

        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        if(ActivityManager.getInstance().isExistActivity(cls)){
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        object = obj;
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param obj
     * @param isCloseThis    是否关闭当前界面
     */
    public void gotoNextActivity(Context packageContext, Class<?> cls,
                                 Object obj, boolean isCloseThis, int requestFlag) {
        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        object = obj;
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);


        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * 获取传过去的值
     *
     * @return
     */
    public Object getData() {
        return object;
    }


}
