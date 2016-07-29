package com.kit.utils;

import com.kit.utils.log.ZogUtils;

public class WebUtils {

    public static String getStartWith(String url) {
        if(StringUtils.isEmptyOrNullOrNullStr(url))
            return null;

        int end = url.indexOf("://");

        if (end == -1)
            end = url.length();

        return url.substring(0, end);
    }



}
