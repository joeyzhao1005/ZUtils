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
            UIHandler.getMainHandler().post(() ->
                    Toast.makeText(ResWrapper.getApplicationContext(), msg, Toast.LENGTH_LONG).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void l(int msgStringId) {
        try {
            UIHandler.getMainHandler().post(() ->
                    Toast.makeText(ResWrapper.getApplicationContext()
                            , ResWrapper.getApplicationContext().getResources().getString(msgStringId)
                            , Toast.LENGTH_LONG).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void l(Context context, int msgStringId) {
        try {
            UIHandler.getMainHandler().post(() ->

                    Toast.makeText(context, context.getResources().getString(msgStringId), Toast.LENGTH_LONG).show()
            );

        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void l(Context context, String msgString) {
        try {
            UIHandler.getMainHandler().post(() ->

                    Toast.makeText(context, msgString, Toast.LENGTH_LONG).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void s(String msg) {
        try {
            UIHandler.getMainHandler().post(() ->
                    Toast.makeText(ResWrapper.getApplicationContext(), msg, Toast.LENGTH_SHORT).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void s(@StringRes int msgStringId) {

        try {
            UIHandler.getMainHandler().post(() ->
                    Toast.makeText(ResWrapper.getApplicationContext()
                            , ResWrapper.getApplicationContext().getResources().getString(msgStringId)
                            , Toast.LENGTH_SHORT).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void s(final Context context, @StringRes int msgStringId) {
        final Context contxt;
        if (context == null) {
            contxt = ResWrapper.getApplicationContext();
        } else {
            contxt = context;
        }
        try {
            UIHandler.getMainHandler().post(() ->
                    Toast.makeText(contxt, contxt.getResources().getString(msgStringId), Toast.LENGTH_SHORT).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void s(Context context, String msgString) {
        try {
            UIHandler.getMainHandler().post(() ->
                    Toast.makeText(context, msgString, Toast.LENGTH_SHORT).show()
            );
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
            mToast = Toast.makeText(ResWrapper.getApplicationContext(), text, Toast.LENGTH_SHORT);
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

        String text = ResWrapper.getApplicationContext().getResources().getString(resId);
        UIHandler.getMainHandler().removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(ResWrapper.getApplicationContext(), text, Toast.LENGTH_SHORT);
        }
        UIHandler.getMainHandler().postDelayed(r, duration);

        mToast.show();
    }
}
