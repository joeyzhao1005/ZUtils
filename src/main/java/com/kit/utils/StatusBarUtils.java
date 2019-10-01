package com.kit.utils;

import android.content.Context;

import com.kit.utils.log.Zog;

import java.lang.reflect.Method;

public class StatusBarUtils {
    /**
     * 折叠通知栏
     *
     * @param context
     */
    public static void collapsingNotification(Context context) {
        Object service = context.getSystemService("statusbar");
        if (null == service)
            return;
        try {
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            int sdkVersion = android.os.Build.VERSION.SDK_INT;
            Method collapse = null;
            if (sdkVersion <= 16) {
                collapse = clazz.getMethod("collapse");
            } else {
                collapse = clazz.getMethod("collapsePanels");
            }
            collapse.setAccessible(true);
            collapse.invoke(service);
        } catch (Exception e) {
            Zog.e("不要忘记在 manifest 里声明权限 android.permission.EXPAND_STATUS_BAR");
            e.printStackTrace();
        }
    }


    public static void expandNotificationsPanel(Context context) {
        Object service = context.getSystemService("statusbar");
        if (null == service)
            return;

        try {
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            Method expand = null;
            if (android.os.Build.VERSION.SDK_INT <= 16) {
                expand = clazz.getMethod("expand");
            } else {
                /*
                 * Android SDK 16之后的版本展开通知栏有两个接口可以处理
                 * expandNotificationsPanel()
                 * expandSettingsPanel()
                 */
                expand = clazz.getMethod("expandNotificationsPanel");
//                expand = clazz.getMethod("expandSettingsPanel");

            }

            expand.setAccessible(true);
            expand.invoke(service);
        } catch (Exception e) {
            Zog.e("不要忘记在 manifest 里声明权限 android.permission.EXPAND_STATUS_BAR");
            e.printStackTrace();
        }

    }


    public static void expandSettingsPanel(Context context) {

        Object service = context.getSystemService("statusbar");

        if (null == service)
            return;

        try {
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            Method expand = null;

            if (android.os.Build.VERSION.SDK_INT <= 16) {
                expand = clazz.getMethod("expand");
            } else {
                /*
                 * Android SDK 16之后的版本展开通知栏有两个接口可以处理
                 * expandNotificationsPanel()
                 * expandSettingsPanel()
                 */

                //expand =clazz.getMethod("expandNotificationsPanel");
                expand = clazz.getMethod("expandSettingsPanel");
            }

            expand.setAccessible(true);
            expand.invoke(service);
        } catch (Exception e) {
            Zog.e("不要忘记在 manifest 里声明权限 android.permission.EXPAND_STATUS_BAR");
            e.printStackTrace();
        }

    }

}
