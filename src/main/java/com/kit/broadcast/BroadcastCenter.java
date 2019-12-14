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

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.kit.app.application.AppMaster;
import com.kit.utils.StringUtils;
import com.kit.utils.intent.BundleData;
import com.kit.utils.log.Zog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author joeyzhao
 */
public class BroadcastCenter {
    private LocalBroadcastManager localBroadcastManager;

    private Intent intent;
    private String action;
    private BundleData data;
    private boolean isUsing = false;

    private static final Set<BroadcastCenter> BROADCAST_CENTER_POOL = new CopyOnWriteArraySet<>();

    private static final int MAX = 5;


    private static synchronized BroadcastCenter createNew() {
        BroadcastCenter broadcastCenter = new BroadcastCenter();
        broadcastCenter.localBroadcastManager = LocalBroadcastManager.getInstance(AppMaster.getInstance().getAppContext());
        broadcastCenter.isUsing = true;
        BROADCAST_CENTER_POOL.add(broadcastCenter);


        //池中大于MAX 移除一个没在使用的
        if (BROADCAST_CENTER_POOL.size() > MAX) {
            for (BroadcastCenter bc : BROADCAST_CENTER_POOL) {
                if (!bc.isUsing) {
                    BROADCAST_CENTER_POOL.remove(bc);
                    break;
                }
            }
        }
        return broadcastCenter;
    }

    public static synchronized BroadcastCenter get() {
        if (BROADCAST_CENTER_POOL.isEmpty()) {
            return createNew();
        } else {
            BroadcastCenter getOne = null;
            for (BroadcastCenter broadcastCenter : BROADCAST_CENTER_POOL) {
                if (!broadcastCenter.isUsing) {
                    getOne = broadcastCenter;
                    break;
                }
            }

            if (getOne == null) {
                return createNew();
            } else {
                getOne.reset();
                getOne.isUsing = true;
                getOne.localBroadcastManager = LocalBroadcastManager.getInstance(AppMaster.getInstance().getAppContext());
                return getOne;
            }

        }
    }


    public void reset() {
        intent = null;
        action = null;
        data = null;
        localBroadcastManager = null;
        isUsing = false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
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

//        if (intent == null) {
//            Zog.d("intent is not created");
//        }

        if (intent == null) {
            if (!StringUtils.isEmptyOrNullStr(action)) {
                intent = new Intent(action);
            }
//            Zog.d("intent created with action");
        }

    }


    public void broadcast() {

        createIntent();

        if (data != null) {
            intent.putExtra(action, data);
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
        isUsing = false;
//        Zog.d("broadcast | sendBroadcast finished");

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

