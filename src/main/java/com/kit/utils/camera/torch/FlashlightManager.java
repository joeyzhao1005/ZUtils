package com.kit.utils.camera.torch;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.util.Log;

import com.kit.utils.log.Zog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FlashlightManager {
    private static FlashlightManager flashlightManager;
    private static final String TAG = FlashlightManager.class.getSimpleName();


    private Context context;

    public static FlashlightManager getInstance() {
        if (flashlightManager == null) {
            flashlightManager = new FlashlightManager();
        }

        return flashlightManager;
    }

    public FlashlightManager setContext(Context context) {
        getInstance().context = context;

        return flashlightManager;
    }


    /**
     * Use reflect
     *
     * @param methodName
     * @param iHardwareService
     * @param argClasses
     * @return
     */
    private static Method getMethod(String methodName, Object iHardwareService,
                                    Class<?>... argClasses) {
        if (iHardwareService == null) {
            return null;
        }
        Class<?> proxyClass = iHardwareService.getClass();

        // test
        Method[] methods = proxyClass.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Log.i("method---", methods[i].getName());
        }

        return maybeGetMethod(proxyClass, methodName, argClasses);
    }

    private static Class<?> maybeForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException cnfe) {
            // OK
            return null;
        } catch (RuntimeException re) {
            Log.w(TAG, "Unexpected error while finding class " + name, re);
            return null;
        }
    }

    private static Method maybeGetMethod(Class<?> clazz, String name,
                                         Class<?>... argClasses) {
        try {
            return clazz.getMethod(name, argClasses);
        } catch (NoSuchMethodException nsme) {
            // OK
            return null;
        } catch (RuntimeException re) {
            Log.w(TAG, "Unexpected error while finding method " + name, re);
            return null;
        }
    }

    private static Object invoke(Method method, Object instance, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            Log.w(TAG, "Unexpected error while invoking " + method, e);
            return null;
        } catch (InvocationTargetException e) {
            Log.w(TAG, "Unexpected error while invoking " + method,
                    e.getCause());
            return null;
        } catch (RuntimeException re) {
            Log.w(TAG, "Unexpected error while invoking " + method, re);
            return null;
        }
    }

    public void enableFlashlight() {
        setFlashlight(true);
    }

    public void disableFlashlight() {
        setFlashlight(false);
    }

    /**
     * Set Flahlight if activate
     *
     * @param active
     */
    private void setFlashlight(boolean active) {

//        LogUtils.i(FlashlightManager.class, "iHardwareService:" + iHardwareService
//                + " setFlashEnabledMethod:" + setFlashEnabledMethod
//                + " getFlashEnabledMethod:" + getFlashEnabledMethod);
        Zog.i("active:" + active);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null) {
            try {
                setFlashlightAbove23(context, active);
            } catch (Exception e) {
                Zog.showException(e);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                && getInstance().context != null) {
            setFlashlightAbove11(context, active);
        }

    }

    public void setFlashlightAbove11(Context context, boolean active) {

        CameraTorch cameraTorch = new CameraTorch();
        cameraTorch.init();
        cameraTorch.toggle(active);


    }

    public void setFlashlightAbove23(Context context, boolean active) throws CameraAccessException {

        Camera2Torch camera2Torch = new Camera2Torch(context);
        camera2Torch.init();
        camera2Torch.toggle(active);
    }


//    private static void setFlashlightConventional(boolean active) {
//        if (camera == null)
//            return;
//
//        Parameters p = camera.getParameters();
//
//        // Set
//        if (active) {
//            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
//            camera.setParameters(p);
//            camera.startPreview(); // 开始亮灯;
//        } else {
//            p.setFlashMode(Parameters.FLASH_MODE_OFF);
//            camera.stopPreview(); // 关掉亮灯
//            camera.release(); // 关掉照相机
//        }
//
//        LogUtils.i(FlashlightManager.class, "setFlashlightConventional active:" + active);
//    }


    public void setFlashLightNormal() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                setFlashlightAbove23(context, false);
            } catch (Exception e) {
                Zog.showException(e);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                && getInstance().context != null) {
            setFlashlightAbove11(context, false);
        }
    }

}
