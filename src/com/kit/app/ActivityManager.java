package com.kit.app;

import android.app.Activity;

import com.kit.utils.log.ZogUtils;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActivityManager {
    private CopyOnWriteArrayList<WeakReference<Activity>> activities = new CopyOnWriteArrayList<WeakReference<Activity>>();
    private static ActivityManager instance;

    private ActivityManager() {
    }

    // 单例模式中获取唯一的ZhaoActivityManager实例
    public static ActivityManager getInstance() {
        if (null == instance) {
            instance = new ActivityManager();
        }
        return instance;
    }


    public int getSize() {
        if (activities != null)
            return activities.size();
        return 0;
    }

    /**
     * activity压入activities
     *
     * @param activity
     */
    public synchronized void pushActivity(Activity activity) {
        if (activity != null) {
            activities.add(new WeakReference<Activity>(activity));
        }
        ZogUtils.i("pushActivity size:" + getSize());
    }

    /**
     * 根据类名来销毁activity
     *
     * @param cls
     */
    public synchronized void popActivity(Class cls) {
        Iterator<WeakReference<Activity>> iter = activities.iterator();
        while (iter.hasNext()) {

            WeakReference<Activity> weakReference = iter.next();
            Activity activity = weakReference.get();

            if (activity != null && activity.getClass().equals(cls)) {

                activity.finish();
                weakReference.clear();
                activities.remove(weakReference);
                activity = null;
            }
        }

        ZogUtils.i("popActivity size:" + getSize());
    }

    /**
     * 弹出activity
     *
     * @param activity
     */
    public synchronized void popActivity(Activity activity) {

        Iterator<WeakReference<Activity>> iter = activities.iterator();
        while (iter.hasNext()) {
            WeakReference<Activity> weakReference = iter.next();
            Activity act = weakReference.get();
            if (act != null && activity != null) {
                if (act.getClass().equals(activity.getClass())) {
                    activities.remove(weakReference);
                    weakReference.clear();
                    act.finish();
                    act = null;
                    activity = null;
                }
            }
        }

        ZogUtils.i("popActivity(activity) size:" + getSize());

    }

    /**
     * 遍历所有Activity并finish（一般用于退出应用，销毁APP）
     */
    public synchronized void popAllActivity() {
        Iterator<WeakReference<Activity>> iter = activities.iterator();
        while (iter.hasNext()) {

            WeakReference<Activity> weakReference = iter.next();
            Activity activity = weakReference.get();
            if (activity != null) {
                activity.finish();

                weakReference.clear();
                activity = null;
            }
            activities.remove(weakReference);

        }
        ZogUtils.i("popAllActivity size:" + getSize());

    }

    /**
     * 销毁除了类名为cls的activity的其余所有的activity
     *
     * @param cls
     */
    public synchronized void popAllActivityExceptOne(Class cls) {
        ZogUtils.i("activities.size():"
                + activities.size());
        Iterator<WeakReference<Activity>> iter = activities.iterator();
        while (iter.hasNext()) {
            WeakReference<Activity> weakReference = iter.next();
            Activity activity = weakReference.get();
            if (activity != null && !activity.getClass().equals(cls)) {
                activities.remove(weakReference);

                weakReference.clear();

                activity.finish();
            }

        }
        ZogUtils.i("popAllActivityExceptOne size:" + getSize());

    }

    /**
     * 获取最新压入activity
     *
     * @return
     */
    public synchronized Activity getCurrActivity() {

        WeakReference<Activity> activity = null;
        try {
            activity = activities.get(activities.size() - 1);
            return activity.get();

        } catch (Exception e) {
            ZogUtils.i("none activity.");

//            ZogUtils.showException(e);
            return null;
        }
    }

    /**
     * 获取压入特定activity
     *
     * @return
     */
    public synchronized Activity getActivity(Class cls) {
        Iterator<WeakReference<Activity>> iter = activities.iterator();
        while (iter.hasNext()) {
            WeakReference<Activity> weakReference = iter.next();
            Activity activity = weakReference.get();

            if (activity != null && activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }


    /**
     * 判断是否存在类名为cls的activity
     *
     * @param cls
     * @return
     */
    public synchronized boolean isExistActivity(Class cls) {

        Iterator<WeakReference<Activity>> iter = activities.iterator();
        while (iter.hasNext()) {
            WeakReference<Activity> weakReference = iter.next();
            Activity activity = weakReference.get();

            if (activity != null && activity.getClass().equals(cls)) {
                return true;
            }
        }

        return false;
    }

}