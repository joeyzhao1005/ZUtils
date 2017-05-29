package com.kit.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.kit.utils.log.ZogUtils;

public class ToastUtils {
    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void mkLongTimeToast(String msg) {
        try {
            Context context = ResWrapper.getInstance().getContext();
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }

    public static void mkLongTimeToast(int msgStringId) {
        try {
            Context context = ResWrapper.getInstance().getContext();
            Toast.makeText(context, context.getResources().getString(msgStringId), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }

    public static void mkLongTimeToast(Context context, int msgStringId) {
        try {
            Toast.makeText(context, context.getResources().getString(msgStringId), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }

    public static void mkLongTimeToast(Context context, String msgString) {
        try {
            Toast.makeText(context, msgString, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }


    public static void mkShortTimeToast(String msg) {
        try {
            Context context = ResWrapper.getInstance().getContext();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }


    public static void mkShortTimeToast(int msgStringId) {
        try {
            Context context = ResWrapper.getInstance().getContext();
            Toast.makeText(context, context.getResources().getString(msgStringId), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }


    public static void mkShortTimeToast(Context context, int msgStringId) {
        try {
            Toast.makeText(context, context.getResources().getString(msgStringId), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }

    public static void mkShortTimeToast(Context context, String msgString) {
        try {
            Toast.makeText(context, msgString, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ZogUtils.showException(e);
        }
    }


    /**
     * @param text     内容string
     * @param duration 时长
     * @return void 返回类型
     * @Title mkToast
     * @Description 自定义toast内容和时长
     */
    public static void mkToast(String text, int duration) {

        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else {
            Context context = ResWrapper.getInstance().getContext();
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        mHandler.postDelayed(r, duration);

        mToast.show();
    }

    /**
     * @param resId    内容string id
     * @param duration 时长
     * @return void 返回类型
     * @Title mkToast
     * @Description 自定义toast内容和时长
     */
    public static void mkToast(int resId, int duration) {
        Context context = ResWrapper.getInstance().getContext();

        String text = context.getResources().getString(resId);
        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, duration);

        mToast.show();
    }
}
