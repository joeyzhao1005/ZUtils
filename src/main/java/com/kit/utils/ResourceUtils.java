package com.kit.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

import com.kit.app.application.AppMaster;

/**
 * Created by Zhao on 14-10-1.
 */
public class ResourceUtils {

    public static final String RESOURCE_TYPE_DRAWABLE = "drawable";
    public static final String IDS = "id";
    public static final String STRING = "string";
    public static final String LAYOUT = "layout";
    public static final String COLOR = "color";


//    public static int getDrawableId(Context context, String resourceName) {
//        Resources resources = context.getResources();
//        int indentify = resources.getIdentifier(context.getPackageName() + ":drawable/" + resourceName, null, null);
//
//        return indentify;
//    }


    public static void setSelectableItemBackgroundBorderless(Context context, View view) {
        if (context == null) {
            context = AppMaster.INSTANCE.getAppContext();
        } else {
            context = context.getApplicationContext();
        }

        TypedValue typedValue = new TypedValue();
        // I used getActivity() as if you were calling from a fragment.
        // You just want to call getTheme() on the current activity, however you can get it
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true);
        // it's probably a good idea to check if the color wasn't specified as a resource
        if (typedValue.resourceId != 0) {
            view.setBackgroundResource(typedValue.resourceId);
        } else {
            // this should work whether there was a resource id or not
            view.setBackgroundColor(typedValue.data);
        }
    }

    /**
     * 根据资源的名字获取它的ID
     *
     * @param name    要获取的资源的名字
     * @param defType 资源的类型，如drawable, string 。。。
     * @return 资源的id
     */
    public static int getResId(Context context, String name, String defType) {

        if (context == null) {
            context = AppMaster.INSTANCE.getAppContext();
        } else {
            context = context.getApplicationContext();
        }

//        Zog.i(ResourceUtils.class, "context:" + context);
        Resources resources = context.getResources();

        String packageName = context.getApplicationInfo().packageName;

//        Zog.i(ResourceUtils.class, "packageName:" + packageName);

        return resources.getIdentifier(name, defType, packageName);
    }


    /**
     * 根据资源的名字获取它的ID
     *
     * @param name 要获取的资源的名字
     * @return 资源的id
     */
    public static int getStringId(Context context, String name) {
        return getResId(context, name, "string");
    }


    /**
     * 根据资源的名字获取它的ID
     *
     * @param name 要获取的资源的名字
     * @return 资源的id
     */
    public static int getLayoutId(Context context, String name) {
        return getResId(context, name, "layout");
    }

    /**
     * 根据资源的名字获取它的ID
     *
     * @param name 要获取的资源的名字
     * @return 资源的id
     */
    public static int getViewId(Context context, String name) {
        return getResId(context, name, "id");
    }

    /**
     * 根据资源的名字获取它的ID
     *
     * @param name 要获取的资源的名字
     * @return 资源的id
     */
    public static int getDrawableId(Context context, String name) {
        return getResId(context, name, "drawable");
    }


    /**
     * 根据资源的名字获取它的ID
     *
     * @param name 要获取的资源的名字
     * @return 资源的id
     */
    public static int getAnimId(Context context, String name) {
        return getResId(context, name, "anim");
    }

    /**
     * 根据资源的名字获取它的ID
     *
     * @param name 要获取的资源的名字
     * @return 资源的id
     */
    public static int getColorId(Context context, String name) {
        return getResId(context, name, "color");
    }


    /**
     * 根据资源的名字获取它的ID
     *
     * @param name 要获取的资源的名字
     * @return 资源的id
     */
    public static int getStyleId(Context context, String name) {
        return getResId(context, name, "style");
    }


}
