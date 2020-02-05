package com.kit.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.kit.app.ActivityManager;
import com.kit.app.application.AppMaster;
import com.kit.sharedpreferences.DataCache;
import com.kit.utils.log.Zog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

public class DeviceUtils {

    private static String deviceId;

    /**
     * 获取设备唯一标识符
     *
     * @return 唯一标识符
     */
    public static String getDeviceId() {
        // 通过 SharedPreferences 获取 deviceId
        deviceId = DataCache.getInstance().loadStringSharedPreference("deviceId");
        if (deviceId != null) {
            return deviceId;
        }

        // 获取 ANDROID_ID
        String androidId = Settings.System.getString(
                AppMaster.getInstance().getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!StringUtils.isEmptyOrNullStr(androidId)) {
            // 通过 ANDROID_ID 生成 deviceId（唯一标识符）
            deviceId = MD5Utils.getMD5String(androidId);
        } else {
            // 通过 UUID 生成 deviceId（唯一标识符）
            deviceId = MD5Utils.getMD5String(UUID.randomUUID().toString());
        }
        // 保存 deviceId 到 SharedPreferences
        DataCache.getInstance().saveSharedPreferences("deviceId", deviceId);
        return deviceId;
    }


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
            Zog.showException(e);
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
            Zog.showException(e);
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
            Zog.showException(e);
        }

        return model;
    }


    private static int statusBarHeight = 0;


    public static final int DEVICE_ADMIN = 70 + 1;


    public static boolean lockScreen(@Nullable Activity activity, Class<?> adminManagerReceiver) {

        if (activity == null) {
            activity = ActivityManager.getInstance().getCurrActivity();
        }

        //获取设备管理服务
        DevicePolicyManager policyManager = (DevicePolicyManager) activity.getApplicationContext()
                .getSystemService(Context.DEVICE_POLICY_SERVICE);

        //AdminReceiver 继承自 DeviceAdminReceiver
        ComponentName componentName = new ComponentName(activity, adminManagerReceiver);
        if (policyManager == null) {
            return false;
        }

        boolean active = policyManager.isAdminActive(componentName);
        if (!active) {
            //若无权限
            try {
                // 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

                //权限列表
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                //描述(additional explanation)
//                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Lock Screen");

//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                activity.startActivityForResult(intent, DEVICE_ADMIN);
                activity.startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        policyManager.lockNow();//直接锁屏
        return true;
    }


    /**
     * 获取是否存在NavigationBar
     *
     * @param context
     * @return
     */
    public static boolean isDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar || checkDeviceHasNavigationBar(context);
    }


    public static boolean checkDeviceHasNavigationBar(Context context) {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(context)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }


    /**
     * 获取虚拟功能键高度
     *
     * @param context
     * @return
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }


    /**
     * Get the screen height.
     *
     * @param context
     * @return the screen height
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        if (screenHeight != 0) {
            return screenHeight;
        }
        if (!(context instanceof Activity)) {
            context = ActivityManager.getInstance().getCurrActivity();
        }

        if (context == null) {
            return 0;
        }

        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        if (ApiLevel.ATLEAST_HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        } else {
            screenHeight = display.getHeight();
        }

        return screenHeight;
    }

    private static int screenHeight;


    @Deprecated
    public static int getRealScreenHeight() {
        return getRealScreenHeight(null);
    }

    public static int getRealScreenHeight(Context context) {
        if (realScreenHeight != 0) {
            return realScreenHeight;
        }
        if (!(context instanceof Activity)) {
            context = ActivityManager.getInstance().getCurrActivity();
        }

        if (context == null) {
            return 0;
        }

        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        if (ApiLevel.ATLEAST_JB_MR1) {
            Point size = new Point();
            display.getRealSize(size);
            realScreenHeight = size.y;
        } else {
            realScreenHeight = display.getHeight();
        }

        return realScreenHeight;
    }

    private static int realScreenHeight;

    /**
     * Get the screen width.
     *
     * @param context
     * @return the screen width
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        if (screenWidth != 0) {
            return screenWidth;
        }

        if (context == null) {
            context = AppMaster.getInstance().getAppContext();
        }

        if (context == null) {
            return 768;
        }

        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        if (ApiLevel.ATLEAST_HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        } else {
            screenWidth = display.getWidth();
        }

        return screenWidth;
    }

    private static int screenWidth;

    public static int getRealScreenWidth(Context context) {
        if (realScreenWidth != 0) {
            return realScreenWidth;
        }

        if (!(context instanceof Activity)) {
            context = ActivityManager.getInstance().getCurrActivity();
        }

        if (context == null) {
            return 1080;
        }

        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        if (ApiLevel.ATLEAST_HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            realScreenWidth = size.x;
        } else {
            realScreenWidth = display.getWidth();
        }
        return realScreenWidth;
    }

    private static int realScreenWidth;

    /**
     * Get the screen width.
     *
     * @return the screen width
     */
    @Deprecated
    public static int getRealScreenWidth() {
        return getRealScreenWidth(null);
    }

    /**
     * 获取虚拟按键高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        if (ApiLevel.ATLEAST_Q) {
            //Android Q 统一启用导航栏高度
            Zog.e("Android Q 及以上版本不建议使用getNavigationBarHeight来获取导航栏，建议采用ViewCompat.setOnApplyWindowInsetsListener的方式来监听导航栏");
        }

        if (navigationBarHeight != -1) {
            return navigationBarHeight;
        }

        if (ApiLevel.ATLEAST_JB_MR1 && Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0) {
            //小米手势导航 启用
            navigationBarHeight = 0;
            return navigationBarHeight;
        } else {
            navigationBarHeight = getNaviBarHeight(context);
            return navigationBarHeight;
        }

    }

    private static int getNaviBarHeight(@Nullable Context context) {
        if (context == null) {
            context = AppMaster.getInstance().getAppContext();
        }
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                return DeviceUtils.getRealScreenHeight(context) - DeviceUtils.getScreenHeight(context);
            } else {
                return DeviceUtils.getRealScreenWidth(context) - DeviceUtils.getScreenWidth(context);
            }
        } else {
            return 0;
        }
    }

    public static void setNavigationBarHeight(int navigationBarHeight) {
        DeviceUtils.navigationBarHeight = navigationBarHeight;
    }

    private static int navigationBarHeight = -1;

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
                int x = (Integer) (field.get(obj));
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
        PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
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
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
        return flag;
    }


}
