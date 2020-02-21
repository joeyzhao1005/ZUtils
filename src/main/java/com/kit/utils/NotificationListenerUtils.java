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




    public static NotificationListenerUtils getInstance() {
        if (notificationListenerUtils == null) {
            notificationListenerUtils = new NotificationListenerUtils();
        }

        return notificationListenerUtils;
    }

    private static NotificationListenerUtils notificationListenerUtils;

}
