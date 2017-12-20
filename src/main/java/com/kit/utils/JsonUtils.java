package com.kit.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.kit.utils.log.ZogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static URL url;

    // 解析处理过的json字符串，需调用getJosnStr()，以取得处理过的json字符串
    public static JSONObject str2JSONObj(String str) {

        String jsonStr = "{jsonStr:" + str + "}";

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsonStr);

            jsonObject = jsonObject.getJSONObject("jsonStr");

        } catch (JSONException e) {

            Log.e("error", "JsonUtils.str2JSONObj() have something wrong...");

        }
        return jsonObject;
    }

    // 解析处理过的json字符串，需调用getJosnStr()，以取得处理过的json字符串
    public static JSONArray str2JSONArray(String str) {
        ZogUtils.i(str);
        if (StringUtils.isEmptyOrNullStr(str))
            return null;

        JSONArray jsonArray = null;
        try {
            str = formatJsonStr(str);
//            str = str.substring(1,str.length()-1);
            jsonArray = new JSONArray(str);
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
        return jsonArray;
    }

    // 解析JsonArray,返回ArrayList<JSONObject>
    public static ArrayList<JSONObject> resolveJsonArray(JSONArray jsonArray) {
        ArrayList<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempJsonObject = null;
            try {
                tempJsonObject = jsonArray.getJSONObject(i);
                jsonObjectList.add(tempJsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return jsonObjectList;
    }

    public static String formatJsonStr(String inputString) {

        if (TextUtils.isEmpty(inputString))
            return "";
        // String dataStr = inputString;
        // System.out.println(dataStr);
        String paramsStr = inputString;

        paramsStr = paramsStr.replaceAll("\\\\", "");
        // System.out.println("UMailMessageData1: " + paramsStr);
        paramsStr = paramsStr.replaceAll("\"\\{", "{");
        // System.out.println("UMailMessageData2: " + paramsStr);
        paramsStr = paramsStr.replaceAll("\\}\"", "}");


        paramsStr = paramsStr.replaceAll("\"\\[", "[");

        paramsStr = paramsStr.replaceAll("\\]\"", "]");

        paramsStr = paramsStr.replaceAll("\\\"", "\"");

        String jsonStr = paramsStr;

        return jsonStr;

    }

    public static void replaceStringValue(JSONObject jobj, String keyName, String replaceValue) throws JSONException {
        String value;
        Iterator it = jobj.keys();

        while (it.hasNext()) {

            String key = it.next().toString();

            // 将所有的空串去掉
            if (jobj.getString(key) == null) {

                continue;
            }

            if (keyName.equals(key)) {      //试剂类型
                ZogUtils.i("img_url got!!!!!!!!!!!!!!");
                jobj.put(keyName, replaceValue);
            }
        }
//        try {
//            params.put(SDK.IMG_URL_TAG, URLEncoder.encode(params.getString(SDK.IMG_URL_TAG), SDK.DEFAULT_CODE));
//        } catch (Exception e) {
//        }


    }

    /**
     * JSONObject转为map
     *
     * @param object json对象
     * @return 转化后的Map
     */
    public static Map<String, Object> toMap(JSONObject object) {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator it = object.keys();
        while (it.hasNext()) {
            String key = it.next().toString();
            Object value = null;
            try {
                value = object.get(key);
            } catch (JSONException e) {
            }

            if (value == null)
                continue;

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }

        return map;
    }

    /**
     * JSONArray转为List
     *
     * @param array json数组
     * @return 转化后的List
     */
    public static List<Object> toList(JSONArray array) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = null;
            try {
                value = array.get(i);
            } catch (JSONException e) {
            }

            if (value == null)
                continue;

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


    /**
     * 把对象按照Json格式输出
     *
     * @param obj
     */
    public static void printAsJson(Object obj) {
        Gson gson = new Gson();
        ZogUtils.i(gson.toJson(obj));
    }


    public static boolean isJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.getJSONObject(jsonString);
        } catch (Exception e) {

            return false;
        }
        return true;
    }
}

