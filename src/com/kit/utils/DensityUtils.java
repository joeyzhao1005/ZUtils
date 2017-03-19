package com.kit.utils;

import android.content.Context;

public class DensityUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = getScale(context);
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = getScale(context);
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = getScale();
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = getScale();
        return (int) (pxValue / scale + 0.5f);
    }

    public static float getScale(Context context){
        final float scale = context.getResources().getDisplayMetrics().density;

        return scale;
    }

    public static float getScale(){
        final float scale = ResWrapper.getInstance().getResources().getDisplayMetrics().density;

        return scale;
    }

}