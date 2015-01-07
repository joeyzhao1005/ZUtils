package com.kit.config;

public class AppConfig {

    public static final String DOWNLOAD_URL = "download/pantomobile/";
    public static final String UPDATE_PATH = "http://180.168.106.102:8000/App_Public/update.json";
    public static final String LOCKNOW = "com.kit.LockNow";
    public static final String ALARM_BROADCAST = "com.kit.alarm.action";


    public static String DATA_DIR = "mnt/sdcard/" + ".data/";

    /**
     * 超时时间
     */
    public static int TIME_OUT = 500;

    /**
     * 是否打印log
     */
    public static boolean LOG = true;

    /**
     * 是否展示Exception
     */
    public static boolean SHOW_EXCEPTION = true;

    /**
     * 网络请求数据异常时候是否返回默认的值
     */
    public static boolean EXCEPTION_RETURN_RES = true;

    /**
     * 网络请求数据异常时候是否返回默认的错误值
     */
    public static boolean EXCEPTION_RETURN_FAIL_RES = false;

}
