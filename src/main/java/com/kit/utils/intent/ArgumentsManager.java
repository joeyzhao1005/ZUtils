package com.kit.utils.intent;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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

public class ArgumentsManager {
    /************* intent 的启动   START ************************/
//    public void startActivity(Activity activity, Intent intent) {
//        if (activity == null)
//            return;
//
//        this.mIntent = intent;
//        if (mIntent == null)
//            throw new IllegalStateException("intent must be setted first.");
//
//        if (mIntent.getComponent() == null)
//            throw new IllegalStateException("intent must be setted class first.");
//
//        if (mIntent.getExtras() != null) {
//            itemMap = new BundleData();
//            itemMap.put("bundle", mIntent.getExtras());
//            putItem(mIntent.getComponent().getClassName(), itemMap);
//        }
//        mIntent.putExtras(new Bundle());
//        activity.startActivity(mIntent);
//        mIntent = null;
//        itemMap = null;
//
//        if (isFinishActivityAfterStart) {
//            activity.finish();
//        }
//    }
//
//
//    public void startActivity(Context context, ActivityOptions activityOptions) {
//        if (context == null)
//            return;
//
//        if (mIntent == null)
//            throw new IllegalStateException("intent must be setted first.");
//
//        if (mIntent.getComponent() == null)
//            throw new IllegalStateException("intent must be setted class first.");
//
//        if (!(context instanceof Activity)) {
//            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && activityOptions != null) {
//            Bundle bundle = activityOptions.toBundle();
//            context.startActivity(mIntent, bundle);
//        } else {
//            context.startActivity(mIntent);
//        }
//        if (isFinishActivityAfterStart && (context instanceof Activity)) {
//            ((Activity) context).finish();
//        }
//        mIntent = null;
//        itemMap = null;
//    }
//
//    public void startActivity(Activity activity, ActivityOptions activityOptions) {
//        if (activity == null)
//            return;
//
//        if (mIntent == null)
//            throw new IllegalStateException("intent must be setted first.");
//
//        if (mIntent.getComponent() == null)
//            throw new IllegalStateException("intent must be setted class first.");
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && activityOptions != null) {
//            Bundle bundle = activityOptions.toBundle();
//            activity.startActivity(mIntent, bundle);
//        } else {
//            activity.startActivity(mIntent);
//        }
//        if (isFinishActivityAfterStart) {
//            activity.finish();
//        }
//        mIntent = null;
//        itemMap = null;
//    }
//
//
//    public void startActivity(Context context) {
//        if (context == null)
//            return;
//
//        if (mIntent == null)
//            throw new IllegalStateException("intent must be setted first.");
//
//        if (mIntent.getComponent() == null)
//            throw new IllegalStateException("intent must be setted class first.");
//
//        putItem(mIntent.getComponent().getClassName(), itemMap);
//        if (!(context instanceof Activity)) {
//            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        context.startActivity(mIntent);
//        if (isFinishActivityAfterStart && (context instanceof Activity)) {
//            ((Activity) context).finish();
//        }
//
//        mIntent = null;
//        itemMap = null;
//    }
//
//    public void startActivity(Activity activity) {
//        if (activity == null)
//            return;
//
//        if (mIntent == null)
//            throw new IllegalStateException("intent must be setted first.");
//
//        if (mIntent.getComponent() == null)
//            throw new IllegalStateException("intent must be setted class first.");
//
//        putItem(mIntent.getComponent().getClassName(), itemMap);
//        activity.startActivity(mIntent);
//        if (isFinishActivityAfterStart) {
//            activity.finish();
//        }
//
//        mIntent = null;
//        itemMap = null;
//    }
//
//    public void startActivityForResult(Activity activity, int code) {
//        if (activity == null)
//            return;
//
//        if (mIntent == null)
//            throw new IllegalStateException("intent must be setted first.");
//
//        if (mIntent.getComponent() == null)
//            throw new IllegalStateException("intent must be setted class first.");
//
//        putItem(mIntent.getComponent().getClassName(), itemMap);
//        activity.startActivityForResult(mIntent, code);
//        if (isFinishActivityAfterStart) {
//            activity.finish();
//        }
//        mIntent = null;
//        itemMap = null;
//    }
//
//    public void startService(Context context) {
//        if (context == null)
//            return;
//
//        if (mIntent == null)
//            throw new IllegalStateException("intent must be setted first.");
//
//        if (mIntent.getComponent() == null)
//            throw new IllegalStateException("intent must be setted class first.");
//
//        putItem(mIntent.getComponent().getClassName(), itemMap);
//        context.startService(mIntent);
//        mIntent = null;
//        itemMap = null;
//    }
//
//    public void stopService(Context context) {
//        if (context == null)
//            return;
//
//        if (mIntent == null)
//            throw new IllegalStateException("intent must be setted first.");
//
//        if (mIntent.getComponent() == null)
//            throw new IllegalStateException("intent must be setted class first.");
//
//        putItem(mIntent.getComponent().getClassName(), itemMap);
//        context.stopService(mIntent);
//        mIntent = null;
//        itemMap = null;
//    }
//
//    public void sendBroadCast(Context context) {
//        if (context == null)
//            return;
//
//        if (mIntent == null)
//            throw new IllegalStateException("intent must be setted first.");
//
//        if (mIntent.getAction() == null || StringUtils.isEmptyOrNullStr(mIntent.getAction()))
//            throw new IllegalStateException("intent must be setted action first.");
//
//        putItem(mIntent.getAction(), itemMap);
//        context.sendBroadcast(mIntent);
//        mIntent = null;
//        itemMap = null;
//    }



    public void set(Class fragmentClass){
        if (fragmentClass == null)
            return;

        putItem(fragmentClass.getName(), itemMap);
//        context.sendBroadcast(mIntent);
//        mIntent = null;
        itemMap = null;
    }
    /************* intent 的启动   END ************************/


    /************* intent 的构造   START ************************/
//    public ArgumentsManager setClass(Context packageContext, Class<?> cls) {
//        getIntent().setClass(packageContext, cls);
//        return this;
//    }
//
//    public ArgumentsManager setData(Uri data) {
//        getIntent().setData(data);
//        return this;
//    }
//
//    public ArgumentsManager setAction(String action) {
//        getIntent().setAction(action);
//        return this;
//    }
//
//    public ArgumentsManager addFlag(int flags) {
//        getIntent().addFlags(flags);
//        return this;
//    }
//
//    public ArgumentsManager addCategory(String category) {
//        getIntent().addCategory(category);
//        return this;
//    }

    /**
     * @param isFinishActivityAfterStart 打开新的界面之后是否关闭当前界面
     * @return
     */
    public ArgumentsManager finishActivityAfterStart(boolean isFinishActivityAfterStart) {
        this.isFinishActivityAfterStart = isFinishActivityAfterStart;
        return this;
    }

    /************* intent 的构造   END ************************/


    /************* bundle 的构造  START *********/


    public ArgumentsManager bundleData(BundleData bundleData) {
        this.itemMap = bundleData;
        return this;
    }

    public ArgumentsManager bundle(Bundle bundle) {
        put("bundle", bundle);
        return this;
    }


    /**
     * 往item中压入数据 无敌的方法
     *
     * @param key
     * @param value
     */
    public <T> ArgumentsManager put(String key, T value) {
        getData().put(key, value);
        return this;
    }
    /************* bundle 的构造  END *********/


    /************* intent 的取值  START *********/



    public BundleData getArguments(Fragment fragment) {
        if (fragment == null)
            return null;

        return map.get(fragment.getClass().getName());
    }

    /************* intent 的取值  END *********/


    /************* intent 的传值销毁  START *********/
    /**
     * 销毁
     * 在基类的onDestory中调用最好
     *
     * @param context
     */
    public void destory(Fragment fragment) {
        if (fragment == null)
            return;

        map.remove(fragment.getClass().getName());
    }

    /************* intent 的传值销毁  END *********/

//
//    private Intent getIntent() {
//        if (mIntent == null)
//            mIntent = new Intent();
//        return mIntent;
//    }


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
        if (bundleData != null) {
            map.put(key, bundleData);
            if (map.size() > MAX_SIZE) {
                MapUtils.removeFirst(map);
            }
        }
    }

    private static final int MAX_SIZE = 50;

    private static ConcurrentHashMap<String, BundleData> map;

    private static ArgumentsManager mInstance;

//    Intent mIntent;
    BundleData itemMap;
    boolean isFinishActivityAfterStart;


    public ArgumentsManager() {
//        mIntent = new Intent();
        itemMap = new BundleData();
    }

    public static ArgumentsManager get() {
        if (mInstance == null) {
            mInstance = new ArgumentsManager();
            map = new ConcurrentHashMap<String, BundleData>();
        }
        return mInstance;
    }
}
