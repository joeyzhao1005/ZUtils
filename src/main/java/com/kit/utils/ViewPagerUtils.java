package com.kit.utils;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import java.lang.reflect.Field;

public class ViewPagerUtils {

    /**
     * 通过反射来修改 ViewPager的mScroller属性
     */

    public static void smoothScroll(Context context, ViewPager viewPager, int duration) {
        try {
            Class clazz = Class.forName("android.support.v4.view.ViewPager");
            Field f = clazz.getDeclaredField("mScroller");
            FixedSpeedScroller fixedSpeedScroller = new FixedSpeedScroller(context, new LinearOutSlowInInterpolator());
            fixedSpeedScroller.setmDuration(duration);
            f.setAccessible(true);
            f.set(viewPager, fixedSpeedScroller);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
