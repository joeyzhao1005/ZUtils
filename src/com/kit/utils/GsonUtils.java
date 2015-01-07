package com.kit.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Zhao on 14/11/20.
 */
public class GsonUtils {


    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    /**
     * 解析成list
     *
     * @param jsonStr
     * @param type
     * @return
     */
    public static List getList(String jsonStr, Type type) {
        Gson gson = new Gson();
        List<?> list = gson.fromJson(jsonStr, type);
        return list;
    }

    /**
     * 解析成对象
     *
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static Object getObj(String jsonStr, Class clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, clazz);
    }
}
