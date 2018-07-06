package com.kit.utils;

public class ValueOf {



    public static float toFloat(Object o) {
        float value = 0;
        try {
            value = Float.valueOf(o.toString());
        } catch (Exception e) {
        }

        return value;
    }

    public static Float toFloat(Object o, float defaultValue) {
        float value;
        try {
            value = Float.valueOf(o.toString());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }


    public static double toDouble(Object o) {
        double value = 0;
        try {
            value = Double.valueOf(o.toString());
        } catch (Exception e) {
        }

        return value;
    }

    public static double toDouble(Object o, double defaultValue) {
        double value;
        try {
            value = Double.valueOf(o.toString());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o) {
        long value = 0;
        try {
            value = Long.valueOf(o.toString());
        } catch (Exception e) {
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        long value;
        try {
            value = Long.valueOf(o.toString());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }


    public static int toInt(Object o, int defaultValue) {
        int value;
        try {
            value = Integer.valueOf(o.toString());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static int toInt(Object o) {
        int value = 0;
        try {
            value = Integer.valueOf(o.toString());
        } catch (Exception e) {
        }

        return value;
    }



    public static String toString(Object o, String defaultValue) {
        String value;
        try {
            value = String.valueOf(o);
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static String toString(Object o) {
        String value = "";
        try {
            value = String.valueOf(o);
        } catch (Exception e) {
        }

        return value;
    }


}
