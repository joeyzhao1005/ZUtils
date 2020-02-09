package com.kit.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.kit.app.application.AppMaster;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConnectivityUtils {

    private static ConnectivityUtils connectivityUtils;
    private ConnectivityManager mConnectivityManager;
    private WifiManager wm;

    public static ConnectivityUtils getInstance() {
        if (connectivityUtils == null) {
            connectivityUtils = new ConnectivityUtils();
            Context context = AppMaster.getInstance().getAppContext();
            connectivityUtils.mConnectivityManager = (ConnectivityManager) context.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityUtils.wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        }
        return connectivityUtils;
    }

    /**
     * WIFI网络开关
     */
    @SuppressWarnings("MissingPermission")
    public void toggleWiFi(boolean enabled) {
        if (AppUtils.isPermission(Manifest.permission.CHANGE_WIFI_STATE)) {
            connectivityUtils.wm.setWifiEnabled(enabled);
        }
    }

    public boolean isWiFiEnabled() {

        return wm.isWifiEnabled();
    }


    /**
     * 网络是否可用
     * @return
     */
public boolean isNetWorkAvaliable() {
        NetworkInfo networkinfo = connectivityUtils.mConnectivityManager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }




    private boolean isMobileNetWorkEnabled() {

        Class<?> conMgrClass = null; // ConnectivityManager类
        Field iConMgrField = null; // ConnectivityManager类中的字段
        Object iConMgr = null; // IConnectivityManager类的引用
        Class<?> iConMgrClass = null; // IConnectivityManager类
        Method getMobileDataEnabledMethod = null; // setMobileDataEnabled方法

        try {
            // 取得ConnectivityManager类
            conMgrClass = Class.forName(mConnectivityManager.getClass()
                    .getName());
            // 取得ConnectivityManager类中的对象mService
            iConMgrField = conMgrClass.getDeclaredField("mService");
            // 设置mService可访问
            iConMgrField.setAccessible(true);
            // 取得mService的实例化类IConnectivityManager
            iConMgr = iConMgrField.get(mConnectivityManager);
            // 取得IConnectivityManager类
            iConMgrClass = Class.forName(iConMgr.getClass().getName());
            // 取得IConnectivityManager类中的getMobileDataEnabled(boolean)方法
            getMobileDataEnabledMethod = iConMgrClass
                    .getDeclaredMethod("getMobileDataEnabled");
            // 设置getMobileDataEnabled方法可访问
            getMobileDataEnabledMethod.setAccessible(true);
            // 调用getMobileDataEnabled方法
            return (Boolean) getMobileDataEnabledMethod.invoke(iConMgr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public Object invokeMethod(String methodName, Object[] arg)
            throws Exception {
        Class ownerClass = mConnectivityManager.getClass();
        Class[] argsClass = null;
        if (arg != null) {
            argsClass = new Class[1];
            argsClass[0] = arg.getClass();
        }
        @SuppressWarnings("unchecked")
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(mConnectivityManager, arg);
    }

    @SuppressWarnings("rawtypes")
    public Object invokeBooleanArgMethod(String methodName, boolean value)
            throws Exception {
        Class ownerClass = mConnectivityManager.getClass();
        Class[] argsClass = new Class[1];
        argsClass[0] = boolean.class;
        @SuppressWarnings("unchecked")
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(mConnectivityManager, value);
    }

    /**
     * 移动网络开关
     */
    public void toggleMobileData(boolean enabled) {
        // ConnectivityManager conMgr = (ConnectivityManager) context
        // .getSystemService(Context.CONNECTIVITY_SERVICE);

        Class<?> conMgrClass = null; // ConnectivityManager类
        Field iConMgrField = null; // ConnectivityManager类中的字段
        Object iConMgr = null; // IConnectivityManager类的引用
        Class<?> iConMgrClass = null; // IConnectivityManager类
        Method setMobileDataEnabledMethod = null; // setMobileDataEnabled方法

        try {
            // 取得ConnectivityManager类
            conMgrClass = Class.forName(mConnectivityManager.getClass()
                    .getName());
            // 取得ConnectivityManager类中的对象mService
            iConMgrField = conMgrClass.getDeclaredField("mService");
            // 设置mService可访问
            iConMgrField.setAccessible(true);
            // 取得mService的实例化类IConnectivityManager
            iConMgr = iConMgrField.get(mConnectivityManager);
            // 取得IConnectivityManager类
            iConMgrClass = Class.forName(iConMgr.getClass().getName());
            // 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法
            setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
                    "setMobileDataEnabled", Boolean.TYPE);
            // 设置setMobileDataEnabled方法可访问
            setMobileDataEnabledMethod.setAccessible(true);
            // 调用setMobileDataEnabled方法
            setMobileDataEnabledMethod.invoke(iConMgr, enabled);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static String getNetworkTypeReadable(Context context) {
        final ConnectivityManager conMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") final NetworkInfo activeNetInfo = conMgr.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.isConnected()) {
            switch (activeNetInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return "wifi";
                case ConnectivityManager.TYPE_MOBILE:
                case ConnectivityManager.TYPE_MOBILE_DUN:
                case ConnectivityManager.TYPE_MOBILE_HIPRI:
                case ConnectivityManager.TYPE_MOBILE_MMS:
                case ConnectivityManager.TYPE_MOBILE_SUPL:
                case ConnectivityManager.TYPE_WIMAX:
                    return "mobile";
                default:
                    return "mobile";


            }
        } else {
            return "none";
        }
    }


    @SuppressLint("MissingPermission")
    public static int getNetworkType(Context context) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectionManager.getActiveNetworkInfo().getType();
    }

}
