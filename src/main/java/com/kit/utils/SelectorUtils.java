package com.kit.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by Zhao on 2017/3/15.
 */

public class SelectorUtils {

    public static StateListDrawable getStateListDrawable(int normalColor, int focusColor, int pressedColor) {


        StateListDrawable sd = new StateListDrawable();

        ColorDrawable normal = new ColorDrawable(normalColor);
        ColorDrawable focus = new ColorDrawable(focusColor);
        ColorDrawable pressed = new ColorDrawable(pressedColor);

        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        sd.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        sd.addState(new int[]{android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{android.R.attr.state_enabled}, normal);
        sd.addState(new int[]{}, normal);


        return sd;
    }
}
