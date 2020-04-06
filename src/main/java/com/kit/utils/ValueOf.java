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

    public static long toLong(Object o, long defaultValue) {
        long value = 0;
        try {
            String s = o.toString();
            s = s.trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public static long toLong(Object o) {
        return toLong(o, 0);
    }


    public static short toShort(Object o) {
        short value = 0;
        try {
            value = Short.valueOf(o.toString());
        } catch (Exception e) {
        }

        return value;
    }

    public static short toShort(Object o, short defaultValue) {
        short value;
        try {
            value = Short.valueOf(o.toString());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }


    public static byte toByte(Object o) {
        byte value = 0;
        try {
            value = Byte.valueOf(o.toString());
        } catch (Exception e) {
        }

        return value;
    }

    public static boolean toBoolean(Object o, boolean defaultValue) {
        boolean value = defaultValue;
        try {
            String s = o.toString();
            s = s.trim();
            value = Boolean.valueOf(s);
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static boolean toBoolean(Object o) {
        return toBoolean(o, false);
    }


    public static int toInt(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        int value;
        try {
            String s = o.toString();
            s = s.trim();
            if (s.contains(".")) {
                value = Integer.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Integer.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static int toInt(Object o) {
        return toInt(o, 0);
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
