package com.kit.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;


import com.kit.app.application.AppMaster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: dongxl
 * @Date: 2018/12/3
 * @Description:
 */
public class NotchScreenUtils {

    public static int getNotchSizeHeight(Context context) {

        if (context instanceof Activity) {
            return getNotchSizeHeight((Activity) context);
        } else {
            return DeviceUtils.getStatusBarHeight(context);
        }

    }

    public static int getNotchSizeHeight(Activity activity) {
        if (activity == null) {
            return DeviceUtils.getStatusBarHeight(AppMaster.getInstance().getAppContext());
        }

        if (!hasNotchScreen(activity)) {
            return DeviceUtils.getStatusBarHeight(activity);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            DisplayCutout displayCutout = getDisplayCutout4P(activity);
            if (displayCutout != null) {
                return displayCutout.getSafeInsetTop();
            } else {
                return DeviceUtils.getStatusBarHeight(activity);
            }

        } else {

            if (RomUtils.getAvailableRomType() == RomUtils.AvailableRomType.EMUI) {
                return getNotchSize4Huawei(activity)[1];
            } else if (RomUtils.getAvailableRomType() == RomUtils.AvailableRomType.MIUI) {
                return getNotchSize4MIUI(activity);
            } else {
                return DeviceUtils.getStatusBarHeight(activity);
            }

        }


    }


    public static int getNotchSize4MIUI(Context context) {
        return DeviceUtils.getStatusBarHeight(context);
    }


    public static int[] getNotchSize4Huawei(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        } finally {
            return ret;
        }
    }

    /**
     * 判断是否是刘海屏
     *
     * @return
     */
    public static boolean hasNotchScreen(Activity activity) {
        if (((getIntForMIUI("ro.miui.notch", activity) == 1 || hasNotchAtHuawei() || hasNotchAtOPPO() || hasNotchAtVivo()))
                || getDisplayCutout4P(activity) != null) {
            return true;
        }

        return false;
    }

    /**
     * Android P 刘海屏判断
     *
     * @param activity
     * @return
     */
    private static DisplayCutout getDisplayCutout4P(Activity activity) {
        if (activity == null) {
            return null;
        }
        View decorView = activity.getWindow().getDecorView();
        if (decorView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null) {
                return windowInsets.getDisplayCutout();
            }
        }
        return null;
    }

    /**
     * 小米刘海屏判断.
     *
     * @return 0 if it is not notch ; return 1 means notch
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    private static int getIntForMIUI(String key, Activity activity) {
        int result = 0;
        if (isXiaomi()) {
            try {
                ClassLoader classLoader = activity.getClassLoader();
                @SuppressWarnings("rawtypes")
                Class SystemProperties = classLoader.loadClass("android.os.SystemProperties");
                //参数类型
                @SuppressWarnings("rawtypes")
                Class[] paramTypes = new Class[2];
                paramTypes[0] = String.class;
                paramTypes[1] = int.class;
                Method getInt = SystemProperties.getMethod("getInt", paramTypes);
                //参数
                Object[] params = new Object[2];
                params[0] = new String(key);
                params[1] = new Integer(0);
                result = (Integer) getInt.invoke(SystemProperties, params);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 华为刘海屏判断
     *
     * @return
     */
    public static boolean hasNotchAtHuawei() {
        boolean ret = false;
        try {
            ClassLoader classLoader = AppMaster.getInstance().getAppContext().getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
//            Log.e("NotchScreenUtil", "hasNotchAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
//            Log.e("NotchScreenUtil", "hasNotchAtHuawei NoSuchMethodException");
        } catch (Exception e) {
//            Log.e("NotchScreenUtil", "hasNotchAtHuawei Exception");
        } finally {
            return ret;
        }
    }

    public static final int VIVO_NOTCH = 0x00000020;//是否有刘海
    public static final int VIVO_FILLET = 0x00000008;//是否有圆角

    /**
     * VIVO刘海屏判断
     *
     * @return
     */
    public static boolean hasNotchAtVivo() {
        boolean ret = false;
        try {
            ClassLoader classLoader = AppMaster.getInstance().getAppContext().getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
        } catch (ClassNotFoundException e) {
//            Log.e("NotchScreenUtil", "hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
//            Log.e("NotchScreenUtil", "hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
//            Log.e("NotchScreenUtil", "hasNotchAtVivo Exception");
        } finally {
            return ret;
        }
    }

    /**
     * OPPO刘海屏判断
     *
     * @return
     */
    public static boolean hasNotchAtOPPO() {
        return AppMaster.getInstance().getAppContext().getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    // 是否是小米手机
    public static boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }
}
