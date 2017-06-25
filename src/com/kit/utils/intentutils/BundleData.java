package com.kit.utils.intentutils;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.kit.utils.GsonUtils;
import com.kit.utils.log.ZogUtils;

import java.io.Serializable;
import java.lang.reflect.Type;
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
    public void put(String key, Object value) {
        hashMap.put(key, value);
    }


    /**
     * 获取Object
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getObject(String key) {
        T t = null;
        try {
            t = (T)hashMap.get(key);
        } catch (Exception e) {
            ZogUtils.showException(e);
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
            ZogUtils.showException(e);
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
            ZogUtils.showException(e);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.hashMap);
    }

    public BundleData(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }


    protected BundleData(Parcel in) {
        try {
            this.hashMap = (HashMap<String, Object>) in.readHashMap(HashMap.class.getClassLoader());
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }

    String flag;

    public static final Creator<BundleData> CREATOR = new Creator<BundleData>() {
        @Override
        public BundleData createFromParcel(Parcel source) {
            try {
                return new BundleData(source);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public BundleData[] newArray(int size) {
            return new BundleData[size];
        }
    };
}