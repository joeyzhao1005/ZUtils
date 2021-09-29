package com.kit.config;

import android.graphics.Bitmap;
import android.util.Log;

public class AppConfig {
    private static IAppConfig appConfig;

    public static final String LOCKNOW = "com.kit.LockNow";

    public static IAppConfig getAppConfig() {
        if (appConfig == null) {
            Log.e("AppConfig", "You must called setAppConfig(IAppConfig appConfig) before.");
        }
        return appConfig;
    }

    public static void setAppConfig(IAppConfig appConfig) {
        AppConfig.appConfig = appConfig;
    }

    public interface IAppConfig {
        /**
         * 获取 sp name
         * @return
         */
        String sharedPreferencesName();

        boolean isShowLog();

        boolean isShowException();


        String getFilesDir(String type);

        String getCacheDir();


        String getCacheDataDir();

        String getCacheImageDir();


        String getTheme();
    }


}
