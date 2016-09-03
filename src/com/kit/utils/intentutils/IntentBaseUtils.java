package com.kit.utils.intentutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kit.utils.GsonUtils;
import com.kit.utils.log.ZogUtils;

import java.util.Iterator;
import java.util.Set;

public class IntentBaseUtils {
    private static final String KEY_DATA = "zhao_bundle_data";

    public static void gotoNextActivity(Context packageContext, Class<?> cls) {
        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        // intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);

        // overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * @param packageContext
     * @param cls
     * @param anmiResIn      进入动画
     * @param anmiResOut     退出动画
     */
    public static void gotoNextActivity(Context packageContext, Class<?> cls, int anmiResIn, int anmiResOut) {
        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        // intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);

        ((Activity) packageContext).overridePendingTransition(anmiResIn, anmiResOut);
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        boolean isCloseThis) {
        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        // intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
        if (isCloseThis) {
            ((Activity) packageContext).finish();

        }
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        int RequestFlag) {
        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        // intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, RequestFlag);
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoNextActivity(Fragment fragment, Class<?> cls,
                                        int RequestFlag) {
        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        // intent.putExtras(bundle);
        intent.setClass(fragment.getActivity(), cls);
        fragment.startActivityForResult(intent, RequestFlag);
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        Bundle bundle, int RequestFlag) {
        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        ((Activity) packageContext).startActivityForResult(intent, RequestFlag);
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }


    public static void gotoNextActivity(Fragment fragment, Class<?> cls,
                                        Bundle bundle, int RequestFlag) {
        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        intent.putExtras(bundle);
        intent.setClass(fragment.getActivity(), cls);
        fragment.startActivityForResult(intent, RequestFlag);
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }


    public static void setResult(Context packageContext, Bundle bundle,
                                 int resultCode, boolean isCloseThis) {

        Intent intent = new Intent();
        intent.putExtras(bundle);
        if (packageContext instanceof Activity) {
            ((Activity) packageContext).setResult(resultCode, intent);

            if (isCloseThis) {
                ((Activity) packageContext).finish();
            }
        }
    }

    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
    }

    public static void gotoNextActivity(Context packageContext, Class<?> cls,
                                        Bundle bundle, boolean isCloseThis) {
        if (isCloseThis) {
            ((Activity) packageContext).finish();

        }

        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);

        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoNextActivity(Context packageContext, Intent intent) {

        packageContext.startActivity(intent);
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * 启动Service
     *
     * @param packageContext
     * @param intent
     */
    public static void gotoService(Context packageContext, Intent intent) {
        packageContext.startService(intent);
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 启动Service
     *
     * @param packageContext
     * @param intent
     * @param isCloseThis    是否关闭当前Activity
     */
    public static void gotoService(Context packageContext, Intent intent, boolean isCloseThis) {
        if (isCloseThis) {
            ((Activity) packageContext).finish();
        }

        packageContext.startService(intent);
        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 启动Service
     *
     * @param packageContext
     * @param cls
     * @param bundle
     */
    public static void gotoService(Context packageContext, Class<?> cls,
                                   Bundle bundle) {

        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        packageContext.startService(intent);

        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 启动Service
     *
     * @param packageContext
     * @param cls
     * @param bundle
     * @param isCloseThis    是否关闭当前Activity
     */
    public static void gotoService(Context packageContext, Class<?> cls,
                                   Bundle bundle, boolean isCloseThis) {
        if (isCloseThis) {
            ((Activity) packageContext).finish();

        }

        Intent intent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("USERNAME",
        // et_username.getText().toString());
        intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        packageContext.startService(intent);

        // ((Activity) packageContext).overridePendingTransition(
        // R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * 从Service到Activity
     *
     * @param packageContext
     * @param cls
     */
    public static void gotoActivityFromService(Context packageContext,
                                               Class<?> cls) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);

    }


    /**
     * 从Service到Activity
     *
     * @param packageContext
     */
    public static void gotoActivityFromService(Context packageContext, String action, String uri,
                                               BundleData data) {

        Uri realUri = Uri.parse(uri);
        Intent intent = new Intent(action, realUri);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        pushData(intent, data);
        packageContext.startActivity(intent);
    }


    /**
     * 从Service到Activity
     *
     * @param packageContext
     * @param cls
     */
    public static void gotoActivityFromReceiver(Context packageContext,
                                                Class<?> cls) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
    }

    /**
     * @param intent
     */
    public static void printIntent(Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Set<String> keys = bundle.keySet();
                Iterator<String> it = keys.iterator();
                ZogUtils.i( "printIntent start");
                while (it.hasNext()) {
                    String key = it.next();
                    ZogUtils.i( "[" + key + "=" + bundle.get(key) + "]");
                }
                ZogUtils.i( "printIntent end");
            }
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }


    /**
     * 压入数据
     *
     * @param intent
     * @param data
     */
    public static void pushData(Intent intent, BundleData data) {
        String value = GsonUtils.toJson(data);
        intent.putExtra(KEY_DATA, value);
    }


    /**
     * 获取传过去的值
     *
     * @return
     */
    public static BundleData getData(Intent intent) {
        BundleData bundleData = null;

        try {
            String bundleDataStr = intent.getExtras().getString(KEY_DATA);
            bundleData = GsonUtils.getObj(bundleDataStr, BundleData.class);
        } catch (Exception e) {
//            ZogUtils.showException(e);
        }
        return bundleData;
    }


}
