package com.kit.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * 获取设备IP和mac地址工具类
 *
 * @author zhoud
 * @date 2014-8-2 下午13:25
 */
public class NetAddressUtil {


	/**
	 * 获取设备mac地址
	 * @param mContext
	 * @return 设备mac地址
	 */
	public static  String getLocalMacAddress(Context mContext) {
		WifiManager wifi = (WifiManager) mContext.getApplicationContext()
				.getSystemService(Context.WIFI_SERVICE);
		@SuppressLint("MissingPermission") WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}
}
