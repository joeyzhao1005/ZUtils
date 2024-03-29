package com.kit.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kit.utils.log.Zog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Zhao on 14/11/20.
 */
public class GsonUtils {


    /**
     * 强制转换对象
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T castObj(Object obj, Class<T> clazz) {
        return GsonUtils.getObj(GsonUtils.toJson(obj), clazz);
    }

    /**
     * 强制转换列表
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> List<T> castList(Object obj, Type typeOfT) {
        return GsonUtils.getList(GsonUtils.toJson(obj), typeOfT);
    }

    /**
     * 强制转换列表
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Map<?, T> castMap(Object obj, Type typeOfT) {
        return GsonUtils.getMap(GsonUtils.toJson(obj), typeOfT);
    }


    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }



    /**
     * 解析成list
     *
     * @param jsonStr
     * @param type    要构建出type
     * @return
     */
    public static <T> Map<String, T> getMap(String jsonStr, Type type) {
        if (StringUtils.isEmptyOrNullStr(jsonStr)) {
            return null;
        }

        Gson gson = new Gson();
        Map<String, T> list = gson.fromJson(jsonStr, type);
        return list;
    }

    /**
     * 解析成list
     *
     * @param jsonStr
     * @return
     */
    public static <T> Set<T> getSet(String jsonStr, Type typeOfT) {
        if (StringUtils.isEmptyOrNullStr(jsonStr)) {
            return null;
        }

        Gson gson = new Gson();
        Set<T> list = gson.fromJson(jsonStr, typeOfT);
        return list;
    }


    /**
     * 解析成list
     *
     * @param jsonStr
     * @return
     */
    public static <T> LinkedHashSet<T> getLinkedHashSet(String jsonStr, Type typeOfT) {
        if (StringUtils.isEmptyOrNullStr(jsonStr)) {
            return null;
        }

        Gson gson = new Gson();
        LinkedHashSet<T> list = gson.fromJson(jsonStr, typeOfT);
        return list;
    }


    /**
     * 解析成list
     *
     * @param jsonStr
     * @return
     */
    public static <T> T getList(String jsonStr, Type typeOfT) {
        if (StringUtils.isEmptyOrNullStr(jsonStr)) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(jsonStr, typeOfT);
    }



    /**
     * 解析成ArrayList
     *
     * @param jsonStr
     * @return
     */
    public static <T> Set<T> getSet(String jsonStr) {

        if (StringUtils.isEmptyOrNullStr(jsonStr)) {
            return null;
        }

        Type type = new TypeToken<Set<T>>() {
        }.getType();

        return GsonUtils.getSet(jsonStr, type);
    }


    /**
     * 解析成ArrayList
     *
     * @param jsonStr
     * @return
     */
    public static <T> LinkedHashSet<T> getLinkedHashSet(String jsonStr) {

        if (StringUtils.isEmptyOrNullStr(jsonStr)) {
            return null;
        }

        Type type = new TypeToken<LinkedHashSet<T>>() {
        }.getType();

        return GsonUtils.getLinkedHashSet(jsonStr, type);
    }

    /**
     * 解析成对象
     *
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T getObj(String jsonStr, Class<T> clazz) {
        if (StringUtils.isEmptyOrNullStr(jsonStr)) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(jsonStr, clazz);
    }


    /**
     * 根据 key 获取 json 数据
     *
     * @return
     */
    public static String getJsonByKey(Object object, String key) {
        if (object == null) {
            return null;
        }

        String jsonStr = GsonUtils.toJson(object);


        if (StringUtils.isEmptyOrNullStr(jsonStr)) {
            return null;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (JSONException e) {
            Zog.showException(e);
        }

        String value = null;
        if (jsonObject != null && jsonObject.has(key)) {
            try {
                value = jsonObject.get(key).toString();
            } catch (JSONException e) {
            }
        }

        return value;
    }


}
