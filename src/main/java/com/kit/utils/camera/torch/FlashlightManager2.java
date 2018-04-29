package com.kit.utils.camera.torch;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.kit.utils.log.Zog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Deprecated
public class FlashlightManager2 {
    private static FlashlightManager2 flashlightManager;
    private static final String TAG = FlashlightManager2.class.getSimpleName();
    private static Camera camera = null;

    private static final Object iHardwareService;
    private static final Method setFlashEnabledMethod;
    private static final Method getFlashEnabledMethod;


    private Context context;

    public static FlashlightManager2 getInstance() {
        if (flashlightManager == null) {
            flashlightManager = new FlashlightManager2();
        }

        return flashlightManager;
    }

    public FlashlightManager2 setContext(Context context) {
        getInstance().context = context;

        return flashlightManager;
    }

    /**
     * Use Static Intialize Object,Setting HardwareService Manager Object and
     * flash method.
     */
    static {
        iHardwareService = getHardwareService();
        setFlashEnabledMethod = getMethod("setFlashlightEnabled",
                iHardwareService, boolean.class);
        getFlashEnabledMethod = getMethod("getFlashlightEnabled",
                iHardwareService, new Class[0]); // here must set null
        if (iHardwareService == null) {
            Log.v(TAG, "This device does supports control of a flashlight");
        } else {
            Log.v(TAG, "This device does not support control of a flashlight");
        }

        camera = getCamera();
    }


    public static Camera getCamera() {
        if (camera != null) {
            return camera;
        } else {
            camera = Camera.open();
        }
        return camera;
    }

    /**
     * Get Hardware Service
     *
     * @return
     */
    private static Object getHardwareService() {

        // Use reflect get system service mamger object
        Class<?> serviceManagerClass = maybeForName("android.os.ServiceManager");
        if (serviceManagerClass == null) {
            return null;
        }

        // Get getService function method object
        Method getServiceMethod = maybeGetMethod(serviceManagerClass,
                "getService", String.class);
        if (getServiceMethod == null) {
            return null;
        }

        Object hardwareService = invoke(getServiceMethod, null, "hardware");
        if (hardwareService == null) {
            return null;
        }

        Class<?> iHardwareServiceStubClass = maybeForName("android.os.IHardwareService$Stub");
        if (iHardwareServiceStubClass == null) {
            return null;
        }

        Method asInterfaceMethod = maybeGetMethod(iHardwareServiceStubClass,
                "asInterface", IBinder.class);
        if (asInterfaceMethod == null) {
            return null;
        }

        return invoke(asInterfaceMethod, null, hardwareService);
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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M
                && context != null) {
            try {
                setFlashlightAbove23(context, active);
            } catch (Exception e) {
                Zog.showException(e);
            }
        } else {
            Zog.i( "active:" + active);

            if (iHardwareService != null && setFlashEnabledMethod != null
                    && getFlashEnabledMethod != null) {
                try {
                    //是否可以通过反射来打开手电筒
                    Boolean enabled = (Boolean) getFlashEnabledMethod.invoke(
                            iHardwareService, (Object[]) null);

                    if (enabled) {
                        setFlashEnabledMethod.invoke(iHardwareService, active);
                    } else {
                        setFlashLightNormal();
                    }

                } catch (Exception e) {
                    setFlashLightNormal();
                }
            } else {
                setFlashLightNormal();
            }
        }

    }

    public void setFlashlightAbove23(Context context, boolean active) throws CameraAccessException {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M
                && getInstance().context != null) {
            Camera2Torch camera2Torch = new Camera2Torch(context);
            camera2Torch.init();
            camera2Torch.toggle(active);
        }

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


    public  void setFlashLightNormal() {

        if (camera == null) {
            camera = getCamera();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                setFlashlightAbove23(context, false);
            }catch (Exception e){
                Zog.showException(e);
            }

        }else {
            Parameters params = camera.getParameters();

            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.stopPreview(); // 关掉亮灯
            camera.release(); // 关掉照相机
            camera = null;
        }
    }

}
