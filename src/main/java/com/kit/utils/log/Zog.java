package com.kit.utils.log;

import android.util.Log;

import com.kit.config.AppConfig;
import com.kit.utils.GsonUtils;
import com.kit.utils.ZString;

/**
 * @author Zhao
 * 日志类
 */
public class Zog {


    /**
     * 打印日志
     */
    public static void i(String tag, ZString.Creator creator) {
        if (AppConfig.getAppConfig() == null || !AppConfig.getAppConfig().isShowLog()) {
            return;
        }
        Log.i(tag, getTitle() + creator.build());
    }


    /**
     * 打印日志
     */
    public static void i(ZString.Creator creator) {
        i(LOGUTILS_TAG, creator);
    }


    /**
     * 打印日志
     */
    public static void i(String msg) {
        i(() -> msg);
    }


    /**
     * 打印日志
     */
    public static void i(Object... msg) {

        i(() -> {
            ZString zString = ZString.get();
            for (Object obj : msg) {
                zString.p(obj);
            }
            return zString.string();
        });
    }


    /**
     * 打印日志
     */
    public static void e(String tag, ZString.Creator creator) {
        AppConfig.IAppConfig appConfig = AppConfig.getAppConfig();
        if (appConfig == null || !appConfig.isShowLog()) {
            return;
        }
        Log.e(tag, getTitle() + creator.build());
    }


    /**
     * 打印日志
     */
    public static void e(ZString.Creator creator) {
        e(LOGUTILS_TAG, creator);
    }


    /**
     * 打印日志
     */
    public static void e(String msg) {
        e(() -> msg);
    }


    /**
     * 打印日志
     */
    public static void e(Object... msg) {

        e(() -> {
            ZString zString = ZString.get();
            for (Object obj : msg) {
                zString.p(obj);
            }
            return zString.string();
        });
    }


    /**
     * 打印日志
     */
    public static void d(String tag, ZString.Creator creator) {
        if (!AppConfig.getAppConfig().isShowLog()) {
            return;
        }
        Log.d(tag, getTitle() + creator.build());
    }


    /**
     * 打印日志
     */
    public static void d(ZString.Creator creator) {
        d(LOGUTILS_TAG, creator);
    }


    /**
     * 打印日志
     */
    public static void d(String msg) {
        d(() -> msg);
    }


    /**
     * 打印日志
     */
    public static void d(Object... msg) {

        d(() -> {
            ZString zString = ZString.get();
            for (Object obj : msg) {
                zString.p(obj);
            }
            return zString.string();
        });
    }


    private static StringBuilder getTitle() {
        DebugInfo debugInfo = new DebugInfo();

        StringBuilder title = new StringBuilder();
        switch (STYLE) {
            case STYLE_MINIMAL:
                title.append("【")
                        .append(LOGUTILS_IDENTIFY)
                        .append(debugInfo.simpleClassName())
                        .append("】 ");
                break;

            case STYLE_SIMPLE:
                title.append("【")
                        .append(LOGUTILS_IDENTIFY)
                        .append(debugInfo.className())
                        .append("】 ");
                break;
            case STYLE_VERBOSE:
                title.append("【")
                        .append(LOGUTILS_IDENTIFY)
                        .append(debugInfo.className())
                        .append(debugInfo)
                        .append("】 ");
                break;

            default:
                break;
        }
        return title;
    }


    /**
     * 打印日志
     */
    public static void printObj(Object object) {
        String msg = GsonUtils.toJson(object);

        i(msg);
    }


    /**
     * 打印日志
     */
    public static void printObj(Object object, String tag) {

        String msg = GsonUtils.toJson(object);
        i(tag, msg);
    }


    /**
     * 打印日志
     */
    public static void showException(Throwable e) {
        if (AppConfig.getAppConfig().isShowException()) {
            Zog.e(e == null ? "ERROR!!!" : Log.getStackTraceString(e));
        }
    }


    /**
     * 打印日志
     */
    public static void showException(Exception e) {
        if (AppConfig.getAppConfig().isShowException()) {
            Zog.e(e == null ? "ERROR!!!" : Log.getStackTraceString(e));
        }
    }

    /**
     * 极简
     */
    public static final String STYLE_MINIMAL = "MINIMAL";

    /**
     * 简单
     */
    public static final String STYLE_SIMPLE = "SIMPLE";

    /**
     * 啰嗦
     */
    public static final String STYLE_VERBOSE = "VERBOSE";


    public static int COUNT = 0;

    private static String STYLE = STYLE_MINIMAL;


    public static String LOGUTILS_TAG = "Zhao_APP";
    public static String LOGUTILS_IDENTIFY = "@";


    private Zog() {
    }

    public static void setStyle(String style) {
        Zog.STYLE = style;
    }
}
