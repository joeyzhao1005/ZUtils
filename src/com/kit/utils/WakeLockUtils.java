package com.kit.utils;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

import com.kit.utils.log.ZogUtils;

/**
 * Created by Zhao on 16/7/26.
 */
public class WakeLockUtils {

    private static WakeLockUtils wakeLockUtils;


    private Context context;
    PowerManager pm;

    PowerManager.WakeLock wakeLockNear;

    PowerManager.WakeLock wakeLockScreen;


    public static WakeLockUtils getInstance() {
        if (wakeLockUtils == null)
            wakeLockUtils = new WakeLockUtils();

        return wakeLockUtils;
    }


    public WakeLockUtils setContext(Context context) {
        wakeLockUtils.context = context;
        return wakeLockUtils;
    }

    /**
     * 控制点亮屏幕,需要与距离传感器结合
     */
    public void screenOnWithNear() {
        pm = (PowerManager) wakeLockUtils.context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (wakeLockNear == null)
                wakeLockNear = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "near");

            wakeLockNear.setReferenceCounted(false);

            if (!wakeLockNear.isHeld()) {
                ZogUtils.i(wakeLockNear+" acquire");
                wakeLockNear.acquire();
            }
        } else {
            screenOn();
        }

    }


    /**
     * 控制熄灭屏幕,需要与距离传感器结合
     */
    public void screenOffWithNear() {
        pm = (PowerManager) wakeLockUtils.context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (wakeLockNear == null)
                wakeLockNear = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "near");

            wakeLockNear.setReferenceCounted(false);

            if (!wakeLockNear.isHeld()) {
                ZogUtils.i(wakeLockNear+" release");
                wakeLockNear.release();
            }
        } else {
            screenOff();
        }
    }


    /**
     * 点亮屏幕
     */
    public void screenOn() {
        pm = (PowerManager) wakeLockUtils.context.getSystemService(Context.POWER_SERVICE);
        if (wakeLockScreen == null)
            wakeLockScreen = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "screen");

        wakeLockScreen.setReferenceCounted(false);


        if (!wakeLockScreen.isHeld()) {
            wakeLockScreen.acquire();
        }
    }


    /**
     * 熄灭屏幕
     */
    public void screenOff() {
        pm = (PowerManager) wakeLockUtils.context.getSystemService(Context.POWER_SERVICE);

        if (wakeLockScreen == null)
            wakeLockScreen = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "screen");

        wakeLockScreen.setReferenceCounted(false);

        if (!wakeLockScreen.isHeld()) {
            wakeLockScreen.release();
        }
    }


    public void destory() {
        if (wakeLockScreen != null && !wakeLockScreen.isHeld()) {
            wakeLockScreen.acquire();
        }

        wakeLockScreen = null;

        if (wakeLockNear != null && !wakeLockNear.isHeld()) {
            wakeLockNear.release();
        }

        wakeLockNear = null;

        pm = null;

        wakeLockUtils = null;

    }
}
