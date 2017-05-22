package com.kit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.kit.utils.log.ZogUtils;

public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private String action = null;


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null)
            return;

        action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            // 开屏
            ZogUtils.i("开屏");
            onScreenOn(intent);
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            // 锁屏
            ZogUtils.i("锁屏");
            onScreenOff(intent);

        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            // 解锁
            ZogUtils.i("解锁");
            onScreenUnlock(intent);

        } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
            ZogUtils.i("锁屏");
            onScreenLock(intent);

            // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
            // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        }
    }


    public void onScreenOn(Intent intent) {

    }


    public void onScreenOff(Intent intent) {

    }


    public void onScreenUnlock(Intent intent) {

    }

    public void onScreenLock(Intent intent) {

    }

    public void registerScreenBroadcastReceiver(Context context, ScreenBroadcastReceiver screenBroadcastReceiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(screenBroadcastReceiver, filter);
    }

    public void unregisterScreenBroadcastReceiver(Context context, ScreenBroadcastReceiver screenBroadcastReceiver) {
        context.unregisterReceiver(screenBroadcastReceiver);
    }
}
