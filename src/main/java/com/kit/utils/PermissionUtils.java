package com.kit.utils;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;

import com.kit.app.application.AppMaster;

public class PermissionUtils {

    private void test() {
        PermissionUtils.check(AppMaster.getInstance().getAppContext(), Manifest.permission.VIBRATE, new PermissionCallback() {
            @Override
            public void granted() {

            }

            @Override
            public void denied() {

            }
        });

//        PermissionUtils.check(AppMaster.getInstance().appContext, Manifest.permission.VIBRATE, object : PermissionCallback() {
//            override fun granted() {
//                super.granted()
//                Zog.d("tttt")
//            }
//        })
    }

    public static void check(Context context, @NonNull String permission, PermissionCallback callback) {

        int result = PermissionChecker.checkSelfPermission(context, permission);

        switch (result) {
            case PermissionChecker.PERMISSION_GRANTED:
                if (callback != null) {
                    callback.granted();
                }
                break;

            case PermissionChecker.PERMISSION_DENIED:
            case PermissionChecker.PERMISSION_DENIED_APP_OP:
                if (callback != null) {
                    callback.denied();
                }
                break;
        }

    }
}



