package com.kit.utils.intent;

import android.app.Activity;
import android.os.Bundle;

import com.kit.utils.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author joeyzhao
 */
public class BundleData implements Cloneable {
    HashMap<String, Object> hashMap = new HashMap();

    public HashMap<String, Object> getHashMap() {
        return hashMap;
    }

    public boolean containsKey(String key) {

        if (hashMap == null) {
            return false;
        }

        return hashMap.containsKey(key);
    }

    /**
     * 压入数据
     *
     * @param key
     * @param value
     */
    public <T> BundleData put(String key, T value) {
        hashMap.put(key, value);

        if (hashMap.size() > 20) {
            MapUtils.removeFirst(hashMap);
        }
        return this;
    }


    /**
     * 获取Object
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Activity activity, String key, T defaultValue) {
        if (hashMap == null || !hashMap.containsKey(key)) {
            return null;
        }
        T t = null;
        try {
            t = (T) hashMap.get(key);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        if (t == null && activity != null && activity.getIntent() != null && activity.getIntent().getExtras() != null) {
            try {
                return (T) (activity.getIntent().getExtras().get(key) == null ? defaultValue : activity.getIntent().getExtras().get(key));
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    /**
     * 获取Object
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (hashMap == null || !hashMap.containsKey(key)) {
            return null;
        }
        T t = null;
        try {
            t = (T) hashMap.get(key);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return t;
    }

    /**
     * 获取数值
     * <p>
     * <p>
     * 如果是获取数值型的 用这个方法，如果获取的是对象，用上面的get(String key)方法
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key, T defaultValue) {
        if (hashMap == null || !hashMap.containsKey(key)) {
            return defaultValue;
        }
        T t = null;
        try {
            t = (T) hashMap.get(key);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

        if (t == null) {
            t = defaultValue;
        }
        return t;
    }

    /**
     * 获取List
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key) {
        List<T> t = null;

        try {
            t = (List<T>) hashMap.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }


    /**
     * 获取List
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> ArrayList<T> getArrayList(String key) {
        ArrayList<T> t = null;

        try {
            t = (ArrayList<T>) hashMap.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }


    @Override
    public BundleData clone() {
        BundleData o = null;
        try {
            o = (BundleData) super.clone();//Object中的clone()识别出你要复制的是哪一个对象。
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;
    }

    // 打印所有的 BundleData extra 数据
    public static String printBundleData(BundleData bundle) {
        StringBuilder sb = new StringBuilder();
        for (Object key : bundle.hashMap.keySet()) {
            sb.append("\nkey:" + key + ", value:" + bundle.hashMap.get(key));
        }
        return sb.toString();
    }


    // 打印所有的 intent extra 数据
    public static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (Object key : bundle.keySet()) {
            sb.append("\nkey:" + key + ", value:" + bundle.get(key.toString()));
        }
        return sb.toString();
    }


    public BundleData() {
    }

//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        if (hashMap == null || hashMap.isEmpty()) {
//            return;
//        }
//
//        dest.writeInt(hashMap.size());
//
//        Iterator<Map.Entry<String, Object>> it = hashMap.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, Object> entry = it.next();
//            if (entry.getValue() != null) {
//                dest.writeString(entry.getKey());
//                if (entry.getValue() instanceof Parcelable) {
//                    dest.writeParcelable((Parcelable) entry.getValue(), flags);
//                } else {
//                    //TODO 转化成为Parcelable
//                    dest.writeSerializable((Serializable) entry.getValue());
//                }
//            }
//        }
//    }
//    protected BundleData(Parcel in) {
//        int size = in.readInt();
//
//        this.hashMap = new HashMap<String, Object>(size);
//        for (int i = 0; i < size; i++) {
//            String key = in.readString();
//            //TODO 转化成为Parcelable
//
//            Object value = null;
//            try {
//                value = in.readParcelable(null);
//            } catch (BadParcelableException e) {
//            }
//            if (value == null) {
//                value = in.readSerializable();
//            }
//            hashMap.put(key, value);
//        }
//
//    }


}