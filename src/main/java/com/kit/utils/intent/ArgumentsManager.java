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
    /************* fragment 传值的构造   START ************************/
    public void setArguments(Fragment fragment) {

        if (fragment == null) {
            LogUtils.e("fragmentClass can not be null!");
            return;
        }
        putItem(String.valueOf(fragment.hashCode()), itemMap);
        itemMap = null;
    }
//    public void setArguments(Class<? extends Fragment> fragment) {
//
//        if (fragment == null) {
//            LogUtils.e("fragmentClass can not be null!");
//            return;
//        }
//        putItem(fragment.toString(), itemMap);
//    }
//    /**
//     * @param fragmentClass
//     * @param tag           重复的fragment（即一个界面上同时出现多个这个fragment）那么需要加tag，销毁的时候页传递相应的tag
//     */
//    public void setArguments(Class<? extends Fragment> fragmentClass, String tag) {
//        if (StringUtils.isEmptyOrNullStr(tag)) {
//            setArguments(fragmentClass);
//            return;
//        }
//
//        if (fragmentClass == null) {
//            LogUtils.e("fragmentClass can not be null!");
//            return;
//        }
//
//        itemMap.put("IntentManagerTag", tag);
//        putItem(fragmentClass.getName() + tag, itemMap);
//    }
    /************* fragment 传值的构造   END ************************/



    public BundleData getData(Fragment fragment) {
        if (fragment == null)
            return null;

        return map.get(String.valueOf(fragment.hashCode()));
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

    private static final int MAX_SIZE = 100;

    private static ConcurrentHashMap<String, BundleData> map;
    private static ConcurrentHashMap<String, Class> targetMap;

    private static ArgumentsManager mInstance;

    BundleData itemMap;
    boolean isFinishActivityAfterStart;

    public ArgumentsManager() {
        itemMap = new BundleData();
    }

    public static ArgumentsManager get() {
        if (mInstance == null) {
            mInstance = new ArgumentsManager();
            map = new ConcurrentHashMap<String, BundleData>(50);
        }
        return mInstance;
    }
}
