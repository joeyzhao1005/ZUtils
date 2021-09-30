package com.kit.app.application;

import android.content.Context;

import com.kit.app.theme.ThemeProvider;
import com.kit.config.AppConfig;

public interface IApp {
    String getAppName();
    Context getAppContext();
    String getApplicationId();
    String getVersionName();
    long getVersionCode();

    String getFlavor();
    boolean isDebug();
    void restartApp();

    AppConfig.IAppConfig getAppConfig();

    ThemeProvider getThemeProvider();

}
