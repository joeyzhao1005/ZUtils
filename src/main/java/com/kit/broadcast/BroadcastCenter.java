/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.kit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.kit.app.application.AppMaster;
import com.kit.utils.StringUtils;
import com.kit.utils.intent.BundleData;
import com.kit.utils.log.Zog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BroadcastCenter {
    private LocalBroadcastManager localBroadcastManager;

    private Intent intent;
    private String action;
    private BundleData data;


    public static BroadcastCenter getInstance() {
        BroadcastCenter broadcastCenter = new BroadcastCenter();
        broadcastCenter.localBroadcastManager = LocalBroadcastManager.getInstance(AppMaster.getInstance().getAppContext());
        return broadcastCenter;
    }


    public BroadcastCenter intent(Intent intent) {
        this.intent = intent;
        return this;
    }


    public BroadcastCenter action(String action) {
        this.action = action;
        return this;
    }


    public BroadcastCenter extras(BundleData bundleData) {
        this.data = bundleData;
        return this;
    }

    public BroadcastCenter extras(Bundle bundle) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }
        intent.putExtras(bundle);
        return this;
    }


    public BroadcastCenter put(String key, ArrayList<? extends Parcelable> value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public BroadcastCenter put(String key, Parcelable[] value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }


    public BroadcastCenter put(String key, Parcelable value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public BroadcastCenter put(String key, float value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public BroadcastCenter put(String key, double value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public BroadcastCenter put(String key, long value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public BroadcastCenter put(String key, boolean value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }

    public BroadcastCenter put(String key, int value) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, value);
        return this;
    }


    public BroadcastCenter put(String key, String str) {
        createIntent();

        if (intent == null) {
            Zog.e("intent create failed");
            return this;
        }

        intent.putExtra(key, str);
        return this;
    }

    private void createIntent() {
        if (null == localBroadcastManager) {
            localBroadcastManager = LocalBroadcastManager.getInstance(AppMaster.getInstance().getAppContext());
        }

        if (intent == null) {
            Zog.d("intent is not created");
        }

        if (intent == null) {
            if (!StringUtils.isEmptyOrNullStr(action)) {
                intent = new Intent(action);
            }
            Zog.d("intent created with action");
        }

    }


    public void broadcast() {

        createIntent();

        if (data != null) {
            intent.putExtra(action, data);
            Zog.d("intent created with data");
        }

        if (intent == null) {
            Zog.e("intent create failed");
            return;
        }


        if (action == null) {
            Zog.e("action is null!!!");
            return;
        }

        intent.setAction(action);

        if (null == localBroadcastManager) {
            localBroadcastManager = LocalBroadcastManager.getInstance(AppMaster.getInstance().getAppContext());
        }
        localBroadcastManager.sendBroadcast(intent);
        Zog.d("broadcast | sendBroadcast finished");

    }

    public void registerReceiver(BroadcastReceiver br, List<String> actions) {
        if (null == br || null == actions) {
            Zog.e("registerReceiver | param is null ");
            return;
        }

        IntentFilter iFilter = new IntentFilter();
        if (actions != null) {
            for (String action : actions) {
                iFilter.addAction(action);
            }
        }

        if (null == localBroadcastManager) {
            localBroadcastManager = LocalBroadcastManager.getInstance(AppMaster.getInstance().getAppContext());
        }
        localBroadcastManager.registerReceiver(br, iFilter);
    }


    public void registerReceiver(BroadcastReceiver br, String... actions) {
        if (actions == null || actions.length <= 0) {
            return;
        }
        registerReceiver(br, Arrays.asList(actions));
    }


    /**
     * @param br
     */
    public void unregisterReceiver(BroadcastReceiver br) {
        if (null == br) {
            Zog.e("unregisterReceiver | param is null");
            return;
        }

        if (null == localBroadcastManager) {
            localBroadcastManager = LocalBroadcastManager.getInstance(AppMaster.getInstance().getAppContext());
        }

        try {
            localBroadcastManager.unregisterReceiver(br);
        } catch (Exception e) {

        }
    }


}

