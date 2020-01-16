package com.kit.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.kit.utils.log.Zog;

/**
 * Created by hkq325800 on 2017/4/14.
 */

public class JumpPermissionManagement {
    /**
     * Build.MANUFACTURER
     */
    private static final String MANUFACTURER_HUAWEI = "Huawei";//华为
    private static final String MANUFACTURER_MEIZU = "Meizu";//魅族
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";//小米
    private static final String MANUFACTURER_SONY = "Sony";//索尼
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_LG = "LG";
    private static final String MANUFACTURER_LGE = "LGE";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    private static final String MANUFACTURER_LETV = "Letv";//乐视
    private static final String MANUFACTURER_ZTE = "ZTE";//中兴
    private static final String MANUFACTURER_YULONG = "YuLong";//酷派
    private static final String MANUFACTURER_LENOVO = "LENOVO";//联想
    private static final String MANUFACTURER_360 = "360";//联想

    /**
     * 此函数可以自己定义
     *
     * @param activity
     */
    public static void goToPermissionSetting(Activity activity, String packageName) throws Exception {
        try {
            int type = RomUtils.getAvailableRomType();
            switch (type) {
                case RomUtils.AvailableRomType.MIUI:
                    Xiaomi(activity, packageName);
                    break;

                case RomUtils.AvailableRomType.FLYME:
                    Meizu(activity, packageName);
                    break;

                case RomUtils.AvailableRomType.ANDROID_NATIVE:
                case RomUtils.AvailableRomType.NA:
                    switch (Build.MANUFACTURER) {
                        case MANUFACTURER_HUAWEI:
                            Huawei(activity, packageName);
                            break;

                        case MANUFACTURER_MEIZU:
                            Meizu(activity, packageName);
                            break;

                        case MANUFACTURER_XIAOMI:
                            Xiaomi(activity, packageName);
                            break;

                        case MANUFACTURER_SONY:
                            Sony(activity, packageName);
                            break;

                        case MANUFACTURER_OPPO:
                            OPPO(activity, packageName);
                            break;

                        case MANUFACTURER_LG:
                        case MANUFACTURER_LGE:
                            LG(activity, packageName);
                            break;

                        case MANUFACTURER_LETV:
                            Letv(activity, packageName);
                            break;

                        case MANUFACTURER_360:
                            _360(activity, packageName);
                            break;

                        default:
                            applicationInfo(activity, packageName);
                            Log.e("goToSetting", "目前暂不支持此系统");
                            break;
                    }
                    break;

                default:
                    applicationInfo(activity, packageName);
                    break;
            }
        } catch (Exception e) {
            Zog.showException(e);
            applicationInfo(activity, packageName);
        }


    }

    public static void Huawei(Activity activity, String packageName) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", packageName);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void Meizu(Activity activity, String packageName) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", packageName);
        activity.startActivity(intent);

    }


    public static void Xiaomi(Activity activity, String packageName) {

        int rom = RomUtils.getMiuiVersion();
        Intent intent = null;
        if (5 == rom) {
            Uri packageURI = Uri.parse("package:" + activity.getApplicationInfo().packageName);
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        } else if (rom > 5 && rom < 8) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);

        } else if (rom >= 8) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        } else {
            Zog.e("ERROR!!! cannot get miui version");
        }

        activity.startActivity(intent);

//        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
//        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
//        intent.setComponent(componentName);
//        intent.putExtra("extra_pkgname",packageName);
//        activity.startActivity(intent);
    }

    public static void Sony(Activity activity, String packageName) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", packageName);
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void OPPO(Activity activity, String packageName) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", packageName);
        ComponentName comp = new ComponentName("com.coloros.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void LG(Activity activity, String packageName) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", packageName);
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void Letv(Activity activity, String packageName) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", packageName);
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 只能打开到自带安全软件
     *
     * @param activity
     */
    public static void _360(Activity activity, String packageName) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", packageName);
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    public static void applicationInfo(Activity activity, String packageName) throws Exception {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", packageName, null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName);
        }
        activity.startActivity(localIntent);
    }

    /**
     * 系统设置界面
     *
     * @param activity
     */
    public static void SystemConfig(Activity activity, String packageName) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }
}