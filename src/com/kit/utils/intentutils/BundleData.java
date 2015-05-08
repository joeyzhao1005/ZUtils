package com.kit.utils.intentutils;

import java.util.HashMap;

public class BundleData implements Cloneable {
    HashMap hashMap = new HashMap();

    public void putObject(String key, Object value) {
        hashMap.put(key, value);
    }

    public Object getObject(String key) {
        Object o = hashMap.get(key);
        return o;
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
}