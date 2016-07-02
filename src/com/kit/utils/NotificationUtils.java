package com.kit.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.kit.utils.bitmap.BitmapUtils;

public class NotificationUtils {

    /**
     * 取消通知栏通知
     *
     * @param notificationManager
     * @param id
     */
    public static void cancel(NotificationManager notificationManager, int id) {
        try {
            notificationManager.cancel(id);
        } catch (Exception e) {
            ZogUtils.showException(e);
        }

    }

    @TargetApi(11)
    public static Notification mkNotity(Context context, NotificationManager nm
            , Intent intent, PendingIntent pendingIntent
            , String title
            , String ticker
            , String content
            , int iconLargeDrawableRes, int iconSmallDrawableRes
            , int defaultsAudio, int flags
            , int notifyId, int requestCode) {

        Notification n = null;
        Intent i = intent;
        if (i == null) {
            i = new Intent();
        }

        PendingIntent contentIntent = pendingIntent;
        //PendingIntent
        if (contentIntent == null) {
            contentIntent = PendingIntent.getActivity(
                    context,
                    requestCode,
                    i,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        Notification.Builder builder = new Notification.Builder(context);

        if (iconLargeDrawableRes > 0)
            builder.setLargeIcon(BitmapUtils.drawable2Bitmap(
                    context.getResources().getDrawable(iconLargeDrawableRes)));

        if (iconSmallDrawableRes > 0)
            builder.setSmallIcon(iconSmallDrawableRes);

        builder.setAutoCancel(false);
        builder.setTicker(ticker);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setContentIntent(contentIntent);


        n.flags = flags;
        n.defaults = defaultsAudio;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setSubText(content);   //API level 16
            n = builder.build();
        } else {
            n = builder.getNotification();

        }
        nm.notify(notifyId, n);

        return n;

    }

    /**
     * @param notificationFlag 设置常驻状态栏 Notification.FLAG_ONGOING_EVENT
     * @return int 返回类型
     * @Title creatResidentNotification
     * @Description 常驻状态栏的通知, 通过view设置点击，可以设置点击时间
     */
    @SuppressWarnings("deprecation")
    public static Notification creatResidentNotification(Context context,
                                                         int notificationId, int statusBarIcon, Intent intent,
                                                         RemoteViews view, String notice, int notificationFlag) {

        Notification notification = new Notification(statusBarIcon, notice,
                System.currentTimeMillis());

        notification.contentView = view;
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        notification.flags = notificationFlag;

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        // notification.setLatestEventInfo(context, title, content,
        // contentIntent);

        return notification;
    }

    /**
     * @return int 返回类型
     * @Title creatResidentNotification
     * @Description 常驻状态栏的通知, 通过view设置点击，可以设置点击时间
     */
    @SuppressWarnings("deprecation")
    public static void showResidentNotification4Service(NotificationManager nm, Notification
            notification, int notificationId, int statusBarIcon,
                                                        PendingIntent contentIntent, RemoteViews view, String notice) {
        notification.contentView = view;

//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_NEW_TASK);

        notification.flags = Notification.FLAG_ONGOING_EVENT;

        notification.contentIntent = contentIntent;

        nm.notify(notificationId, notification);

    }

    /**
     * @param notificationFlag 设置常驻状态栏 Notification.FLAG_ONGOING_EVENT
     * @return int 返回类型
     * @Title creatNotification
     * @Description 常驻状态栏的通知, 通过view设置点击，可以设置点击时间
     */
    @SuppressWarnings("deprecation")
    public static int creatNotification(Context context,
                                        NotificationManager nm, Intent intent, String iconName,
                                        int layoutId, String notice, String title, String content,
                                        int notificationFlag) {

        int m = 10000;
        int n = 100000;
        int notificationId = (int) (Math.random() * (m - n)) + n;

        int id = context.getResources().getIdentifier(iconName, "drawable",
                context.getApplicationContext().getPackageName());

        Notification notification = new Notification(id, notice,
                System.currentTimeMillis());
        RemoteViews contentView = new RemoteViews(context.getPackageName(),
                layoutId);
        notification.contentView = contentView;
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        notification.flags = notificationFlag; // 设置常驻 Flag
        // Notification.FLAG_ONGOING_EVENT

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        // notification.setLatestEventInfo(context, title, content,
        // contentIntent);

        nm.notify(notificationId, notification);

        return notificationId;

    }

    public static void startForegroundNotification(Context context,
                                                   int notificationId, RemoteViews view, Intent intent,
                                                   PendingIntent pendingIntent, int iconR, String str) {

        Notification notification = NotificationUtils
                .creatResidentNotification(context, notificationId, iconR,
                        intent, view, "", Notification.FLAG_ONGOING_EVENT);

        ((Service) context).startForeground(notificationId, notification);

    }

}
