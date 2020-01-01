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
     * @return void 返回类型
     * @Title e
     * @Description 打印Log
     */
    public static void i(String tag, ZString.Creator creator) {
        if (AppConfig.getAppConfig() == null || !AppConfig.getAppConfig().isShowLog()) {
            return;
        }
        Log.i(tag, getTitle() + creator.build());
    }


    /**
     * @return void 返回类型
     * @Title e
     * @Description 打印Log
     */
    public static void i(ZString.Creator creator) {
        i(LOGUTILS_TAG, creator);
    }


    /**
     * @param msg String 消息
     * @return void 返回类型
     * @Title i
     * @Description 打印Log
     */
    public static void i(String msg) {
        i(() -> msg);
    }

    /**
     * @param msg String 消息
     * @return void 返回类型
     * @Title i
     * @Description 打印Log
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
     * @return void 返回类型
     * @Title e
     * @Description 打印Log
     */
    public static void e(String tag, ZString.Creator creator) {
        AppConfig.IAppConfig appConfig = AppConfig.getAppConfig();
        if (appConfig == null || !appConfig.isShowLog()) {
            return;
        }
        Log.e(tag, getTitle() + creator.build());
    }


    /**
     * @return void 返回类型
     * @Title e
     * @Description 打印Log
     */
    public static void e(ZString.Creator creator) {
        e(LOGUTILS_TAG, creator);
    }

    /**
     * @param msg String 消息
     * @return void 返回类型
     * @Title i
     * @Description 打印Log
     */
    public static void e(String msg) {
        e(() -> msg);
    }


    /**
     * @param msg String 消息
     * @return void 返回类型
     * @Title i
     * @Description 打印Log
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
     * @return void 返回类型
     * @Title e
     * @Description 打印Log
     */
    public static void d(String tag, ZString.Creator creator) {
        if (!AppConfig.getAppConfig().isShowLog()) {
            return;
        }
        Log.d(tag, getTitle() + creator.build());
    }


    /**
     * @return void 返回类型
     * @Title e
     * @Description 打印Log
     */
    public static void d(ZString.Creator creator) {
        d(LOGUTILS_TAG, creator);
    }


    /**
     * @param msg String 消息
     * @return void 返回类型
     * @Title i
     * @Description 打印Log
     */
    public static void d(String msg) {
        d(() -> msg);
    }


    /**
     * @param msg String 消息
     * @return void 返回类型
     * @Title i
     * @Description 打印Log
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
     * @param object
     * @return void 返回类型
     * @Title e
     * @Description 打印Object
     */
    public static void printObj(Object object) {
        String msg = GsonUtils.toJson(object);

        i(msg);
    }

    /**
     * @param object
     * @return void 返回类型
     * @Title e
     * @Description 打印Object
     */
    public static void printObj(Object object, String tag) {

        String msg = GsonUtils.toJson(object);
        i(tag, msg);
    }


    /**
     * @param
     * @return void 返回类型
     * @Title showException
     * @Description 显示Exception
     */
    public static void showException(Throwable e) {
        if (AppConfig.getAppConfig().isShowException()) {
            Zog.e(e == null ? "ERROR!!!" : Log.getStackTraceString(e));
        }
    }

    /**
     * @param
     * @return void 返回类型
     * @Title showException
     * @Description 显示Exception
     */
    public static void showException(Exception e) {
        if (AppConfig.getAppConfig().isShowException()) {
            Zog.e(e == null ? "ERROR!!!" : Log.getStackTraceString(e));
        }
    }


    public static final String STYLE_MINIMAL = "MINIMAL";//极简
    public static final String STYLE_SIMPLE = "SIMPLE";//简单
    public static final String STYLE_VERBOSE = "VERBOSE";//简单


    public static int COUNT = 0;

    private static String STYLE = STYLE_MINIMAL;


    public static String LOGUTILS_TAG = "Zhao_APP";
    public static String LOGUTILS_IDENTIFY = "@";


    public static Zog setStyle(String style) {
        Zog.STYLE = style;
        return new Zog();
    }
}
