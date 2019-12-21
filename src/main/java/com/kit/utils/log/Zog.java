package com.kit.utils.log;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.kit.config.AppConfig;
import com.kit.utils.AppUtils;
import com.kit.utils.GsonUtils;
import com.kit.utils.ZString;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zhao
 * 日志类
 */
public class Zog {


    /**
     * 保存错误信息到文件中
     *
     * @param zogStr
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    public static String saveLog2File(Context context, String tag, String zogStr) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        StringBuffer sb = new StringBuffer();

        sb.append(zogStr);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());


            String fileName = AppUtils.getAppName(context) + "-" + tag + time + "-" + timestamp + ".zog";
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String path = AppConfig.getAppConfig().getCacheDataDir() + "zogs/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            showException(e);
        }
        return null;
    }

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
