package com.kit.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.Display;

import com.kit.app.ActivityManager;
import com.kit.receiver.DeviceAdminManagerReceiver;
import com.kit.utils.log.ZogUtils;

import java.lang.reflect.Field;

public class DeviceUtils {


    /**
     * 设备厂商
     *
     * @return
     */
    public static String getDeviceManufacturer() {
        String model = null;

        try {
            model = android.os.Build.MANUFACTURER;
        } catch (Exception e) {
            ZogUtils.showException(e);
        }

        return model;
    }


    /**
     * 系统版本代号
     *
     * @return
     */
    public static String getDeviceDisplay() {
        String model = null;

        try {
            model = android.os.Build.DISPLAY;
        } catch (Exception e) {
            ZogUtils.showException(e);
        }

        return model;
    }


    /**
     * 设备型号
     *
     * @return
     */
    public static String getDeviceModel() {
        String model = null;

        try {
            model = android.os.Build.MODEL;
        } catch (Exception e) {
            ZogUtils.showException(e);
        }

        return model;
    }


    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();

    }


    private static int statusBarHeight = 0;


    public static final int DEVICE_ADMIN = 70 + 1;


    public static void lockScreen(@Nullable Activity activity) {

        if (activity == null)
            activity = ActivityManager.getInstance().getCurrActivity();

        //获取设备管理服务
        DevicePolicyManager policyManager = (DevicePolicyManager) activity
                .getSystemService(Context.DEVICE_POLICY_SERVICE);

        //AdminReceiver 继承自 DeviceAdminReceiver
        ComponentName componentName = new ComponentName(activity, DeviceAdminManagerReceiver.class);

        boolean active = policyManager.isAdminActive(componentName);
        if (!active) {//若无权限
            // 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

            //权限列表
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

            //描述(additional explanation)
//        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "------------");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(intent, DEVICE_ADMIN);

            // 判断该组件是否有系统管理员的权限
            active = policyManager.isAdminActive(componentName);
            if (active) {
                policyManager.lockNow();//并锁屏
            }
        }
        if (active) {
            policyManager.lockNow();//直接锁屏
        }
    }


    /**
     * 判定是否有虚拟按键
     *
     * @param context
     * @return
     */
    public static boolean isShowNavigationBar(Context context) {
        if (getAPIVersion() >= 14) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("config_showNavigationBar", "bool", "android");
            if (resourceId > 0) {
                return resources.getBoolean(resourceId);
            }
        }
        return false;
    }


    /**
     * Get the screen height.
     *
     * @param context
     * @return the screen height
     */
    public static int getScreenHeight(Context context) {
        if (context == null) {
            context = ActivityManager.getInstance().getCurrActivity();
        }

        if (context instanceof Activity) {
            Display display = ((Activity) context).getWindowManager()
                    .getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Point size = new Point();
                display.getSize(size);
                return size.y;
            } else {
                return display.getHeight();
            }
        }
        return 0;
    }


    public static int getRealScreenHeight(Context context) {
        if (context == null) {
            context = ActivityManager.getInstance().getCurrActivity();
        }

        if (context instanceof Activity) {
            Display display = ((Activity) context).getWindowManager()
                    .getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Point size = new Point();
                display.getRealSize(size);
                return size.y;
            } else {
                return display.getHeight();
            }
        }
        return 0;
    }

    /**
     * Get the screen width.
     *
     * @param context
     * @return the screen width
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenWidth(Context context) {
        if (context == null) {
            context = ActivityManager.getInstance().getCurrActivity();
        }

        if (context instanceof Activity) {
            Display display = ((Activity) context).getWindowManager()
                    .getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Point size = new Point();
                display.getSize(size);
                return size.x;
            } else {
                return display.getWidth();
            }
        }
        return 0;
    }


    /**
     * Get the screen width.
     *
     * @param context
     * @return the screen width
     */
    public static int getRealScreenWidth(Context context) {
        if (context == null) {
            context = ActivityManager.getInstance().getCurrActivity();
        }

        if (context instanceof Activity) {
            Display display = ((Activity) context).getWindowManager()
                    .getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Point size = new Point();
                display.getRealSize(size);
                return size.x;
            } else {
                return display.getWidth();
            }
        }
        return 0;
    }

    /**
     * 获取虚拟按键高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);

        if (height == 0)
            height = DensityUtils.dip2px(context, 44);
        return height;
    }

    /**
     * 获取虚拟按键宽度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarWidth(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_width", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }


    /**
     * 获取虚拟按键横向时候的高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeithtLandscape(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height_landscape", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {

        if (statusBarHeight == 0) {
            int sbar = 0;

            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer)(field.get(obj));
                sbar = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            statusBarHeight = sbar;
        }
        if (statusBarHeight <= 0) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                statusBarHeight = DensityUtils.dip2px(context, 24);
            } else {
                statusBarHeight = DensityUtils.dip2px(context, 25);

            }
        }
        return statusBarHeight;
    }


    /**
     * 获取ActionBar的高度
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))// 如果资源是存在的、有效的
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }


    /**
     * 获取设备的APILevel
     *
     * @return
     */
    public static int getAPIVersion() {
        int APIVersion;
        try {
            APIVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            APIVersion = 0;
        }
        return APIVersion;
    }


    /**
     * 判定屏幕是否点亮
     *
     * @param context
     * @return
     */
    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean screen = pm.isScreenOn();

        return screen;
    }


    /**
     * 是否解锁
     *
     * @param context
     * @return
     */
    public static boolean isKeyguard(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
        return flag;
    }


}
