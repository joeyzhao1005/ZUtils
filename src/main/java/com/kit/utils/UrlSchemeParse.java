package com.kit.utils;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.kit.utils.log.Zog;

import java.util.HashMap;

/**
 * @author joeyzhao
 */
public class UrlSchemeParse {

    /**
     * 得到 uri 携带的参数
     */
    @Nullable
    public static HashMap<String, Object> parse(Intent intent) {
        if (intent == null) {
            return null;
        }
        // 转换成 uri
        Uri uri = intent.getData();
        if (uri == null) {
            return null;
        }
        // 得到参数字符串
        String encoded = uri.getEncodedFragment();
        // 拆分获得单个参数
        if (encoded != null && !encoded.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            String[] params = encoded.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    map.put(keyValue[0], keyValue[1]);
                }
            }

            return map;
        } else {
            return null;
        }
    }

    /**
     * 得到 uri 携带的参数
     */
    @Nullable
    public static HashMap<String, Object> parse(String url) {
        // 转换成 uri
        Uri uri = Uri.parse(url);
        // 得到参数字符串
        String encoded = uri.getEncodedQuery();
        // 拆分获得单个参数
        if (encoded != null && !encoded.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            String[] params = encoded.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    map.put(keyValue[0], keyValue[1]);
                }
            }

            return map;
        } else {
            return null;
        }
    }

}
