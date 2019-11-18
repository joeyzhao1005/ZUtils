package com.kit.app;



import androidx.annotation.DrawableRes;

import com.kit.app.application.AppMaster;
import com.kit.utils.StringUtils;

import java.io.File;

public class SourceWapper {


    public static String get(String uri) {
        if (StringUtils.isEmptyOrNullStr(uri)) {
            return uri;
        }
        if (uri.startsWith("file://")) {
            return uri.substring("file://".length(), uri.length());
        } else if (uri.startsWith("asset://")) {
            return uri.substring(("asset://" + AppMaster.getInstance().getApplicationId() + "/").length(), uri.length());
        } else if (uri.startsWith("res://")) {
            return uri.substring(("res://" + AppMaster.getInstance().getApplicationId() + "/").length(), uri.length());
        } else if (uri.startsWith("content://")) {
            return uri.substring(("content://" + AppMaster.getInstance().getApplicationId() + "/").length(), uri.length());
        }
        return uri;
    }


    public static String contentProvider(String assetsName) {
        return "content://" + AppMaster.getInstance().getApplicationId() + "/" + assetsName;
    }

    public static String res(@DrawableRes int drawableResId) {
        return "res://" + AppMaster.getInstance().getApplicationId() + "/" + drawableResId;
    }

    public static String assets(String assetsName) {
        return "asset://" + AppMaster.getInstance().getApplicationId() + "/" + assetsName;
    }

    public static String file(String fileFullPath) {
        return "file://" + fileFullPath;
    }

    public static String file(File file) {
        if (file == null) {
            return "file://";
        } else {
            return "file://" + file.getPath();
        }
    }


    public static String getType(String uri) {
        if (uri.startsWith("file://")) {
            return "file";
        } else if (uri.startsWith("asset://")) {
            return "asset";
        } else if (uri.startsWith("content://")) {
            return "contentProvider";
        } else if (uri.startsWith("res://")) {
            return "res";
        } else if (uri.endsWith(".gif") || uri.startsWith(".GIF")) {
            return "gif";
        } else if (uri.startsWith("http://") || uri.startsWith("https://")) {
            return "http";
        } else {
            return "http";
        }
    }
}
