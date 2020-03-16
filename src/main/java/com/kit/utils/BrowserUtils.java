package com.kit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.kit.app.application.AppMaster;

import java.util.List;

/**
 * Created by Zhao on 15/10/22.
 */
public class BrowserUtils {

    public static void gotoBrowser(Activity context, String url) {
        Uri uri = Uri.parse(url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        //下面这句会让用户自行选择浏览器打开
        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        context.startActivity(browserIntent);
    }

    public static void gotoBrowser( String url) {
        Context context = AppMaster.getInstance().getAppContext();
        Uri uri = Uri.parse(url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        //下面这句会让用户自行选择浏览器打开
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        context.startActivity(browserIntent);
    }


    private static void gotoUrl(Context context, String packageName, String url,
                                PackageManager packageMgr) {
        try {
            Intent intent;
            intent = packageMgr.getLaunchIntentForPackage(packageName);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            // 在1.5及以前版本会要求catch(android.content.pm.PackageManager.NameNotFoundException)异常，该异常在1.5以后版本已取消。
            e.printStackTrace();
        }
    }

    private static void doDefault(Context context, String visitUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(visitUrl));
//        加上下面这一句会让选择浏览器打开
//        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        context.startActivity(intent);
    }

    /**
     * 直接启动UC，用于验证测试。
     */
    public static void showUCBrowser(Context context, String visitUrl) {
        Intent intent = new Intent();
        intent.setClassName("com.uc.browser", "com.uc.browser.ActivityUpdate");
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse(visitUrl));
        context.startActivity(intent);
    }

    /**
     * 直接启动QQ，用于验证测试。
     */
    public static void showQQBrowser(Context context, String visitUrl) {
        Intent intent = new Intent();
        intent.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity");
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse(visitUrl));
        context.startActivity(intent);
    }

    /**
     * 直接启动Opera，用于验证测试。
     */
    public static void showOperaBrowser(Context context, String visitUrl) {
        Intent intent = new Intent();
        intent.setClassName("com.opera.mini.android",
                "com.opera.mini.android.Browser");
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse(visitUrl));
        context.startActivity(intent);
    }

    /**
     * 直接启动Dolphin Browser，用于验证测试。
     */
    public static void showDolphinBrowser(Context context, String visitUrl) {
        // 方法一：
        // Intent intent = new Intent();
        // intent.setClassName("mobi.mgeek.TunnyBrowser",
        // "mobi.mgeek.TunnyBrowser.BrowserActivity");
        // intent.setAction(Intent.ACTION_VIEW);
        // intent.addCategory(Intent.CATEGORY_DEFAULT);
        // intent.setData(Uri.parse(visitUrl));
        // startActivity(intent);
        // 方法二：
        gotoUrl(context, "mobi.mgeek.TunnyBrowser", visitUrl, ((Activity) context).getPackageManager());
    }

    /**
     * 直接启动Skyfire Browser，用于验证测试。
     */
    public static void showSkyfireBrowser(Context context, String visitUrl) {
        // 方法一：
        Intent intent = new Intent();
        intent.setClassName("com.skyfire.browser",
                "com.skyfire.browser.core.Main");
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse(visitUrl));
        context.startActivity(intent);
        // 方法二：
        // gotoUrl("com.skyfire.browser", visitUrl, getPackageManager());
    }

    /**
     * 直接启动Steel Browser，用于验证测试。
     */
    public static void showSteelBrowser(Context context, String visitUrl) {
        // 方法一：
        // Intent intent = new Intent();
        // intent.setClassName("com.kolbysoft.steel",
        // "com.kolbysoft.steel.Steel");
        // intent.setAction(Intent.ACTION_VIEW);
        // intent.addCategory(Intent.CATEGORY_DEFAULT);
        // intent.setData(Uri.parse(visitUrl));
        // startActivity(intent);
        // 方法二：
        gotoUrl(context, "com.kolbysoft.steel", visitUrl, context.getPackageManager());
    }
}
