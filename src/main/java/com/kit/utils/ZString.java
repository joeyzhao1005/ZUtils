package com.kit.utils;

public class ZString {

    public ZString p(Object s) {
        sb = sb.append(ValueOf.toString(s));
        return this;
    }

    public ZString p(CharSequence s) {
        sb = sb.append(s);
        return this;
    }

    public ZString p(char[] values) {
        sb = sb.append(values);
        return this;
    }

    public ZString p(char value) {
        sb = sb.append(value);
        return this;
    }

    public ZString p(float value) {
        sb.append(value);
        return this;
    }

    public ZString p(double value) {
        sb.append(value);
        return this;
    }

    public ZString p(long value) {
        sb.append(value);
        return this;

    }

    public ZString p(int value) {
        sb.append(value);
        return this;
    }


    public String string() {
        String s = sb.toString();
        sb = null;
        return s;
    }

    @Override
    public String toString() {
        return string();
    }

    private ZString() {
    }

    private StringBuilder sb;

    public static ZString get() {
        ZString zString = new ZString();
        zString.sb = new StringBuilder();
        return zString;
    }
}
