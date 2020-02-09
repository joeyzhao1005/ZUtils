package com.kit.sharedpreferences;

import android.content.Context;

import com.kit.app.application.AppMaster;
import com.kit.utils.ResWrapper;

import java.util.List;
import java.util.Map;

public class DataCache {


    public void saveSharedPreferences(String key, String value) {
        sharedPreferencesUtils.saveSharedPreferences(key, value);
    }

    public String loadStringSharedPreference(String key, String stringValue) {
        return sharedPreferencesUtils.loadStringSharedPreference(key, stringValue);

    }

    public String loadStringSharedPreference(String key) {
        return sharedPreferencesUtils.loadStringSharedPreference(key);
    }

    public void saveSharedPreferences(String key, int value) {
        sharedPreferencesUtils.saveSharedPreferences(key, value);

    }

    public int loadIntSharedPreference(String key, int intValue) {
        return sharedPreferencesUtils.loadIntSharedPreference(key, intValue);
    }

    public int loadIntSharedPreference(String key) {
        return sharedPreferencesUtils.loadIntSharedPreference(key);
    }


    public void saveSharedPreferences(String key, long value) {
        sharedPreferencesUtils.saveSharedPreferences(key, value);

    }

    public void saveSharedPreferences(String key, float value) {
        sharedPreferencesUtils.saveSharedPreferences(key, value);
    }


    public float loadFloatSharedPreference(String key, Float floatValue) {
        return sharedPreferencesUtils.loadFloatSharedPreference(key, floatValue);
    }

    public float loadFloatSharedPreference(String key) {
        return sharedPreferencesUtils.loadFloatSharedPreference(key);
    }

    public void saveSharedPreferences(String key, Long value) {
        sharedPreferencesUtils.saveSharedPreferences(key, value);
    }

    public long loadLongSharedPreference(String key, long longValue) {
        return sharedPreferencesUtils.loadLongSharedPreference(key, longValue);
    }


    public float loadLongSharedPreference(String key, float longValue) {
        return sharedPreferencesUtils.loadLongSharedPreference(key, longValue);
    }

    public long loadLongSharedPreference(String key) {
        return sharedPreferencesUtils.loadLongSharedPreference(key);
    }

    public void saveSharedPreferences(String key, Boolean value) {
        sharedPreferencesUtils.saveSharedPreferences(key, value);
    }

    public boolean loadBooleanSharedPreference(String key, boolean booleanValue) {
        return sharedPreferencesUtils.loadBooleanSharedPreference(key, booleanValue);
    }

    public boolean loadBooleanSharedPreference(String key) {
        return sharedPreferencesUtils.loadBooleanSharedPreference(key);
    }

    public void saveAllSharePreference(String keyName, List<?> list) {
        sharedPreferencesUtils.saveAllSharePreference(keyName, list);
    }

    public Map<String, ?> loadAllSharePreference(String key) {
        return sharedPreferencesUtils.loadAllSharePreference(key);

    }

    public void removeKey(String key) {
        sharedPreferencesUtils.removeKey(key);
    }

    public void removeAllKey() {
        sharedPreferencesUtils.removeAllKey();
    }

    /**
     * 判断是否存在key
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return sharedPreferencesUtils.contains(key);
    }


    /**
     * 清空
     *
     * @return
     */
    public void clear() {
        sharedPreferencesUtils.clear();
    }

    /**
     * 使用SharedPreferences保存对象类型的数据 先将对象类型转化为二进制数据，然后用特定的字符集编码成字符串进行保存
     *
     * @param object     要保存的对象
     * @param context
     * @param shaPreName 保存的文件名
     * @param saveLength 保持有多少个object
     */
    public static void saveObject(Context context, String shaPreName, String saveTag, Object object,
                                  int saveLength) {
        SharedPreferencesUtils.saveObject(context, shaPreName, saveTag, object, saveLength);

    }

    /**
     * 根据文件名取得存储的数据对象 先将取得的数据转化成二进制数组，然后转化成对象
     *
     * @param context
     * @param shaPreName 读取数据的文件名
     * @return
     */
    public static List<Object> getObject(Context context,
                                         String shaPreName, String saveTag) {

        return SharedPreferencesUtils.getObject(context, shaPreName, saveTag);
    }

    /**
     * 使用SharedPreferences保存对象类型的数据 先将对象类型转化为二进制数据，然后用特定的字符集编码成字符串进行保存
     *
     * @param context
     * @param shaPreName 保存的文件名
     */
    public static void newSP(Context context, String shaPreName) {
        SharedPreferencesUtils.newSP(context, shaPreName);
    }


    /**
     * 根据文件名取得存储的数据对象 先将取得的数据转化成二进制数组，然后转化成对象
     *
     * @param context
     * @param shaPreName 读取数据的文件名
     * @return
     */
    public static boolean isExist(Context context,
                                  String shaPreName) {
        return SharedPreferencesUtils.isExist(context, shaPreName);
    }


    private void init(Context context) {
        if (context == null) {
            context = AppMaster.getInstance().getAppContext();
        }
        sharedPreferencesUtils = new SharedPreferencesUtils(context, sharedPreferencesName);
    }


    public static DataCache getInstance() {
        if (dataCache == null) {
            dataCache = new DataCache();
        }

        if (dataCache.sharedPreferencesUtils == null) {
            dataCache.init(ResWrapper.getApplicationContext());
        }
        return dataCache;
    }

    private static DataCache dataCache;

    SharedPreferencesUtils sharedPreferencesUtils;

    private static final String sharedPreferencesName = "data_cache";


}