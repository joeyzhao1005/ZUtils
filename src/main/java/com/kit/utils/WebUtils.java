package com.kit.utils;

public class WebUtils {

    public static String getStartWith(String url) {
        if (StringUtils.isEmptyOrNullStr(url)) {
            return null;
        }

        int end = url.indexOf("://");

        if (end == -1) {
            end = url.length();
        }

        return url.substring(0, end);
    }


}
