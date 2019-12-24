package com.kit.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.kit.utils.log.Zog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RomUtils {

    public class AvailableRomType {
        public static final int MIUI = 1;
        public static final int FLYME = 2;
        public static final int EMUI = 3;
        public static final int ANDROID_NATIVE = 4;
        public static final int NA = 5;
    }

    public static boolean isLightStatusBarAvailable() {
        if (isMIUIV6OrAbove() || isFlymeV4OrAbove() || isAndroidMOrAbove()) {
            return true;
        }
        return false;
    }

    public static int getAvailableRomType() {
        if (isMIUIV6OrAbove()) {
            return AvailableRomType.MIUI;
        }

        if (isFlymeV4OrAbove()) {
            return AvailableRomType.FLYME;
        }

        if (isEMUI()) {
            return AvailableRomType.EMUI;

        }

        if (isAndroidMOrAbove()) {
            return AvailableRomType.ANDROID_NATIVE;
        }


        return AvailableRomType.NA;
    }

    //Flyme V4的displayId格式为 [Flyme OS 4.x.x.xA]
    //Flyme V5的displayId格式为 [Flyme 5.x.x.x beta]
    private static boolean isFlymeV4OrAbove() {
        String displayId = Build.DISPLAY;
        if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
            String[] displayIdArray = displayId.split(" ");
            for (String temp : displayIdArray) {
                //版本号4以上，形如4.x.
                if (temp.matches("^[4-9]\\.(\\d+\\.)+\\S*")) {
                    return true;
                }
            }
        }
        return false;
    }

    //MIUI V6对应的versionCode是4
    //MIUI V7对应的versionCode是5
    private static boolean isMIUIV6OrAbove() {
        String miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code");
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                int miuiVersionCode = Integer.parseInt(miuiVersionCodeStr);
                if (miuiVersionCode >= 4) {
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }


    public static int getMIUIVersionCode() {
        String miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code");
        int miuiVersionCode = 0;
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                miuiVersionCode = Integer.parseInt(miuiVersionCodeStr);
                return miuiVersionCode;
            } catch (Exception e) {
            }
        }
        return miuiVersionCode;
    }

    public static boolean isEMUI() {
        String miuiVersionCodeStr = getSystemProperty("ro.build.version.emui");
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            return true;
        }
        return false;
    }


    public static boolean isSmartisan() {
        String miuiVersionCodeStr = getSystemProperty("ro.smartisan.version");
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            return true;
        }
        return false;
    }

    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";


    //Android Api 23以上
    private static boolean isAndroidMOrAbove() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }

    /**
     * 获取小米 rom 版本号，获取失败返回 -1
     *
     * @return miui rom version code, if fail , return -1
     */
    public static int getMiuiVersion() {
        String version = RomUtils.getSystemProperty("ro.miui.ui.version.name");
        if (version != null && !StringUtils.isEmpty(version)) {
            try {
                return Integer.parseInt(version.substring(1));
            } catch (Exception e) {
                Zog.i("get miui version code error, version : " + version);
                Zog.e(Log.getStackTraceString(e));
            }
        }
        return -1;
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }
}
