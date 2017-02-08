package com.kit.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

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
        TelephonyManager telephonyManager = (TelephonyManager) ResWrapper.getInstance().getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (TelephonyManager.CALL_STATE_OFFHOOK == telephonyManager.getCallState()
                || TelephonyManager.CALL_STATE_RINGING == telephonyManager.getCallState()) {
            calling = true;
        }
        return calling;
    }



}
