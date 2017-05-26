package com.kit.utils;


import android.content.res.ColorStateList;
import android.graphics.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {


    /**
     * 不同状态时其颜色
     */
    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, normal};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }


    /**
     * 得到更深的颜色
     *
     * @param color
     * @return
     */
    public static int getDarkerColor(int color, float range) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv
        // make darker

        hsv[1] = hsv[1] + range; // more saturation
        hsv[2] = hsv[2] - range; // less brightness
//        hsv[1] = hsv[1] + 0.1f; // more saturation
//        hsv[2] = hsv[2] - 0.1f; // less brightness
        int darkerColor = Color.HSVToColor(hsv);
        return darkerColor;
    }


    /**
     * 得到更浅的颜色
     *
     * @param color
     * @param range
     * @return
     */
    public static int getLighterColor(int color, float range) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv

        hsv[1] = hsv[1] - range; // less saturation
        hsv[2] = hsv[2] + range; // more brightness
//        hsv[1] = hsv[1] - 0.1f; // less saturation
//        hsv[2] = hsv[2] + 0.1f; // more brightness
        int lightColor = Color.HSVToColor(hsv);
        return lightColor;
    }


    public static String toHex(int a, int r, int g, int b) {
        return toBrowserHexValue(a) + toBrowserHexValue(r)
                + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    public static int toColor(int r, int g, int b) {

        return Color.rgb(r, g, b);

    }

    public static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(
                Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }

    /**
     * 转化成浏览器中常用的，形如 #000000 这样的字符串
     *
     * @param color
     * @return
     */
    public static String toBrowserColor(int color) {
        int red = getRed(color);
        int green = getGreen(color);
        int blue = getBlue(color);

        String redStr = ((red + "").length() < 2 ? red + "0" : red + "");
        String greenStr = ((green + "").length() < 2 ? green + "0" : green + "");
        String blueStr = ((blue + "").length() < 2 ? blue + "0" : blue + "");

        return "#" + redStr + blueStr + greenStr;
    }

    /**
     * 转化成浏览器中常用的，形如 #000000 这样的字符串
     *
     * @param color
     * @return
     */
    public static int getRed(int color) {
        int red = (color & 0xff0000) >> 16;
        return red;
    }

    /**
     * 转化成浏览器中常用的，形如 #000000 这样的字符串
     *
     * @param color
     * @return
     */
    public static int getGreen(int color) {
        int green = (color & 0x00ff00) >> 8;
        return green;
    }

    /**
     * 转化成浏览器中常用的，形如 #000000 这样的字符串
     *
     * @param color
     * @return
     */
    public static int getBlue(int color) {
        int blue = (color & 0x0000ff);
        return blue;
    }


    /**
     * 将纯色转化为透明色
     *
     * @param color
     * @param alpha
     * @return
     */
    public static int toAlpha(int color, int alpha) {
        int r = getRed(color);
        int g = getGreen(color);
        int b = getBlue(color);
        return Color.argb(alpha, r, g, b);
    }

    /**
     * 将十六进制 颜色代码 转换为 int
     *
     * @return
     */
    public static int hex2Color(String color) {

        if (StringUtils.isEmptyOrNullStr(color)) {
            return 0xff000000;
        }
        String reg = "#[a-f0-9A-F]{6,8}";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(color);
        boolean isMatch = matcher.matches();
        if (isMatch) {
            int c = Color.parseColor(color);
            return c;
        }
        return 0xff000000;
    }

}