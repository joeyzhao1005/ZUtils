package com.kit.utils.intentutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.kit.utils.MapUtils;
import com.kit.utils.StringUtils;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by joeyzhao on 2018/2/2.
 * <p>
 * 本类用来做intent传值
 * <p>
 * 中间不做序列化处理，可能性能更好，存取速度更快
 * <p>
 * 最重要的是解决parcleable传值溢出的问题
 */

public class IntentManager {
    /************* intent 的启动   START ************************/
    public void startActivity(Activity activity, Intent intent) {
        if (activity == null)
            return;

        this.mIntent = intent;
        if (mIntent == null)
            throw new IllegalStateException("intent must be setted first.");

        if (mIntent.getComponent() == null)
            throw new IllegalStateException("intent must be setted class first.");

        if (mIntent.getExtras() != null) {
            itemMap = new BundleData();
            itemMap.put("bundle", mIntent.getExtras());
            putItem(mIntent.getComponent().getClassName(), itemMap);
        }
        mIntent.putExtras(new Bundle());
        activity.startActivity(mIntent);
        mIntent = null;
        itemMap = null;
    }

    public void startActivity(Activity activity) {
        if (activity == null)
            return;

        if (mIntent == null)
            throw new IllegalStateException("intent must be setted first.");

        if (mIntent.getComponent() == null)
            throw new IllegalStateException("intent must be setted class first.");

        putItem(mIntent.getComponent().getClassName(), itemMap);
        activity.startActivity(mIntent);
        mIntent = null;
        itemMap = null;
    }

    public void startActivityForResult(Activity activity, int code) {
        if (activity == null)
            return;

        if (mIntent == null)
            throw new IllegalStateException("intent must be setted first.");

        if (mIntent.getComponent() == null)
            throw new IllegalStateException("intent must be setted class first.");

        putItem(mIntent.getComponent().getClassName(), itemMap);
        activity.startActivityForResult(mIntent, code);
        mIntent = null;
        itemMap = null;
    }

    public void startService(Context context) {
        if (context == null)
            return;

        if (mIntent == null)
            throw new IllegalStateException("intent must be setted first.");

        if (mIntent.getComponent() == null)
            throw new IllegalStateException("intent must be setted class first.");

        putItem(mIntent.getComponent().getClassName(), itemMap);
        context.startService(mIntent);
        mIntent = null;
        itemMap = null;
    }

    public void stopService(Context context) {
        if (context == null)
            return;

        if (mIntent == null)
            throw new IllegalStateException("intent must be setted first.");

        if (mIntent.getComponent() == null)
            throw new IllegalStateException("intent must be setted class first.");

        putItem(mIntent.getComponent().getClassName(), itemMap);
        context.stopService(mIntent);
        mIntent = null;
        itemMap = null;
    }

    public void sendBroadCast(Context context) {
        if (context == null)
            return;

        if (mIntent == null)
            throw new IllegalStateException("intent must be setted first.");

        if (mIntent.getAction() == null || StringUtils.isEmptyOrNullStr(mIntent.getAction()))
            throw new IllegalStateException("intent must be setted action first.");

        putItem(mIntent.getAction(), itemMap);
        context.sendBroadcast(mIntent);
        mIntent = null;
        itemMap = null;
    }
    /************* intent 的启动   END ************************/


    /************* intent 的构造   START ************************/
    public IntentManager setClass(Context packageContext, Class<?> cls) {
        getIntent().setClass(packageContext, cls);
        return this;
    }

    public IntentManager setData(Uri data) {
        getIntent().setData(data);
        return this;
    }

    public IntentManager setAction(String action) {
        getIntent().setAction(action);
        return this;
    }

    public IntentManager addFlag(int flags) {
        getIntent().addFlags(flags);
        return this;
    }

    public IntentManager addCategory(String category) {
        getIntent().addCategory(category);
        return this;
    }
    /************* intent 的构造   END ************************/


    /************* bundle 的构造  START *********/
    public IntentManager bundle(Bundle bundle) {
        put("bundle", bundle);
        return this;
    }


    /**
     * 往item中压入数据 无敌的方法
     *
     * @param key
     * @param value
     */
    public <T> IntentManager put(String key, T value) {
        getData().put(key, value);
        return this;
    }
    /************* bundle 的构造  END *********/


    /************* intent 的取值  START *********/
    public BundleData getData(String action) {
        if (action == null)
            return null;

        return map.get(action);
    }


    public BundleData getData(Context context) {
        if (context == null)
            return null;

        return map.get(context.getClass().getName());
    }


    public Bundle getBundle(String action) {
        if (action == null)
            return null;
        BundleData bundleData = map.get(action);

        return bundleData.get("bindle");
    }

    public BundleData getBundle(Context context) {
        if (context == null)
            return null;
        BundleData bundleData = map.get(context.getClass().getName());
        return bundleData.get("bindle");
    }
    /************* intent 的取值  END *********/



    /************* intent 的传值销毁  START *********/
    /**
     * 销毁
     * 在基类的onDestory中调用最好
     *
     * @param context
     */
    public void destory(Context context) {
        if (context == null)
            return;

        map.remove(context.getClass().getName());
    }
    /************* intent 的传值销毁  END *********/


    private Intent getIntent() {
        if (mIntent == null)
            mIntent = new Intent();
        return mIntent;
    }


    private BundleData getData() {
        if (itemMap == null)
            itemMap = new BundleData();

        return itemMap;
    }


    /**
     * 往map中压数据
     *
     * @param key
     * @param bundleData
     */
    private void putItem(String key, BundleData bundleData) {
        map.put(key, bundleData);
        if (map.size() > MAX_SIZE) {
            MapUtils.removeFirst(map);
        }
    }

    private static final int MAX_SIZE = 50;

    private static ConcurrentHashMap<String, BundleData> map;

    private static IntentManager mInstance;

    Intent mIntent;
    BundleData itemMap;


    public IntentManager() {
        mIntent = new Intent();
        itemMap = new BundleData();
    }

    public static IntentManager get() {
        if (mInstance == null) {
            mInstance = new IntentManager();
            map = new ConcurrentHashMap<String, BundleData>();
        }
        return mInstance;
    }
}
