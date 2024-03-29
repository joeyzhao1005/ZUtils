package com.kit.utils.intent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.kit.utils.ApiLevel;
import com.kit.utils.MD5Utils;
import com.kit.utils.ValueOf;
import com.kit.utils.log.Zog;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author joeyzhao
 * @date 2018/2/2
 *
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
        if (cls != null) {
            if (!(packageContext instanceof Activity)) {
                getIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            getIntent().setClass(packageContext, cls);
        }
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


    public IntentManager bundleData(Bundle bundleData) {
        this.itemMap = bundleData;
        return this;
    }

    public IntentManager bundleData(BundleData bundleData) {
        this.itemMap = getData();
        HashMap<String, Object> map = bundleData.getHashMap();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (entry.getValue() != null) {
                put(entry.getKey(), entry.getValue());
            }
        }
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
        //TODO 待完善
        if (value instanceof Bundle) {
            getData().putAll((Bundle) value);
        } else if (value instanceof Parcelable) {
            getData().putParcelable(key, (Parcelable) value);
        } else if (value instanceof Parcelable[]) {
            getData().putParcelableArray(key, (Parcelable[]) value);
        } else if (value instanceof ArrayList && !((ArrayList) value).isEmpty() && ((ArrayList) value).get(0) instanceof Parcelable) {
            getData().putParcelableArrayList(key, (ArrayList<? extends Parcelable>) value);
        } else if (value instanceof String) {
            getData().putString(key, (String) value);
        } else if (value instanceof String[]) {
            getData().putStringArray(key, (String[]) value);
        } else if (value instanceof ArrayList && !((ArrayList) value).isEmpty() && ((ArrayList) value).get(0) instanceof String) {
            getData().putStringArrayList(key, (ArrayList<String>) value);
        } else if (value instanceof CharSequence) {
            getData().putCharSequence(key, (CharSequence) value);
        } else if (value instanceof CharSequence[]) {
            getData().putCharSequenceArray(key, (CharSequence[]) value);
        } else if (value instanceof ArrayList && !((ArrayList) value).isEmpty() && ((ArrayList) value).get(0) instanceof CharSequence) {
            getData().putCharSequenceArrayList(key, (ArrayList<CharSequence>) value);
        } else if (value instanceof Integer) {
            getData().putInt(key, ValueOf.toInt(value));
        } else if (value instanceof ArrayList && !((ArrayList) value).isEmpty() && ((ArrayList) value).get(0) instanceof Integer) {
            getData().putIntegerArrayList(key, (ArrayList<Integer>) value);
        } else if (value instanceof Long) {
            getData().putLong(key, ValueOf.toLong(value));
        } else if (value instanceof Float) {
            getData().putFloat(key, ValueOf.toFloat(value));
        } else if (value instanceof Double) {
            getData().putDouble(key, ValueOf.toDouble(value));
        } else if (value instanceof Short) {
            getData().putShort(key, ValueOf.toShort(value));
        } else if (value instanceof Byte) {
            getData().putByte(key, ValueOf.toByte(value));
        } else if (value instanceof IBinder && ApiLevel.ATLEAST_JB_MR2) {
            getData().putBinder(key, (IBinder) value);
        } else if (value instanceof char[]) {
            getData().putCharArray(key, (char[]) value);
        } else if (value instanceof Character) {
            getData().putChar(key, (Character) value);
        } else if (value instanceof int[]) {
            getData().putIntArray(key, (int[]) value);
        } else if (value instanceof long[]) {
            getData().putLongArray(key, (long[]) value);
        } else if (value instanceof float[]) {
            getData().putFloatArray(key, (float[]) value);
        } else if (value instanceof double[]) {
            getData().putDoubleArray(key, (double[]) value);
        } else if (value instanceof short[]) {
            getData().putShortArray(key, (short[]) value);
        } else if (value instanceof byte[]) {
            getData().putByteArray(key, (byte[]) value);
        }

        return this;
    }
    /************* bundle 的构造  END *********/


    /************* intent 的取值  START *********/


    @NonNull
    public Bundle getData(Intent intent) {
        if (intent == null) {
            return emptyBundleData;
        }
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            return bundle;
        } else {
            return emptyBundleData;
        }
    }


    /************* intent 的取值  END *********/


    private Intent getIntent() {
        if (mIntent == null) {
            mIntent = new Intent();
        }
        return mIntent;
    }


    private Bundle getData() {
        if (itemMap == null) {
            itemMap = new Bundle();
        }
        return itemMap;
    }


    /**
     * @param intent
     * @param bundle
     */
    private void putItem(Intent intent, Bundle bundle) {
        if (intent == null || bundle == null) {
            return;
        }

        intent.putExtras(bundle);
    }

    /**
     * 检测 响应某个Intent的Activity 是否存在
     *
     * @param context
     * @param intent
     * @return
     */
    @SuppressLint("WrongConstant")
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }


    private static ConcurrentHashMap<String, Class> targetMap;

    private static IntentManager mInstance;
    private static Bundle emptyBundleData = new Bundle();
    Intent mIntent;
    Bundle itemMap;
    boolean isFinishActivityAfterStart;


    public IntentManager() {
        mIntent = new Intent();
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
