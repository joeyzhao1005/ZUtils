/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.laoyuegou.android.lib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.laoyuegou.android.lib.intent.BundleData;
import com.laoyuegou.android.lib.utils.LogUtils;
import com.laoyuegou.android.lib.utils.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BroadcastCenter {
    private LocalBroadcastManager localBroadcastManager;

    private CopyOnWriteArrayList<BroadcastReceiver> broadcastReceiverList = new CopyOnWriteArrayList<BroadcastReceiver>();
    private static ConcurrentHashMap<String, BundleData> map;
    private static BroadcastCenter singleBroadcast = new BroadcastCenter();


    private Intent intent;
    private String action;
    private BundleData data;


    public static BroadcastCenter getInstance() {
        return singleBroadcast;
    }

    public boolean init(Context context) {
        LogUtils.d("init | Enter");

        if (localBroadcastManager != null) {
            LogUtils.e("init | localBroadcastManager is null");
            return false;
        }

        if (null == context) {
            LogUtils.e("init | context is null");
            return false;
        }

        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        map = new ConcurrentHashMap<String, BundleData>(50);
        broadcastReceiverList = new CopyOnWriteArrayList<BroadcastReceiver>();
        LogUtils.d("init | Leave");
        return true;
    }


    public BroadcastCenter intent(Intent intent) {
        this.intent = intent;
        return this;
    }


    public BroadcastCenter action(String action) {
        this.action = action;
        return this;
    }

    /**
     * 往item中压入数据 无敌的方法
     *
     * @param key
     * @param value
     */
    public <T> BroadcastCenter put(String key, T value) {
        getData().put(key, value);
        return this;
    }

    public BroadcastCenter extras(BundleData bundleData) {
        this.data = bundleData;
        return this;
    }

    private BundleData getData() {
        if (data == null)
            data = new BundleData();
        return data;
    }

    public void broadcast() {
        if (null == localBroadcastManager) {
            LogUtils.e("localBroadcastManager is null, is the BroadcastCenter inited?");
            return;
        }

        if (intent == null) {
            LogUtils.d("intent is not created");
        }

        if (intent == null) {
            if (!StringUtils.isEmptyOrNullStr(action)) {
                intent = new Intent(action);
            }
            LogUtils.d("intent created with action");
            if (!StringUtils.isEmptyOrNullStr(action)) {
                intent = new Intent(action);
            }
            if (data != null) {
                map.put(action, data);
                LogUtils.d("intent created with data");
            } else {
                map.remove(action);
            }
        }

        if (intent == null) {
            LogUtils.e("intent create failed");
            return;
        }
        localBroadcastManager.sendBroadcast(intent);
        LogUtils.d("broadcast | sendBroadcast finished");
        intent = null;
        action = null;
        data = null;
    }

    public void registerReceiver(BroadcastReceiver br, String action) {
        if (null == br || null == action || null == localBroadcastManager) {
            LogUtils.e("registerReceiver | param is null or localBroadcastManager is null");
            return;
        }

        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(action);

        localBroadcastManager.registerReceiver(br, iFilter);
        broadcastReceiverList.add(br);
    }


    /**
     *
     * @param br
     * @param actions 至少传入一个
     */
    public void unregisterReceiver(BroadcastReceiver br, @NonNull String... actions) {
        if (null == br || null == localBroadcastManager) {
            LogUtils.e("unregisterReceiver | param is null or localBroadcastManager is null");
            return;
        }

        if (actions != null) {
            for (String ac : actions) {
                map.remove(ac);
            }
        }

        localBroadcastManager.unregisterReceiver(br);
        broadcastReceiverList.remove(br);
    }

    public void unregisterAllReceiver() {
        if (null == localBroadcastManager) {
            LogUtils.e("unregisterAllReceiver | localBroadcastManager is null");
            return;
        }

        for (BroadcastReceiver tempBr : broadcastReceiverList) {
            localBroadcastManager.unregisterReceiver(tempBr);
        }
        map.clear();
        broadcastReceiverList.clear();
    }


}
