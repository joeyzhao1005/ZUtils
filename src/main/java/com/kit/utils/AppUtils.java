package com.kit.utils;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.kit.app.ActivityManager;
import com.kit.app.UIHandler;
import com.kit.app.core.task.DoSomeThing;
import com.kit.app.core.task.OnNext;
import com.kit.utils.log.Zog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;

public class AppUtils {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static void fastClick(DoSomeThing doSomeThing) {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;

        if (flag) {
            if (doSomeThing != null) {
                doSomeThing.execute();
            }
        }
    }

    public static boolean isSysApp(String packageName) {
        PackageManager packageManager = null;

        try {
            packageManager = ResWrapper.getApplicationContext().getPackageManager();
        } catch (Exception e) {
        }

        if (packageManager == null) {
            try {
                packageManager = ActivityManager.getInstance().getCurrActivity().getPackageManager();
            } catch (Exception e) {
            }
        }

        if (packageManager == null) {
            return false;
        }

        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (Exception e) {
        }

        if (applicationInfo == null) {
            return false;
        }

        return isSysApp(applicationInfo);

    }


    public static boolean isSysApp(ApplicationInfo applicationInfo) {
        boolean flag = false;
//        if ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
//            // Updated system app
//            flag = true;
//        } else if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//            // Non-system app
//            flag = true;
//        }

        //判断是否系统应用
        if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
            /*|| (applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0*/) {
            //非系统应用
            flag = false;
        } else {
            //系统应用　　　　　　　　
            flag = true;
        }


        return flag;
    }

    /**
     * 从manifest里获取metadata
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getReceiverInfoMetaDataFromManifest(String key, ComponentName componentName) {
        try {
            Context context = ResWrapper.getApplicationContext();
            ActivityInfo info = context.getPackageManager().getReceiverInfo(componentName
                    , PackageManager.GET_META_DATA);
            Bundle metaData = info.metaData;
            if (metaData == null) {
                return null;
            } else {
                return (T) metaData.get(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 从manifest里获取metadata
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getServiceInfoMetaDataFromManifest(String key, ComponentName componentName) {
        try {
            Context context = ResWrapper.getApplicationContext();
            ServiceInfo info = context.getPackageManager().getServiceInfo(componentName
                    , PackageManager.GET_META_DATA);
            Bundle metaData = info.metaData;
            if (metaData == null) {
                return null;
            } else {
                return (T) metaData.get(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从manifest里获取metadata
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getActivityInfoMetaDataFromManifest(Context context, String key, ComponentName componentName) {
        try {
            if (context == null) {
                return null;
            }

            if (context.getPackageManager() == null || componentName == null) {
                return null;
            }

            ActivityInfo info = context.getPackageManager().getActivityInfo(componentName
                    , PackageManager.GET_META_DATA);

            if (info == null) {
                return null;
            }

            Bundle metaData = info.metaData;
            if (metaData == null) {
                return null;
            } else {
                return (T) metaData.get(key);
            }
        } catch (Exception e) {
            //可能获取不到 报异常
//            Zog.showException(e);
        }
        return null;
    }


    /**
     * 从manifest里获取metadata
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAppMetaDataFromManifest(Context context, String key, String packageName) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName
                    , PackageManager.GET_META_DATA);
            Bundle metaData = applicationInfo.metaData;
            if (metaData == null) {
                return null;
            } else {
                return (T) metaData.get(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 从manifest里获取metadata
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAppMetaDataFromManifest(Context context, String key) {
        try {
            if (context == null) {
                return null;
            }
            return (T) context.getPackageManager().getApplicationInfo(context.getPackageName()
                    , PackageManager.GET_META_DATA).metaData.get(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isPermission(String permissionTag) {
        Context context = ResWrapper.getApplicationContext();
        PackageManager pm = context.getPackageManager();
        return (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(permissionTag, context.getPackageName()));

    }

    @TargetApi(3)
    public static void restartApp(Context context) {
        ActivityManager.getInstance().popAllActivity();

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    public static String getAppVersion(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;

        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static int getAppCode(Context context) {
        int versionCode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;

            if (versionCode <= 0) {
                return 0;
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    public static String getAppName(Context context) {

        String appName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);

            appName = pm.getApplicationLabel(pi.applicationInfo).toString();

            if (appName == null || appName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return appName;
    }

    /**
     * @return null may be returned if the specified process not found
     */
    @TargetApi(3)
    public static String getProcessName(Context cxt, int pid) {
        android.app.ActivityManager am = (android.app.ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<android.app.ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (android.app.ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }

        }
        return null;
    }

    public static String getAppPackage(Context context) {
        String packageName = context.getPackageName();
        return packageName;
    }

    public static String getPackageByApkFilePath(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        String packageName = "";
        PackageInfo info = pm.getPackageArchiveInfo(path,
                PackageManager.GET_ACTIVITIES);
        ApplicationInfo appInfo = null;
        if (info != null) {
            appInfo = info.applicationInfo;
            packageName = appInfo.packageName;
//            System.out.println("packageName:" + packageName);
        }
        return packageName;
    }


    public static boolean autoChangeAlarm(Context context) {
        try {

            AudioManager audioManager = (AudioManager) context
                    .getApplicationContext().getSystemService(
                            Context.AUDIO_SERVICE);
            // audioManager.setRingerMode(RINGER_MODE_NORMAL或者RINGER_MODE_SILENT或者RINGER_MODE_VIBRATE);
            // android.media.AudioManager.RINGER_MODE_NORMAL = 2;
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            } else if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 判断手机已安装某程序的方法：
    public static boolean isAvilible(String packageName) {
        PackageInfo packageInfo;
        try {
            Context context = ResWrapper.getApplicationContext();
            final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
            packageInfo = packageManager.getPackageInfo(
                    packageName, 0);

        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }

        if (packageInfo != null) {
            return true;
        } else {
            return false;
        }

    }

//    // 判断手机已安装某程序的方法：
//    public static boolean isAvilible(String packageName) {
//        Context context = ResWrapper.getApplicationContext();
//        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
//        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
//        // 从pinfo中将包名字逐一取出，压入pName list中
//        if (pinfo != null) {
//            for (int i = 0; i < pinfo.size(); i++) {
//                String pn = pinfo.get(i).packageName;
//                pName.add(pn);
//            }
//        }
//        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
//    }

    @SuppressWarnings("unused")
    @TargetApi(3)
    public static void launchApk(String launchApkUrl) {
        Context context = ResWrapper.getApplicationContext();
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(launchApkUrl);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void makeCrash() {
        int[] ints = new int[2];
        System.out.print(ints[99] + "");
    }

    /**
     * 强制关闭应用程序
     */
    public static void closeApp() {
//        makeCrash();


        ActivityManager.getInstance().popAllActivity();

        System.exit(0);

        android.os.Process.killProcess(android.os.Process.myPid());


    }

    /**
     * 暂停主线程
     *
     * @param time
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            Zog.i("Exception:" + e);
        }
    }

    /**
     * 延时执行
     *
     * @param time
     */
    public static void delay(long time, final OnNext onNext) {

        UIHandler.get().postDelayed(onNext::next, time);

    }


    public static void newThread(Runnable runnable) {
        try {
            if (!SingleThread.get().isShutdown()) {
                SingleThread.get().submit(runnable);
            }
        } catch (RejectedExecutionException e) {
            Zog.showException(e);
        }
    }


    /**
     * 改变语言环境
     *
     * @param lanAtr
     */
    public static void setAppLanguage(Application app, String lanAtr) {
        Resources resources = app.getApplicationContext().getResources();
        if (resources == null) {
            resources = app.getResources();
        }
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (lanAtr.equals("zh-cn")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(Locale.SIMPLIFIED_CHINESE);
            } else {
                config.locale = Locale.SIMPLIFIED_CHINESE;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(Locale.getDefault());
            } else {
                config.locale = Locale.getDefault();
            }
        }
        resources.updateConfiguration(config, dm);
    }

    /**
     * 改变语言环境
     *
     * @param resources
     */
    public static Locale getAppLanguage(Resources resources) {
        if (resources == null) {
            resources = ResWrapper.getResources();
        }
        Configuration config = resources.getConfiguration();
        return config.locale;
    }


    /**
     * 查询手机内非系统应用
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getAllApps(Context context) {
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            //判断是否为非系统预装的应用程序
//            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
//                // customs applications
//                apps.add(pak);
//            }
            apps.add(pak);

        }
        return apps;
    }


    public static String getPackageNameByAppName(Context context, String appName) {
        if (StringUtils.isEmptyOrNullStr(appName)) {
            return null;
        }

        List<PackageInfo> apps = getAllApps(context);
        String packageName = null;
        for (PackageInfo packageInfo : apps) {
            PackageManager pManager = context.getPackageManager();

            String thisAppName = packageInfo.applicationInfo.loadLabel(pManager).toString();
            if (appName.equals(thisAppName)) {
                packageName = packageInfo.packageName;
                break;
            }
        }

        return packageName;
    }

    /**
     * 模糊查找
     *
     * @param context
     * @param appName
     * @return
     */
    public static List<PackageInfo> getPackageNamesByAppName(Context context, String appName) {
        if (StringUtils.isEmptyOrNullStr(appName)) {
            return null;
        }

        appName = StringUtils.trimPunct(appName).toLowerCase();

        List<PackageInfo> apps = getAllApps(context);
        List<PackageInfo> packageNames = new ArrayList<PackageInfo>();
        for (PackageInfo packageInfo : apps) {
            PackageManager pManager = context.getPackageManager();
            String thisAppName = packageInfo.applicationInfo.loadLabel(pManager).toString();

            if (thisAppName.contains(appName) || thisAppName.equalsIgnoreCase(appName)) {
                packageNames.add(packageInfo);
            }
        }

        return packageNames;
    }


    /**
     * 模糊查找
     *
     * @param context
     * @param appName
     * @return
     */
    public static List<PackageInfo> getPackageNamesByAppNames(Context context, String[] appName) {
        List<PackageInfo> map = new ArrayList<>();
        List<PackageInfo> packageNames = new ArrayList<PackageInfo>();
        for (String name : appName) {
            List<PackageInfo> inner = getPackageNamesByAppName(context, name);
            if (!ListUtils.isNullOrEmpty(inner)) {
                packageNames.addAll(inner);
            }
        }
        return packageNames;
    }

    /**
     * 卸载应用
     *
     * @param packageName
     */
    public static void uninstallAPK(Context context, String packageName) {
        Intent intent;
//        if (ApiLevel.ATLEAST_LOLLIPOP) {
//            intent = new Intent();
//            PendingIntent sender = PendingIntent.getActivity(context, 0, intent, 0);
//            PackageInstaller mPackageInstaller = context.getPackageManager().getPackageInstaller();
//            mPackageInstaller.uninstall(packageName, sender.getIntentSender());
//        } else {
            Uri uri = Uri.parse("package:" + packageName);
            intent = new Intent(Intent.ACTION_DELETE, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
//        }


    }

    /**
     * 卸载应用
     *
     * @param packageName
     */
    public static void seeAppInMarket(Context context, String packageName) {
        try {
            final Intent intentPlayStore = new Intent(Intent.ACTION_VIEW);
            intentPlayStore.setData(Uri.parse("market://details?id=" + packageName));
            intentPlayStore.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(intentPlayStore);
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    /**
     * 卸载应用
     */
    public static void searchAppInMarketByKeyword(Context context, String keyword) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://search?q=" + keyword));
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(viewIntent);
    }


}
