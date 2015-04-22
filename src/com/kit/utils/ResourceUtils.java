package com.kit.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Zhao on 14-10-1.
 */
public class ResourceUtils extends AssetsUtils {

    public static final String RESOURCE_TYPE_DRAWABLE = "drawable";
    public static final String IDS = "id";
    public static final String STRING = "string";
    public static final String LAYOUT = "layout";
    public static final String COLOR = "color";


    public static int getDrawableId(Context context, String resourceName) {
        Resources resources = context.getResources();
        int indentify = resources.getIdentifier(context.getPackageName() + ":drawable/" + resourceName, null, null);

        return indentify;
    }

    /**
     * 根据资源的名字获取它的ID
     *
     * @param name    要获取的资源的名字
     * @param defType 资源的类型，如drawable, string 。。。
     * @return 资源的id
     */
    public static int getResId(Context context, String name, String defType) {

//        ZogUtils.printLog(ResourceUtils.class, "context:" + context);
        Resources resources = context.getApplicationContext().getResources();

        String packageName = context.getApplicationInfo().packageName;
        return resources.getIdentifier(name, defType, packageName);
    }

}
