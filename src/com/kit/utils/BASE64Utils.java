package com.kit.utils;

import android.util.Base64;


public class BASE64Utils {
    // 加密
    public static String encode(byte[] bytes) {
        String s = null;
        try {
            s = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    // 加密
    public static String encode(String str) {
        String s = null;
        try {
            s = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    // 解密
    public static String decode(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            try {
                b = Base64.decode(s, Base64.DEFAULT);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
