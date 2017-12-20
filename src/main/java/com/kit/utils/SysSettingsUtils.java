package com.kit.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Zhao on 16/9/1.
 */

public class SysSettingsUtils {

    /**
     * 跳转到系统设置-网络设置界面
     */
    public static void gotoNetworkSettings() {
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings"
                    , "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        Context context = ResWrapper.getInstance().getApplicationContext();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
