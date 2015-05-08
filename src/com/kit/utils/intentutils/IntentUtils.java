package com.kit.utils.intentutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kit.app.ActivityManager;
import com.kit.utils.ZogUtils;

public class IntentUtils extends IntentBaseUtils {

    private static IntentUtils instance;

    private static BundleData bundleData;
//    private static BundleData[] objectHistory;

//    private IntentUtils() {
//        if (null == objectHistory) {
//            ZogUtils.printLog(IntentUtils.class, "objectHistory is empty!!!");
//            objectHistory = new LinkedList<>();
//        }
//    }


    public static IntentUtils getInstance() {
        if (null == instance) {
            instance = new IntentUtils();
        }
//        if (null == objectHistory) {
//            ZogUtils.printLog(IntentUtils.class, "objectHistory is empty!!!");
//            objectHistory = new LinkedList<>();
//        }
        return instance;
    }

    @TargetApi(9)
    private void pushData(BundleData data) {
//        synchronized (objectHistory) {
//            if (data != null) {
//                objectHistory.push(data);
//            }
//            ZogUtils.printLog(IntentUtils.class, "pushData objectHistory.size():" + objectHistory.size());
//        }
        bundleData = data;
    }


    /**
     * 发送广播
     *
     * @param packageContext
     * @param data
     * @param action
     */
    public void sendBroadcast(Context packageContext,
                              BundleData data, String action) {
        Intent intent = new Intent();//创建Intent对象
        pushData(data);
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
    public void setResult(Activity activity,int resultCode,
                              BundleData data) {
        pushData(data);
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
    public void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                       BundleData data) {
        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        pushData(data);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public void gotoNextActivity(Context packageContext, Class<?> cls,
                                 BundleData data) {
        Intent intent = new Intent();
        pushData(data);
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
    public void gotoNextActivity(Context packageContext, Class<?> cls,
                                 BundleData data, boolean isCloseThis) {
        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        pushData(data);
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
     * @param data
     */
    public void gotoNextActivity(Context packageContext, Class<?> cls,
                                 BundleData data, int requestFlag) {
        Intent intent = new Intent();
        pushData(data);
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

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivity(intent);
    }

    /**
     * 从Service到Activity
     *
     * @param packageContext
     * @param cls
     */
    public void gotoSingleNextActivityFromReceiver(Context packageContext,
                                                   Class<?> cls, BundleData data, boolean isCloseThis) {
        if (isCloseThis && (packageContext instanceof Activity)) {
            ((Activity) packageContext).finish();
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pushData(data);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                       BundleData data, boolean isCloseThis) {
        if (isCloseThis && (packageContext instanceof Activity)) {
            ((Activity) packageContext).finish();
        }

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        pushData(data);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivity(intent);
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                       BundleData data, int requestFlag) {
        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        pushData(data);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);
    }

    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     */
    public void gotoSingleNextActivity(Context packageContext, Class<?> cls,
                                       BundleData data, boolean isCloseThis, int requestFlag) {

        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        if (ActivityManager.getInstance().isExistActivity(cls)) {
            ActivityManager.getInstance().popActivity(cls);
        }

        Intent intent = new Intent();
        pushData(data);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, requestFlag);
    }


    /**
     * 跳转界面并传值
     *
     * @param packageContext
     * @param cls
     * @param data
     * @param isCloseThis    是否关闭当前界面
     */
    public void gotoNextActivity(Context packageContext, Class<?> cls,
                                 BundleData data, boolean isCloseThis, int requestFlag) {
        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        pushData(data);
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
    public BundleData getData() {
//        BundleData o = objectHistory.get(0);
//        JsonUtils.printAsJson(o);
//        objectHistory.remove(0);
//        return popData(IntentUtils.class);
//        return objectHistory.pop();
        BundleData bd = null;
        if (bundleData != null) {
            bd = bundleData.clone();
            bundleData = null;
        }

        ZogUtils.printLog(IntentUtils.class, "bd:" + bd + " | bundleData:" + bundleData);
        return bd;
    }

//    /**
//     * 获取传过去的值
//     *
//     * @return
//     */
//    public BundleData getData(Class clazz) {
////        BundleData o = objectHistory.get(0);
////        JsonUtils.printAsJson(o);
//
////        objectHistory.remove(0);
////        return popData(clazz);
//
//        String str = GsonUtils.toJson(d);
//
//        ZogUtils.printLog(IntentUtils.class, d.getClass().getName() + " | " + str);
//
//        BundleData o = GsonUtils.getObj(str, clazz);
//        d = null;
//        return o;
////        return objectHistory.pop();
//    }


}
