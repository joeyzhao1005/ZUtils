package com.kit.utils.log;

import com.kit.utils.StringUtils;

public class DebugInfo extends Exception {
    public DebugInfo() {
        super();
    }

    public String className() {
        StackTraceElement[] trace = getStackTrace();
        if (trace == null || trace.length < 1) {
            return null;
        }

        StackTraceElement stackTraceElement = null;

        for (StackTraceElement e : trace) {
            if (!ZogUtils.class.getName().equals(e.getClassName())) {
                stackTraceElement = e;
                break;
            }
        }

        if (stackTraceElement == null) {
            return null;
        }

        return stackTraceElement.getClassName();
    }

    public String simpleClassName() {
        String className = className();
        if (!StringUtils.isEmptyOrNullStr(className)) {
            className = className.substring(className.lastIndexOf(".") + 1);
        }

        return className;
    }

    public int line() {
        StackTraceElement[] trace = getStackTrace();
        if (trace == null || trace.length <= 1) {
            return -1;
        }
        return trace[1].getLineNumber();
    }

    public String fun() {
        StackTraceElement[] trace = getStackTrace();
        if (trace == null || trace.length <= 1) {
            return "";
        }
        return trace[1].getMethodName();
    }


    @Override
    public String toString() {
        return "" + line() + "|" + fun();
    }
}