package com.kit.utils;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.util.TypedValue;

public class DensityUtils {

    public static float sp2Px(int dimensionId) {
        return (int) Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                ResWrapper.getInstance().getResources().getDimension(dimensionId)
                , ResWrapper.getInstance().getResources().getDisplayMetrics()));
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = getScale(context);
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dipRes2px(Context context, @DimenRes int dpResId) {
        final float scale = getScale(context);
        return (int) (context.getResources().getDimension(dpResId) / scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = getScale(context);
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxRes2dip(Context context, @DimenRes int pxResId) {
        final float scale = getScale(context);
        return (int) (context.getResources().getDimensionPixelSize(pxResId) / scale + 0.5f);
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

    public static float getScale(Context context) {
        if (context == null) {
            return getScale();
        }
        return context.getResources().getDisplayMetrics().density;
    }

    public static float getScale() {
        return ResWrapper.getInstance().getResources().getDisplayMetrics().density;
    }

}