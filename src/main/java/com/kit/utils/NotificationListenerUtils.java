package com.kit.utils;

import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import com.kit.app.application.AppMaster;
import com.kit.utils.log.Zog;

import java.util.Set;

/**
 * Created by Zhao on 2017/4/21.
 */

public class NotificationListenerUtils {


    /**
     * 进入设置让用户开启通知监听服务相关权限
     *
     * @param context
     */
    public void enableNotificationListenerListener(Context context) {

        try {
//            if (!isNotificationListenerListenerEnabled(context)) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            context.startActivity(intent);
//            } else {
//                Zog.i("已开启通知监听服务权限");
//            }
        } catch (Exception e) {
            Zog.showException(e);

        }
    }


    /**
     * 当前应用是否启用了通知监听服务
     *
     * @param context
     * @return
     */
    public boolean isNotificationListenerListenerEnabled(Context context) {
        if (context == null) {
            context = AppMaster.getInstance().getAppContext();
            Zog.e("conext is null, it maybe not you wish!");
        }
        String pkgName = context.getPackageName();
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);

        return packageNames.contains(pkgName);
    }


    public static NotificationListenerUtils getInstance() {
        if (notificationListenerUtils == null) {
            notificationListenerUtils = new NotificationListenerUtils();
        }

        return notificationListenerUtils;
    }

    private static NotificationListenerUtils notificationListenerUtils;

}
