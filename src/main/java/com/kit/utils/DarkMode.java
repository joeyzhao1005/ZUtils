package com.kit.utils;

import android.content.Context;
import android.content.res.Configuration;

import com.kit.app.application.AppMaster;

public class DarkMode {

    /**
     * 检查当前系统是否已开启暗黑模式
     */
    public static boolean isDarkMode() {
        int mode = AppMaster.getInstance().getAppContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

}
