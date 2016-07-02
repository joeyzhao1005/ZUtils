package com.kit.config;

import android.content.Context;

import com.kit.basic.R;

public class AppConfig {

    public static final String LOCKNOW = "com.kit.LockNow";


    public static String PACKAGE_NAME = "clan";
    public static String CACHE_DIR = "mnt/sdcard/." + AppConfig.PACKAGE_NAME;
    public static String CACHE_DATA_DIR = CACHE_DIR + "/data/";
    public static String CACHE_IMAGE_DIR = CACHE_DIR + "/image/";

    /**
     * 是否打印log
     */
    public static boolean LOG = true;

    /**
     * 是否Debug模式
     */
    public static boolean DEBUG = false;

    /**
     * 是否展示Exception
     */
    public static boolean SHOW_EXCEPTION = true;

    public static String getImageDir() {
        return CACHE_IMAGE_DIR;
    }


    public static String initDataDir(Context context) {
        return CACHE_DIR;
    }

}
