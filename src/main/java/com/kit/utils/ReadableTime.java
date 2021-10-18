package com.kit.utils;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReadableTime {

    @SuppressLint("SimpleDateFormat")
    public static String readableDate(String createTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long create = 0;
        try {
            create = sdf.parse(createTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return readableDate(create, sdf);
    }

    @SuppressLint("SimpleDateFormat")
    public static String readableDateWeek(long time, String format) {

        if (format == null || format.equals("")) {
            format = DateUtils.WEEK_SIMPLE;
        }
        try {
            return readableDate(time, new SimpleDateFormat(format));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static String readableDate(long time, @Nullable SimpleDateFormat format) {

        if (format == null) {
            format = new SimpleDateFormat("MM-dd HH:mm");
        }
        try {
            String ret = "";
            Date sd = new Date(time);

            Calendar now = Calendar.getInstance();
            long ms = 1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600
                    + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));// 1天毫秒数

            long hms = (1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600
                    + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND))) / 24;// 1小时毫秒数

            long ms_now = now.getTimeInMillis();
            long sub = ms_now - time;
            if (sub > 0) {
                if (sub < ms) {
                    // System.out.println("hms: " + hms);
                    if (sub < hms) {
                        // System.out.println();
                        long mms = (sub / 1000) / 60;
                        if (mms == 0) {
                            ret = "刚刚";
                        } else {
                            ret = mms + "分钟前";
                        }
                    } else {
                        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                        ret = "今天 " + sdf2.format(sd);
                        // ret = "今天";
                    }
                } else if (sub < (ms + 24 * 3600 * 1000)) {

                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                    ret = "昨天 " + sdf2.format(sd);

                } else if (sub < (ms + 24 * 3600 * 1000 * 2)) {
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                    ret = "前天 " + sdf2.format(sd);

                } else {

                    ret = format.format(sd);

                    // ret = "更早";
                }
            } else {
                sub = Math.abs(sub);
                if (sub < ms) {
                    if (sub < hms) {
                        long mms = ((ms / 24) / 1000) / 60;
                        ret = mms + "分钟后";
                    } else {
                        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                        ret = "今天 " + sdf2.format(sd);
                        // ret = "今天";
                    }
                } else if (sub < (ms + 24 * 3600 * 1000)) {
                    ret = "明天";
                } else if (sub < (ms + 24 * 3600 * 1000 * 2)) {
                    ret = "后天";
                } else {

                    ret = format.format(sd);
                    // ret = "更晚";
                }

            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
