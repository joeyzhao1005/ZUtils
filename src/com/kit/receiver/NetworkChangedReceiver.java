package com.kit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.kit.app.enums.ConnectedType;
import com.kit.utils.log.ZogUtils;

public class NetworkChangedReceiver extends BroadcastReceiver {
    State wifiState = null;
    State mobileState = null;
    public static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub  
        if (ACTION.equals(intent.getAction())) {
            //获取手机的连接服务管理器，这里是连接管理器类  
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = cm.getActiveNetworkInfo();
            if (networkinfo == null) {
                ZogUtils.i("网络全部断开!");
                onDisconnected(ConnectedType.ALL);
                return;
            }
            //WIFI
            if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
                wifiState = networkinfo.getState();
                if (wifiState != null) {
                    if (State.CONNECTED != wifiState) {
                        ZogUtils.i("无线网络连接断开！");
                        onDisconnected(ConnectedType.WIFI);

                    } else if (State.CONNECTED == wifiState) {
                        ZogUtils.i("无线网络连接成功！");
                        onConnected(ConnectedType.WIFI);
                    }
                } else {
                    ZogUtils.i("无法获取网络状态");
                }
            } else if (networkinfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                //MOBILE
                mobileState = networkinfo.getState();

                if (mobileState != null) {
                    if (State.CONNECTED != mobileState) {
                        ZogUtils.i("无线网络连接断开！");
                        onDisconnected(ConnectedType.MOBILE);
                    } else if (State.CONNECTED == mobileState) {
                        ZogUtils.i("手机网络连接成功！");
                        onConnected(ConnectedType.MOBILE);
                    }
                } else {
                    ZogUtils.i("无法获取网络状态");
                }
            }
        }
    }


    /**
     * 断开链接
     * @param connectedType
     */
    protected void onDisconnected(String connectedType) {
    }

    /**
     * 链接
     * @param connectedType
     */
    protected void onConnected(String connectedType) {
    }

}  
