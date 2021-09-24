package com.kit.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import com.kit.utils.log.Zog;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author joeyzhao
 */
public class ActivityManager {
    private CopyOnWriteArrayList<WeakReference<Activity>> activities = new CopyOnWriteArrayList<>();
    private static ActivityManager instance;

    private ActivityManager() {
    }

    /**
     * 单例模式中获取唯一的ActivityManager实例
     */
    public static ActivityManager getInstance() {
        if (null == instance) {
            instance = new ActivityManager();
        }
        return instance;
    }


    public int getSize() {
        if (activities != null) {
            return activities.size();
        }
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
        Zog.i("pushActivity size:" + getSize());
    }


    public synchronized void popActivity(Class cls) {
        popActivity(cls, true);
    }


    /**
     * 根据类名来销毁activity
     *
     * @param cls
     */
    public synchronized void popActivity(Class cls, boolean isFinish) {
        popActivity(cls, isFinish, -1);
    }


    /**
     * 根据类名来销毁activity
     *
     * @param cls
     */
    public synchronized void popActivity(Class cls, boolean isFinish, int limit) {
        Iterator<WeakReference<Activity>> iter = activities.iterator();
        int curr = 0;
        while (iter.hasNext()) {

            if (curr >= limit && limit != -1) {
                return;
            }
            WeakReference<Activity> weakReference = iter.next();
            Activity activity = weakReference.get();

            if (activity != null && activity.getClass().equals(cls)) {

                weakReference.clear();
                activities.remove(weakReference);
                if (isFinish) {
                    activity.finish();
                }

                curr++;
            }
        }

        Zog.i("popActivity size:" + getSize());
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
                }
            }
        }

        Zog.i("popActivity(activity) size:" + getSize());

    }

    public synchronized void finishActivity(Activity activity) {
        popActivity(activity);
        activity.finish();
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
            }
            activities.remove(weakReference);

        }
        Zog.i("popAllActivity size:" + getSize());
    }

    /**
     * 销毁除了类名为cls的activity的其余所有的activity
     *
     * @param cls
     */
    public synchronized void finishAllActivityExceptOne(Class cls) {
        Zog.i("activities.size():"
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
        Zog.i("popAllActivityExceptOne size:" + getSize());

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
            Zog.i("none activity.");

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

    /**
     * Get activity from context object
     *
     * @param context something
     * @return object of Activity or null if it is not Activity
     */
    public static Activity castActivity(Context context) {
        if (context == null) return null;

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return castActivity(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }

    /**
     * 判断Activity是否还活着
     * @param ctx
     * @return
     */
    public static boolean activityIsAlive(Context ctx) {
        if (ctx == null) {
            return false;
        }

        Activity activity = castActivity(ctx);
        if (activity == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !(activity.isDestroyed() || activity.isFinishing());
        } else {
            return !activity.isFinishing();
        }
    }

}