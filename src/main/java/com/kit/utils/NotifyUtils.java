//package com.kit.utils;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.NotificationCompat;
//import android.widget.RemoteViews;
//
//public class NotifyUtils {
//
//    /**
//     * @param notificationFlag 设置常驻状态栏 Notification.FLAG_ONGOING_EVENT
//     * @return int 返回类型
//     * @Title creatResidentNotification
//     * @Description 常驻状态栏的通知, 通过view设置点击，可以设置点击时间
//     */
//    @SuppressWarnings("deprecation")
//    public static Notification creatResidentNotification(Context context,
//                                                         int notificationId, int statusBarIcon, Intent intent,
//                                                         RemoteViews view, String notice, int notificationFlag) {
//
//        Notification com.zhao.withu.notification = new Notification(statusBarIcon, notice,
//                System.currentTimeMillis());
//
//        com.zhao.withu.notification.contentView = view;
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        com.zhao.withu.notification.flags = notificationFlag;
//
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        com.zhao.withu.notification.contentIntent = contentIntent;
//        // com.zhao.withu.notification.setLatestEventInfo(context, title, content,
//        // contentIntent);
//
//        return com.zhao.withu.notification;
//    }
//
//
//    public static Notification creatNotification(int smallIconDrawableRes, CharSequence title, CharSequence content) {
//        Context context = AppMaster.getInstance().getAppContext();
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(smallIconDrawableRes)
//                        .setContentTitle(title)
//                        .setContentText(content);
//
//        return  builder.build();
//    }
//
//    /**
//     * @param notificationFlag 设置常驻状态栏 Notification.FLAG_ONGOING_EVENT
//     * @return int 返回类型
//     * @Title creatResidentNotification
//     * @Description 常驻状态栏的通知, 通过view设置点击，可以设置点击时间
//     */
//    @SuppressWarnings("deprecation")
//    public static void creatResidentNotification(Context context,
//                                                 NotificationManager nm, int notificationId, int statusBarIcon,
//                                                 Intent intent, RemoteViews view, String notice, int notificationFlag) {
//
//        Notification com.zhao.withu.notification = new Notification(statusBarIcon, notice,
//                System.currentTimeMillis());
//
//        com.zhao.withu.notification.contentView = view;
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        com.zhao.withu.notification.flags = notificationFlag;
//
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        com.zhao.withu.notification.contentIntent = contentIntent;
//        // com.zhao.withu.notification.setLatestEventInfo(context, title, content,
//        // contentIntent);
//
//        nm.notify(notificationId, com.zhao.withu.notification);
//
//    }
//
//    /**
//     * @param notificationFlag 设置常驻状态栏 Notification.FLAG_ONGOING_EVENT
//     * @return int 返回类型
//     * @Title creatNotification
//     * @Description 常驻状态栏的通知, 通过view设置点击，可以设置点击时间
//     */
//    @SuppressWarnings("deprecation")
//    public static int creatNotification(Context context,
//                                        NotificationManager nm, Intent intent, String iconName,
//                                        int layoutId, String notice, String title, String content,
//                                        int notificationFlag) {
//
//        int m = 10000;
//        int n = 100000;
//        int notificationId = (int) (Math.random() * (m - n)) + n;
//
//        int id = context.getResources().getIdentifier(iconName, "drawable",
//                context.getApplicationContext().getPackageName());
//
//        Notification com.zhao.withu.notification = new Notification(id, notice,
//                System.currentTimeMillis());
//        RemoteViews contentView = new RemoteViews(context.getPackageName(),
//                layoutId);
//        com.zhao.withu.notification.contentView = contentView;
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        com.zhao.withu.notification.flags = notificationFlag; // 设置常驻 Flag
//        // Notification.FLAG_ONGOING_EVENT
//
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        com.zhao.withu.notification.contentIntent = contentIntent;
//        // com.zhao.withu.notification.setLatestEventInfo(context, title, content,
//        // contentIntent);
//
//        nm.notify(notificationId, com.zhao.withu.notification);
//
//        return notificationId;
//
//    }
//
//    public static void startForegroundNotification(Context context,
//                                                   int notificationId, RemoteViews view, Intent intent,
//                                                   PendingIntent pendingIntent, int iconR, String resStringTitleR,
//                                                   String str) {
//        int id = context.getResources().getIdentifier(resStringTitleR,
//                "string", context.getApplicationContext().getPackageName());
//        Notification com.zhao.withu.notification = NotifyUtils
//                .creatResidentNotification(context, id, iconR, intent, view,
//                        "", Notification.FLAG_ONGOING_EVENT);
//
//        ((Service) context).startForeground(notificationId, com.zhao.withu.notification);
//
//    }
//
//}
