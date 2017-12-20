package com.kit.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

import com.kit.utils.log.ZogUtils;

/**
 * Created by Zhao on 16/7/26.
 */
public class WakeLockUtils {

    private static WakeLockUtils wakeLockUtils;


    PowerManager pm;

    PowerManager.WakeLock wakeLockNear;

    PowerManager.WakeLock wakeLockScreen;


    public static WakeLockUtils getInstance() {
        if (wakeLockUtils == null)
            wakeLockUtils = new WakeLockUtils();

        return wakeLockUtils;
    }


    /**
     * 控制点亮屏幕,需要与距离传感器结合
     */
    public void screenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            screenOnAboveAPI23();
        } else {
            screenOnOld();
        }

    }


    /**
     * 控制熄灭屏幕,需要与距离传感器结合
     */
    public void screenOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            screenOffAboveAPI23();
        } else {
            screenOffOld();
        }
    }


    /**
     * 销毁
     */
    public void destory() {
        if (wakeLockScreen != null) {
            wakeLockScreen.release();
        }
        wakeLockScreen = null;

        if (wakeLockNear != null) {
            wakeLockNear.release();
        }
        wakeLockNear = null;

        pm = null;

        wakeLockUtils = null;

    }

    /**
     * 控制熄灭屏幕,需要与距离传感器结合
     */
    @TargetApi(23)
    private void screenOffAboveAPI23() {
        pm = (PowerManager) ResWrapper.getInstance().getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (wakeLockNear == null)
            wakeLockNear = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "near");

        wakeLockNear.setReferenceCounted(false);

        if (!wakeLockNear.isHeld()) {
            ZogUtils.i(wakeLockNear + " acquire");
            wakeLockNear.acquire();
        }


    }


    /**
     * 控制点亮屏幕,需要与距离传感器结合
     */
    @TargetApi(23)
    private void screenOnAboveAPI23() {
        pm = (PowerManager) ResWrapper.getInstance().getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (wakeLockNear == null)
            wakeLockNear = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "near");

        wakeLockNear.setReferenceCounted(false);

        if (wakeLockNear.isHeld()) {
            ZogUtils.i(wakeLockNear + " release");
            wakeLockNear.release();
        }
    }


    /**
     * 点亮屏幕
     */

    private void screenOnOld() {
        pm = (PowerManager) ResWrapper.getInstance().getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (wakeLockScreen == null)
            wakeLockScreen = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "screen");

        wakeLockScreen.setReferenceCounted(false);

        if (!wakeLockScreen.isHeld()) {
            ZogUtils.i(wakeLockScreen + " acquire");
            wakeLockScreen.acquire();
        }
    }


    /**
     * 熄灭屏幕
     */
    private void screenOffOld() {
        pm = (PowerManager) ResWrapper.getInstance().getApplicationContext().getSystemService(Context.POWER_SERVICE);

        if (wakeLockScreen == null)
            wakeLockScreen = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "screen");

        wakeLockScreen.setReferenceCounted(false);

        if (wakeLockScreen.isHeld()) {
            ZogUtils.i(wakeLockScreen + " release");
            wakeLockScreen.release();
        }
    }


}
