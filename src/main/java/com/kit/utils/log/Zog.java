package com.kit.utils.log;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.kit.config.AppConfig;
import com.kit.utils.AppUtils;
import com.kit.utils.GsonUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Zog {
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
     * @param msg String 消息
     * @return void 返回类型
     * @Title i
     * @Description 打印Log
     */
    public static void i(String msg) {

        if (AppConfig.getAppConfig().isShowLog()) {
            Log.i(LOGUTILS_TAG, getTitle() + msg);
        }
    }


    /**
     * @param msg String 消息
     * @return void 返回类型
     * @Title i
     * @Description 打印Log
     */
    public static void i(String tag, String msg) {

        if (AppConfig.getAppConfig().isShowLog()) {
            Log.i(tag, getTitle() + msg);
        }
    }

    /**
     * @param msg String 消息
     * @return void 返回类型
     * @Title e
     * @Description 打印Log
     */
    public static void e(String msg) {

        if (AppConfig.getAppConfig().isShowLog()) {
            Log.e(LOGUTILS_TAG, getTitle() + msg);
        }
    }

    /**
     * @param msg String 消息
     * @return void 返回类型
     * @Title e
     * @Description 打印Log
     */
    public static void d(String msg) {

        if (AppConfig.getAppConfig().isShowLog()) {
            Log.d(LOGUTILS_TAG, getTitle() + msg);
        }
    }


    private static String getTitle() {
        DebugInfo debugInfo = new DebugInfo();

        String title = "";
        switch (STYLE) {
            case STYLE_MINIMAL:
                title = "【"
                        + LOGUTILS_IDENTIFY
                        + debugInfo.simpleClassName()
                        + "】 ";
                break;

            case STYLE_SIMPLE:
                title = "【"
                        + LOGUTILS_IDENTIFY
                        + debugInfo.className()
                        + "】 ";
                break;
            case STYLE_VERBOSE:
                title = "【"
                        + LOGUTILS_IDENTIFY
                        + debugInfo.className()
                        + debugInfo
                        + "】 ";
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
     * @param msg String 消息
     * @return void 返回类型
     * @Title i
     * @Description 打印Log
     */
    public static void i(String msg, int count) {
        if (AppConfig.getAppConfig().isShowLog()) {
            return;
        }

        if (COUNT < count) {
            i(msg);
        }
        COUNT++;
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


}
