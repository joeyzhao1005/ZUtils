package com.kit.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.kit.app.UIHandler;
import com.kit.app.application.AppMaster;
import com.kit.utils.log.Zog;

/**
 * @author joeyzhao
 */
public class ToastUtils {

    public static void l(String msg) {
        try {
            UIHandler.run(() ->
                    Toast.makeText(AppMaster.getInstance().getAppContext(), msg, Toast.LENGTH_LONG).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void l(int msgStringId) {
        try {
            UIHandler.run(() ->
                    Toast.makeText(AppMaster.getInstance().getAppContext()
                            , AppMaster.getInstance().getAppContext().getResources().getString(msgStringId)
                            , Toast.LENGTH_LONG).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void l(Context context, int msgStringId) {
        try {
            UIHandler.run(() ->
                    Toast.makeText(context, context.getResources().getString(msgStringId), Toast.LENGTH_LONG).show()
            );

        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void l(Context context, String msgString) {
        try {
            UIHandler.run(() ->

                    Toast.makeText(context, msgString, Toast.LENGTH_LONG).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void s(@Nullable String msg) {
        if (msg == null) {
            return;
        }
        try {
            UIHandler.run(() ->
                    Toast.makeText(AppMaster.getInstance().getAppContext(), msg, Toast.LENGTH_SHORT).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void s(@StringRes int msgStringId) {

        try {
            UIHandler.run(() ->
                    Toast.makeText(AppMaster.getInstance().getAppContext()
                            , AppMaster.getInstance().getAppContext().getResources().getString(msgStringId)
                            , Toast.LENGTH_SHORT).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


    public static void s(final Context context, @StringRes int msgStringId) {
        final Context contxt;
        if (context == null) {
            contxt = AppMaster.getInstance().getAppContext();
        } else {
            contxt = context;
        }
        try {
            UIHandler.run(() ->
                    Toast.makeText(contxt, contxt.getResources().getString(msgStringId), Toast.LENGTH_SHORT).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }

    public static void s(Context context, String msgString) {
        try {
            UIHandler.run(() ->
                    Toast.makeText(context, msgString, Toast.LENGTH_SHORT).show()
            );
        } catch (Exception e) {
            Zog.showException(e);
        }
    }


}
