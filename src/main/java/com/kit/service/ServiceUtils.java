package com.kit.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.kit.app.application.AppMaster;
import com.kit.utils.AppUtils;
import com.kit.utils.ResWrapper;
import com.kit.utils.intent.BundleData;
import com.kit.utils.intent.IntentManager;
import com.kit.utils.log.Zog;

import java.util.ArrayList;
import java.util.List;

public class ServiceUtils {


    public ServiceUtils() {
    }

    public void startService(final Context context, final Service... services) {

        AppUtils.newThread(() -> {
            for (Service s : services) {

                if (!ServiceUtils.isServiceRunning(context, s.getClass()
                        .getName())) {

                    Intent i = new Intent(context, s.getClass());
                    context.startService(i);

                    Zog.i("start service " + s.getClass().getName());
                }

            }
        });
    }


    public void startService(final Class... clazzes) {
        AppUtils.newThread(() -> {
            final Context context = AppMaster.getInstance().getAppContext();
            for (Class c : clazzes) {
                if (!ServiceUtils.isServiceRunning(context, c.getName())) {

                    Intent i = new Intent(context, c);
                    context.startService(i);

                    Zog.i("start service " + c.getName());
                }

            }
        });
    }

    public void startService(final Class clazzes, final Bundle bundleData) {
        AppUtils.newThread(() -> {
            final Context context = ResWrapper.getApplicationContext();
            Zog.i("start service " + clazzes.getName());

            IntentManager.get().setClass(context, clazzes).bundleData(bundleData).startService(context);

        });
    }


    public void bindService(final Context context, final ArrayList<Service> services,
                            final ArrayList<ServiceConnection> mConnection) {

        for (int i = 0; i < services.size(); i++) {

            if (!ServiceUtils.isServiceRunning(context, services.get(i)
                    .getClass().getName())) {

                context.bindService(new Intent(context, services.get(i)
                                .getClass()), mConnection.get(i),
                        Context.BIND_AUTO_CREATE);
            }

        }

    }

    public void stopService(final Context context, final Service... services) {
        for (Service s : services) {
            Intent service = new Intent(context, s.getClass());
            context.stopService(service);
        }

    }


    public void stopService(final Class... clazzes) {
        final Context context = ResWrapper.getApplicationContext();

        for (Class c : clazzes) {
            Intent service = new Intent(context, c);
            context.stopService(service);
        }
    }

    public void restartService(final Context context, final Service... services) {
        System.out.println("ServiceManager restartService");
        stopService(context, services);

        for (Service s : services) {
            Intent service = new Intent(context, s.getClass());
            context.startService(service);
        }

    }


    /**
     * 用来判断服务是否运行.
     *
     * @param mContext
     * @param clazzName 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String clazzName) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(1000);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {

            String thisClassName = (serviceList.get(i).service.getClassName());
            if (thisClassName.equals(clazzName)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
