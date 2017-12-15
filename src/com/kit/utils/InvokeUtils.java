package com.kit.utils;

import java.lang.reflect.Method;

/**
 * Created by joeyzhao on 2017/12/15.
 */

public class InvokeUtils {

    /**
     * 通过反射，用getter获得属性值
     * 实际上如果类本身没getter方法 依然获取不到值
     *
     * @param entity    需要获取属性值的类
     * @param fieldName 该类的属性名称
     * @return
     */
    public static Object getFieldValue(Object entity, String fieldName) {
        Object value = new Object();
        Method method = null;
        try {
            String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            method = entity.getClass().getMethod("get" + methodName);
            value = method.invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
