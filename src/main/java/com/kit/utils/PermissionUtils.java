package com.kit.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;

import com.kit.app.application.AppMaster;

public class PermissionUtils {


    public static boolean check(@NonNull String permission) {
        return check(AppMaster.INSTANCE.getAppContext(), permission);
    }

    public static boolean check(Context context, @NonNull String permission) {

        int result = PermissionChecker.checkSelfPermission(context, permission);

        switch (result) {
            case PermissionChecker.PERMISSION_GRANTED:
                return true;

            default:
                return false;
        }

    }

    public static void check(@NonNull String permission, PermissionCallback callback) {
         check(AppMaster.INSTANCE.getAppContext(), permission, callback);
    }

    public static void check(Context context, @NonNull String permission, PermissionCallback callback) {

        int result = PermissionChecker.checkSelfPermission(context, permission);

        switch (result) {
            case PermissionChecker.PERMISSION_GRANTED:
                if (callback != null) {
                    callback.onCheck(true);
                }
                break;

            case PermissionChecker.PERMISSION_DENIED:
            case PermissionChecker.PERMISSION_DENIED_APP_OP:
            default:
                if (callback != null) {
                    callback.onCheck(false);
                }
                break;
        }

    }
}



