package com.kit.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.kit.app.UIHandler;
import com.kit.utils.log.Zog;

public class ToastUtils {
    private static Toast mToast;
    private static Runnable r = new Runnable() {
        @Override
        public void run() {
            mToast.cancel();
        }
    };

    public static void l(String msg) {
        try {
            Looper.prepare();
            Toast.makeText(ResWrapper.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            Looper.loop();
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void l(int msgStringId) {
        try {
            Looper.prepare();
            Toast.makeText(ResWrapper.getInstance().getApplicationContext()
                    , ResWrapper.getInstance().getApplicationContext().getResources().getString(msgStringId)
                    , Toast.LENGTH_LONG).show();
            Looper.loop();
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void l(Context context, int msgStringId) {
        try {
            Looper.prepare();
            Toast.makeText(context, context.getResources().getString(msgStringId), Toast.LENGTH_LONG).show();
            Looper.loop();
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void l(Context context, String msgString) {
        try {
            Looper.prepare();
            Toast.makeText(context, msgString, Toast.LENGTH_LONG).show();
            Looper.loop();
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void s(String msg) {
        try {
            Looper.prepare();
            Toast.makeText(ResWrapper.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void s(@StringRes int msgStringId) {

        try {
            Looper.prepare();
            Toast.makeText(ResWrapper.getInstance().getApplicationContext()
                    , ResWrapper.getInstance().getApplicationContext().getResources().getString(msgStringId)
                    , Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void s(Context context, @StringRes int msgStringId) {
        if (context == null) {
            context = ResWrapper.getInstance().getApplicationContext();
        }
        try {
            Looper.prepare();
            Toast.makeText(context, context.getResources().getString(msgStringId), Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void s(Context context, String msgString) {
        try {
            Looper.prepare();
            Toast.makeText(context, msgString, Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    /**
     * @param text     内容string
     * @param duration 时长
     * @return void 返回类型
     * @Title mkToast
     * @Description 自定义toast内容和时长
     */
    public static void toast(String text, int duration) {

        UIHandler.getMainHandler().removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(ResWrapper.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
        }
        UIHandler.getMainHandler().postDelayed(r, duration);

        mToast.show();
    }

    /**
     * @param resId    内容string id
     * @param duration 时长
     * @return void 返回类型
     * @Title mkToast
     * @Description 自定义toast内容和时长
     */
    public static void toast(int resId, int duration) {

        String text = ResWrapper.getInstance().getApplicationContext().getResources().getString(resId);
        UIHandler.getMainHandler().removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(ResWrapper.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
        }
        UIHandler.getMainHandler().postDelayed(r, duration);

        mToast.show();
    }
}
