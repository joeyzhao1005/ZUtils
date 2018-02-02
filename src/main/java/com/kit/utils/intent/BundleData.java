package com.kit.utils.intent;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.kit.utils.MapUtils;
import com.kit.utils.log.Zog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BundleData implements Cloneable, Parcelable {
    HashMap<String, Object> hashMap = new HashMap();


    public boolean containsKey(String key) {

        if (hashMap == null)
            return false;

        return hashMap.containsKey(key);
    }

    /**
     * 压入数据
     *
     * @param key
     * @param value
     */
    public <T> void put(String key, T value) {
        hashMap.put(key, value);

        if (hashMap.size() > 20) {
            MapUtils.removeFirst(hashMap);
        }
    }


    /**
     * 获取Object
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        T t = null;
        try {
            t = (T) hashMap.get(key);
        } catch (Exception e) {
            Zog.showException(e);
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
            Zog.showException(e);
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
            Zog.showException(e);
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


    public HashMap<String, Object> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, Object> hashMap) {
        this.hashMap = hashMap;
    }


    public BundleData() {
    }

    /**
     * 尽量跳转到哪个界面传哪个
     *
     * @param flag
     */
    public BundleData(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }


    String flag;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.hashMap);
        dest.writeString(this.flag);
    }

    protected BundleData(Parcel in) {
        this.hashMap = (HashMap<String, Object>) in.readSerializable();
        this.flag = in.readString();
    }

    public static final Creator<BundleData> CREATOR = new Creator<BundleData>() {
        @Override
        public BundleData createFromParcel(Parcel source) {
            return new BundleData(source);
        }

        @Override
        public BundleData[] newArray(int size) {
            return new BundleData[size];
        }
    };
}