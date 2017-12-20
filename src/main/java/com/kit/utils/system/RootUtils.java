package com.kit.utils.system;

import android.content.Context;

import com.kit.utils.StringUtils;
import com.kit.utils.log.ZogUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;

public class RootUtils {

    /**
     * 结束进程
     *
     * @return 返回数据表明是否有root权限
     */
    public static boolean killProcess(String packageName) {
        String cmd = "am force-stop " + packageName + " \n";
        try {
            return execCmdWithRoot(cmd);
        } catch (Exception e) {
            ZogUtils.showException(e);
            return false;
        }
    }


    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     *
     * @return 应用程序是/否获取Root权限
     */
    public static boolean upgradeRootPermission(Context context) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + context.getPackageCodePath();
            process = Runtime.getRuntime().exec("su"); // 切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null)
                    process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }


    /**
     * 判断当前应用是否有 root 权限
     *
     * @return
     */
    public static synchronized boolean isRootPermission() {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            int exitValue = process.waitFor();
            if (exitValue == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            ZogUtils.d("Unexpected error - Here is what I know: "
                    + e.getMessage());
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 模拟按键
     *
     * @param key KeyEvent.KEYCODE_MENU
     */
    public static boolean simulateKey(int key) {
        try {
            String keyCommand = "input keyevent " + key;
            return execCmdWithRoot(keyCommand);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean execCmdWithRoot(String cmdStr) throws Exception {
//        if (null == cmdStr || "".equals(cmdStr)) {
//            return false;
//        }
//        Process process = null;
//        String[] cmds = new String[]{cmdStr};
//        try {
//            process = Runtime.getRuntime().exec("su");
//
//            DataOutputStream os = new DataOutputStream(process.getOutputStream());
//            for (String cmd : cmds) {
//                os.write((cmd + "\n").getBytes());
//            }
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            ZogUtils.showException(e);
//        }
//
//        if (process != null) {
//            try {
//                int status = process.waitFor();
//                process.getOutputStream().close();
//                process.getErrorStream().close();
//                process.getInputStream().close();
//                //这里是关键代码，其实只有status为1的时候是没有权限，这里个人直接把所有运行shell命令的异常都归为失败
//
//                return 0 == status;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(cmdStr, true);
        if (!StringUtils.isEmptyOrNullStr(commandResult.errorMsg)) {
            throw new Exception(commandResult.errorMsg);
        } else {
            return true;
        }
    }

    /**
     * 判断手机是否root，会弹出root请求框
     */
    public static boolean isRootDevice() {
        String cmdStr = "uiautomator dump";
        try {
            return execCmdWithRoot(cmdStr);
        } catch (Exception e) {
            ZogUtils.showException(e);
            return false;
        }
    }

    /**
     * 判断手机是否root，不弹出root请求框<br/>
     */
    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4();
    }


    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }


    private static boolean checkRootMethod2() {
        return new File("/system/app/Superuser.apk").exists()
                || new File("/system/app/superuser.apk").exists()
                || new File("/system/app/supersu.apk").exists()
                || new File("/system/app/SuperSu.apk").exists();
    }


    private static boolean checkRootMethod3() {
        String[] paths = {"/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }


    private static boolean checkRootMethod4() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }


}
