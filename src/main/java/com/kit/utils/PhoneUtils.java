package com.kit.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.kit.app.application.AppMaster;
import com.kit.utils.log.Zog;

/**
 * Created by Zhao on 16/9/1.
 */

public class PhoneUtils {


    /**
     * 是否正在通话中
     * @return
     */
    public static boolean isTelephonyCalling() {
        boolean calling = false;
        TelephonyManager telephonyManager = (TelephonyManager) AppMaster.INSTANCE.getAppContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (TelephonyManager.CALL_STATE_OFFHOOK == telephonyManager.getCallState()
                || TelephonyManager.CALL_STATE_RINGING == telephonyManager.getCallState()) {
            calling = true;
        }
        return calling;
    }


    @SuppressLint("MissingPermission")
    public static void mkCall(String strPhone) {

        if (ContextCompat.checkSelfPermission(AppMaster.INSTANCE.getAppContext()
                , Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Context context = AppMaster.INSTANCE.getAppContext();
        Uri uri = Uri.parse("tel:" + strPhone);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);// 注意：call是直接就打出去了，dial是经过系统的确定才能打
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Zog.showException(e);
        }

    }

    public static void mkDail(Context context, String strPhone) {
        Uri uri = Uri.parse("tel:" + strPhone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);// 注意：call是直接就打出去了，dial是经过系统的确定才能打
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Zog.showException(e);
        }


    }


}
