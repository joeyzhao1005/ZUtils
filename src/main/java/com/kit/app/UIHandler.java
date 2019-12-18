package com.kit.app;

import android.os.Handler;
import android.os.Looper;

/**
 * @author joeyzhao
 * <p>
 * UIHandler对Handler作了封装
 * 提供全局性的UI支持
 */
public class UIHandler {
    private static Handler mainHandler;

    public static Handler get() {
        if (mainHandler == null) {
            mainHandler = new Handler(Looper.getMainLooper());
        }
        return mainHandler;
    }

    public static Handler create() {
        return new Handler(Looper.getMainLooper());
    }

    public static void run(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            create().post(runnable);
        } else {
            runnable.run();
        }
    }


    public static boolean check() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            return false;
        } else {
            return true;
        }
    }
}
