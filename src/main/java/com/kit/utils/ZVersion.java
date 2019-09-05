package com.kit.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

public class ZVersion {
	public static final boolean ATLEAST_OREO_MR1 = Build.VERSION.SDK_INT >=Build.VERSION_CODES.O_MR1;

	public static final boolean ATLEAST_OREO = Build.VERSION.SDK_INT >=Build.VERSION_CODES.O;

	public static final boolean ATLEAST_NOUGAT_MR1 = Build.VERSION.SDK_INT >=Build.VERSION_CODES.N_MR1;
	public static final boolean ATLEAST_NOUGAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;

	public static final boolean ATLEAST_MARSHMALLOW = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

	public static final boolean ATLEAST_LOLLIPOP_MR1 =
			Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;

	public static final boolean ATLEAST_LOLLIPOP =
			Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

	public static final boolean ATLEAST_KITKAT =
			Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	public static final boolean ATLEAST_JB_MR1 =
			Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;

	public static final boolean ATLEAST_JB_MR2 =
			Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;

	/**  
	* 返回当前程序版本名  
	*/   
	public static String getAppVersionName(Context context) {   
	    String versionName = "";   
	    try {   
	        // ---get the package info---   
	        PackageManager pm = context.getPackageManager();   
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);   
	        versionName = pi.versionName; 
	
	        if (versionName == null || versionName.length() <= 0) {   
	            return "";   
	        }   
	    } catch (Exception e) {   
	        Log.e("VersionInfo", "Exception", e);   
	    }   
	    return versionName;   
	}  

	
	public static int getAppVersionCode(Context context) {   
	    int versionCode = 0;   
	    try {   
	        // ---get the package info---   
	        PackageManager pm = context.getPackageManager();   
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);   
	        versionCode = pi.versionCode; 
 
	    } catch (Exception e) {   
	        Log.e("VersionInfo", "Exception", e);   
	    }   
	    return versionCode;   
	}  

	
	
	
	

}
