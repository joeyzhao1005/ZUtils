package com.kit.utils.intent;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.kit.utils.MD5Utils;
import com.kit.utils.log.Zog;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by joeyzhao on 2018/2/2.
 * <p>
 * 本类用来做intent传值
 * <p>
 * 中间不做序列化处理，可能性能更好，存取速度更快
 * <p>
 * 最重要的是解决parcleable传值溢出的问题
 * <p>
 * 此类 仅作为 activity及fragment 之间传值，broadcast请使用BroadcastCenter；
 * 若有跳转到该类使用PendingIntent传值的话，则跳转到该类的所有传值方法请使用普通的传值方式（即Android默认的方式）；
 * 如：跳转到MainActivity的有通过点击通知栏通知的方式跳入的，则所有跳入MainActivity的都是用普通传值方式。
 * <p>
 * <p>
 * 切记：
 * 切记：
 * 切记：
 * 切记：
 * 切记：
 * 切记：
 * 切记：
 * 切记：
 * 如果跳转到该类的使用了IntentManager，那么所有跳转到该类的都要使用IntentManager。
 * 若使用IntentManager过程中发现有类似上述MainActivity不能使用IntentManager的情况，那么所有的跳转都要重新写回普通的传值方式。
 * <p>
 * 另：使用ARouter或其它路由的Activity，一律不可使用IntentManager。可使用IntentManger代替ARouter。
 */
public class IntentManager {
    /************* intent 的启动   START ************************/
    public void startActivity(Context context, ActivityOptions activityOptions) {
        if (context == null) {
            return;
        }

        if (mIntent == null) {
            throw new IllegalStateException("intent must be setted first.");
        }
//
//        if (mIntent.getComponent() == null) {
//            throw new IllegalStateException("intent must be setted class first.");
//        }
        if (!(context instanceof Activity)) {
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && activityOptions != null) {
            Bundle bundle = activityOptions.toBundle();
            context.startActivity(mIntent, bundle);
        } else {
            context.startActivity(mIntent);
        }
        if (isFinishActivityAfterStart && (context instanceof Activity)) {
            ((Activity) context).finish();
        }
        mIntent = null;
        itemMap = null;
        isClassicMode = false;
    }

    public void startActivity(Activity activity, ActivityOptions activityOptions) {
        if (activity == null) {
            return;
        }

        if (mIntent == null) {
            throw new IllegalStateException("intent must be setted first.");
        }

//        if (mIntent.getComponent() == null) {
//            throw new IllegalStateException("intent must be setted class first.");
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && activityOptions != null) {
            Bundle bundle = activityOptions.toBundle();
            activity.startActivity(mIntent, bundle);
        } else {
            activity.startActivity(mIntent);
        }
        if (isFinishActivityAfterStart) {
            activity.finish();
        }
        mIntent = null;
        itemMap = null;
        isClassicMode = false;
    }


    public void startActivity(Context context) {
        if (context == null) {
            return;
        }

        if (mIntent == null) {
            throw new IllegalStateException("intent must be setted first.");
        }

//        if (mIntent.getComponent() == null) {
//            throw new IllegalStateException("intent must be setted class first.");
//        }

        putItem(mIntent, itemMap);
        if (!(context instanceof Activity)) {
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(mIntent);
        if (isFinishActivityAfterStart && (context instanceof Activity)) {
            ((Activity) context).finish();
        }

        mIntent = null;
        itemMap = null;
        isClassicMode = false;
    }

    public void startActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        if (mIntent == null) {
            throw new IllegalStateException("intent must be setted first.");
        }

//        if (mIntent.getComponent() == null) {
//            throw new IllegalStateException("intent must be setted class first.");
//        }

        putItem(mIntent, itemMap);
        activity.startActivity(mIntent);
        if (isFinishActivityAfterStart) {
            activity.finish();
        }

        mIntent = null;
        itemMap = null;
        isClassicMode = false;
    }

    public void startActivityForResult(Activity activity, int code) {
        if (activity == null) {
            return;
        }

        if (mIntent == null) {
            throw new IllegalStateException("intent must be setted first.");
        }

//        if (mIntent.getComponent() == null) {
//            throw new IllegalStateException("intent must be setted class first.");
//        }

        putItem(mIntent, itemMap);
        activity.startActivityForResult(mIntent, code);
        if (isFinishActivityAfterStart) {
            activity.finish();
        }
        mIntent = null;
        itemMap = null;
        isClassicMode = false;
    }

    public void startService(Context context) {
        if (context == null) {
            return;
        }

        if (mIntent == null) {
            throw new IllegalStateException("intent must be setted first.");
        }

//        if (mIntent.getComponent() == null) {
//            throw new IllegalStateException("intent must be setted class first.");
//        }

        putItem(mIntent, itemMap);
        context.startService(mIntent);
        mIntent = null;
        itemMap = null;
        isClassicMode = false;
    }

    public void stopService(Context context) {
        if (context == null) {
            return;
        }

        if (mIntent == null) {
            throw new IllegalStateException("intent must be setted first.");
        }

//        if (mIntent.getComponent() == null) {
//            throw new IllegalStateException("intent must be setted class first.");
//        }

        putItem(mIntent, itemMap);
        context.stopService(mIntent);
        mIntent = null;
        itemMap = null;
    }
    /************* intent 的启动   END ************************/

    /************* intent 跨项目的构造   START ************************/
    public IntentManager target(Context packageContext, String target) {
        if (targetMap == null) {
            Zog.e("You must init IntentManager first before target");
        }
        if (targetMap != null) {
            Class clazz = targetMap.get(target);
            setClass(packageContext, clazz);
        }
        return this;
    }
    /************* intent 跨项目的构造   END ************************/


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

    public IntentManager addFlags(int flags) {
        getIntent().addFlags(flags);
        return this;
    }

    public IntentManager addCategory(String category) {
        getIntent().addCategory(category);
        return this;
    }

    public IntentManager classicMode() {
        this.isClassicMode = true;
        return this;
    }

    /**
     * @param isFinishActivityAfterStart 打开新的界面之后是否关闭当前界面
     * @return
     */
    public IntentManager finishActivityAfterStart(boolean isFinishActivityAfterStart) {
        this.isFinishActivityAfterStart = isFinishActivityAfterStart;
        return this;
    }

    /************* intent 的构造   END ************************/


    /************* bundle 的构造  START *********/


    public IntentManager bundleData(BundleData bundleData) {
        this.itemMap = bundleData;
        return this;
    }


    /**
     * 往item中压入数据 无敌的方法
     *
     * @param key
     * @param value
     */
    public <T> IntentManager put(String key, T value) {
        if (value == null) {
            return this;
        }
        getData().put(key, value);
        return this;
    }
    /************* bundle 的构造  END *********/


    /************* intent 的取值  START *********/


    public BundleData getData(Intent intent) {
        if (intent == null) {
            return null;
        }
        BundleData bundleData = null;

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            bundleData = bundle.getParcelable("bundle");
        }

        return bundleData;
    }


    /************* intent 的取值  END *********/


    /************* intent 的传值销毁  START *********/
    /**
     * 销毁
     * 在基类的onDestory中调用最好
     */
    public void destory(Intent intent) {
    }


    /************* intent 的传值销毁  END *********/


    private Intent getIntent() {
        if (mIntent == null) {
            mIntent = new Intent();
        }
        return mIntent;
    }


    private BundleData getData() {
        if (itemMap == null) {
            itemMap = new BundleData();
        }
        return itemMap;
    }


    /**
     * 往map中压数据
     *
     * @param intent
     * @param bundleData
     */
    private void putItem(Intent intent, BundleData bundleData) {
        if (intent == null) {
            return;
        }

        intent.putExtra("bundle", bundleData);
    }

    private String getKey(Intent intent) {
        String classStr = intent.getComponent() == null ? "" : intent.getComponent().getClassName();
        String action = intent.getAction() == null ? "" : intent.getAction();

        String categories = "";

        if (intent.getCategories() != null && !intent.getCategories().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : intent.getCategories()) {
                categories = sb
                        .append(categories)
                        .append(s)
                        .append(";")
                        .toString();
            }
        }

        return MD5Utils.getMD5String(classStr + "_" + action + "_" + categories);
    }


    public static void register(String intentKey, Class classTarget) {
        if (targetMap == null) {
            Zog.e("You must init IntentManager first before target");
            return;
        }

        targetMap.put(intentKey, classTarget);
    }



    private static ConcurrentHashMap<String, Class> targetMap;

    private static IntentManager mInstance;
    private boolean isClassicMode;
    Intent mIntent;
    BundleData itemMap;
    boolean isFinishActivityAfterStart;


    public IntentManager() {
        mIntent = new Intent();
        itemMap = new BundleData();
        isClassicMode = false;
    }


    public void init(ConcurrentHashMap map) {
        targetMap = map;
    }

    public static IntentManager get() {
        if (mInstance == null) {
            mInstance = new IntentManager();
        }
        return mInstance;
    }
}
