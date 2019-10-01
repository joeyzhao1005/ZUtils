package com.kit.utils;

/**
 * Created by yuechuanzhen on 2018/7/19.
 */

import android.os.Environment;
import androidx.annotation.NonNull;

import com.kit.app.application.AppMaster;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 将Log日志写入文件中
 */
public class Log2File {
    private static boolean IS_OPEN = false;

    private static final char VERBOSE = 'v';
    private static final char DEBUG = 'd';
    private static final char INFO = 'i';
    private static final char WARN = 'w';
    private static final char ERROR = 'e';

    private static String logPath = null;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static Date date = new Date();


    public static String getLogPath() {
        return logPath;
    }

    public static void init() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && AppMaster.getInstance().getAppContext().getExternalCacheDir() != null) {
            logPath = AppMaster.getInstance().getAppContext().getExternalCacheDir() + File.separator + "log" + File.separator;
        } else {
            logPath = AppMaster.getInstance().getAppContext().getCacheDir() + File.separator + "log" + File.separator;
        }
    }

    public static void init(String path, boolean isOpen) {
        logPath = path;
        IS_OPEN = isOpen;
    }

    public static void init(boolean isOpen) {
        IS_OPEN = isOpen;
    }


    private static boolean createOrExistsFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 将log信息写入文件中
     *
     * @param type
     * @param tag
     * @param msg
     */
    private static void writeToFile(char type, String tag, String msg) {
        if (null == logPath) {
            init();
        }
        final String fullPath =  logPath  + DATE_FORMAT.format(DateUtils.getCurrDate()) + ".txt";

        if (!createOrExistsFile(fullPath)) {
            return;
        }

//        String fileName = logPath + "/log_" + DATE_FORMAT.format(new Date()) + ".log";//log日志名，使用时间命名，保证不重复
        String log = DATE_FORMAT.format(date) + " " + type + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制

//        //如果父路径不存在
//        File file = new File(logPath);
//        if (!file.exists()) {
//            file.mkdirs();//创建父路径
//        }

        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(fullPath, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void v(String tag, @NonNull ZString.Creator zStringCreator) {
        if (!IS_OPEN) {
            return;
        }

        AppUtils.newThread(() -> writeToFile(VERBOSE, tag, zStringCreator.build()));
    }

    public static void d(String tag, @NonNull ZString.Creator zStringCreator) {
        if (!IS_OPEN) {
            return;
        }
        AppUtils.newThread(() -> writeToFile(DEBUG, tag, zStringCreator.build()));
    }

    public static void i(String tag, @NonNull ZString.Creator zStringCreator) {
        if (!IS_OPEN) {
            return;
        }
        AppUtils.newThread(() -> writeToFile(INFO, tag, zStringCreator.build()));
    }

    public static void w(String tag, @NonNull ZString.Creator zStringCreator) {
        if (!IS_OPEN) {
            return;
        }
        AppUtils.newThread(() -> writeToFile(WARN, tag, zStringCreator.build()));
    }

    public static void e(String tag, @NonNull ZString.Creator zStringCreator) {
        if (!IS_OPEN) {
            return;
        }
        AppUtils.newThread(() -> writeToFile(ERROR, tag, zStringCreator.build()));
    }
}
