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
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {
        T t = null;
        try {
            Object o = hashMap.get(key);
            t = GsonUtils.getObj(GsonUtils.toJson(o), clazz);
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
    public <T> List<T> getList(String key, Type typeOfT) {
        List<T> t = null;

        try {
            Object o = hashMap.get(key);
            t = GsonUtils.getList(GsonUtils.toJson(o), typeOfT);
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
    public <T> ArrayList<T> getArrayList(String key, Type typeOfT) {
        ArrayList<T> t = null;

        try {
            Object o = hashMap.get(key);
            String json = GsonUtils.toJson(o);
            ZogUtils.e("json:" + json);
            t = GsonUtils.getArrayList(json, typeOfT);
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

    public BundleData() {
    }

    protected BundleData(Parcel in) {
        try {
            this.hashMap = (HashMap<String, Object>) in.readHashMap(HashMap.class.getClassLoader());
        }catch (Exception e){
            ZogUtils.showException(e);
        }
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