package com.kit.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;

import com.kit.app.application.AppMaster;
import com.kit.utils.AppUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class CrashUtils {

    private static boolean mInitialized;
    private static String defaultDir;
    private static String dir;
    private static String versionName;
    private static int versionCode;

    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH-mm-ss", Locale.getDefault());

    private static final String CRASH_HEAD;

    private static final UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER;
    private static final UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER;

    static {
        try {
            PackageInfo pi = AppMaster.getInstance().getAppContext().getPackageManager().getPackageInfo(AppMaster.getInstance().getAppContext().getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        CRASH_HEAD = "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +    // 设备厂商
                "\nDevice Model       : " + Build.MODEL +           // 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE + // 系统版本
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT + // SDK版本
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Crash Log Head ****************\n\n";

        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

        UNCAUGHT_EXCEPTION_HANDLER = new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                if (e == null) {
                    if (mFinishAppListener != null) {
                        mFinishAppListener.onFinishApp(t, e);
                    }
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                    return;
                }
                e.printStackTrace();
                Date now = new Date(System.currentTimeMillis());
                String fileName = FORMAT.format(now) + ".txt";
                final String fullPath = (dir == null ? defaultDir : dir) + fileName;
                if (!createOrExistsFile(fullPath)) {
                    return;
                }
                AppUtils.newThread(()->{
                    PrintWriter pw = null;
                    try {
                        pw = new PrintWriter(new FileWriter(fullPath, false));
                        pw.write(CRASH_HEAD);
                        e.printStackTrace(pw);
                        Throwable cause = e.getCause();
                        while (cause != null) {
                            cause.printStackTrace(pw);
                            cause = cause.getCause();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        if (pw != null) {
                            pw.close();
                        }
                    }
                });

                if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e);
                }
            }
        };
    }

    private static CrashAppListener mFinishAppListener = null;

    public interface CrashAppListener {
        void onFinishApp(final Thread t, final Throwable e);
    }

    public static void setCrashListener(CrashAppListener mFinishAppListener) {
        CrashUtils.mFinishAppListener = mFinishAppListener;
    }

    private CrashUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @return {@code true}: 初始化成功<br>{@code false}: 初始化失败
     */
    public static boolean init() {
        return init("", null);
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @param crashDir 崩溃文件存储目录
     * @return {@code true}: 初始化成功<br>{@code false}: 初始化失败
     */
    public static boolean init(@NonNull final File crashDir) {
        return init(crashDir.getAbsolutePath() + FILE_SEP, null);
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @param crashDir 崩溃文件存储目录
     * @return {@code true}: 初始化成功<br>{@code false}: 初始化失败
     */
    public static boolean init(final String crashDir, CrashAppListener listener) {
        mFinishAppListener = listener;
        if (isSpace(crashDir)) {
            dir = null;
        } else {
            dir = crashDir.endsWith(FILE_SEP) ? dir : dir + FILE_SEP;
        }
        if (mInitialized) {
            return true;
        }
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && AppMaster.getInstance().getAppContext().getExternalCacheDir() != null) {
            defaultDir = AppMaster.getInstance().getAppContext().getExternalCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        } else {
            defaultDir = AppMaster.getInstance().getAppContext().getCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        }
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
        return mInitialized = true;
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

    private static boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
