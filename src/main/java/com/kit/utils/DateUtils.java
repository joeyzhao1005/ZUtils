package com.kit.utils;

import android.annotation.SuppressLint;
import android.text.format.Time;

import com.kit.utils.log.Zog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressLint("SimpleDateFormat")
public class DateUtils {

    public static String YEAR_MONTH_DAY_HOUR_MIN_SEC = "yyyy-MM-dd HH:mm:ss";

    public static String YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static String MONTH_DAY = "MM-dd";

    public static String MONTH_DAY_HOUR_MIN = "MM-dd HH:mm";

    public static String YEAR_MONTH_DAY_HOUR_MIN = "yyyy-MM-dd HH:mm";

    public static String WEIBO_DATE = "EEE MMM d HH:mm:ss Z yyyy";


    public static String YEAR_MONTH_DAY_WEEK_CHINA = "yyyy年MM月dd日 EEE";
    public static String MONTH_DAY_WEEK_CHINA = "MM月dd日 EEE";
    public static String MONTH_DAY_WEEK = "MM dd";
    public static String MONTH_DAY_WEEK2 = "MM月dd日";


    public static String HOUR_MIN = "HH:mm";
    public static String WEEK_SIMPLE = "EEE";
    public static String WEEK_FULL = "EEEE";

    /**
     * 获取随机日期
     *
     * @param beginDate 起始日期，格式为：yyyy-MM-dd
     * @param endDate   结束日期，格式为：yyyy-MM-dd
     * @return
     */

    public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    public static Date getDateFormLong(long dateLong) {

        Date sd = new Date(dateLong);
        return sd;
    }

    /**
     * 根据字符串的时间转换成Date
     *
     * @param date
     * @param format
     * @param locale
     * @return
     */
    public static Date getDate4StringFormat(String date, String format, Locale locale) {

        if (locale == null) {
            locale = Locale.US;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        long create = 0;
        try {
            create = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date sd = new Date(create);
        return sd;

    }

    public static long getDate2Long(Date date) {
        long l = date.getTime();

        return l;
    }

    public static long getLongDate4Net(String date) {

        long create = 0;
        try {

            String[] dateStr = date.split("\\+");

            // System.out.println("dateStr[0]: " + dateStr[0]);

            String[] dateStr2 = dateStr[0].split("\\)");
            String ss = dateStr2[0].substring(6, dateStr2[0].length());

            // System.out.println("dateStr2[0]: " + dateStr2[0] + "  ss:  " +
            // ss);

            create = Long.parseLong(ss);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return create;
    }

    public static String getDate4LongFormat(long dateLong, String format) {

        Date sd = null;
        if ((dateLong + "").length() == 10) {
            sd = new Date(dateLong * 1000);
        } else {
            sd = new Date(dateLong);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String c = sdf.format(sd);

        return c;
    }


    public static String getNetDate4Net(String createTime, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long create = 0;
        try {
            create = sdf.parse(createTime).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "/Date(" + create + "+0800)/";
    }

    public static String getDate4Net(String date, String format) {

        long create = 0;
        String c = "";
        try {

            String[] dateStr = date.split("\\+");

            // System.out.println("dateStr[0]: " + dateStr[0]);

            String[] dateStr2 = dateStr[0].split("\\)");
            String ss = dateStr2[0].substring(6, dateStr2[0].length());

            // System.out.println("dateStr2[0]: " + dateStr2[0] + "  ss:  " +
            // ss);

            create = Long.parseLong(ss);

            Date sd = new Date(create);

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            c = sdf.format(sd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static String getDateFormat(long millions, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(millions));
    }


    public static String getDateFormat(Date date, String format) {
        // dateStrWithZone = 1351582444220+0800;

        SimpleDateFormat dateformat1 = new SimpleDateFormat(format);
        String strDate = dateformat1.format(date);
        // System.out.println("DateFormat: " + strDate);

        return strDate;
    }

    public static long getDate2Long(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long create = 0;
        try {
            create = sdf.parse(date).getTime();
        } catch (ParseException e) {
//			e.printStackTrace();
            Zog.showException(e);
        }

        return create;
    }


    public static Date getCurrDate() {
        return new Date(System.currentTimeMillis());
    }

    public static String getCurrDateFormat(String format) {
        // dateStrWithZone = 1351582444220+0800;
        if (format == null || format.equals("")) {
            format = "";
        }

        long dateLong = System.currentTimeMillis();

        Date date = new Date(dateLong);

        SimpleDateFormat dateformat1 = new SimpleDateFormat(format);
        String strDate = dateformat1.format(date);
        // System.out.println("DateFormat: " + strDate);

        return strDate;
    }


    public static long getCurrDateLong() {
        return getCurrDateLong(TimeUnit.MILLISECONDS);
    }

    public static long getCurrDateLong(TimeUnit timeUnit) {

        if (timeUnit == TimeUnit.MILLISECONDS) {
            return System.currentTimeMillis();
        } else if (timeUnit == TimeUnit.SECONDS) {
            return System.currentTimeMillis() / 1000;
        } else if (timeUnit == TimeUnit.MINUTES) {
            return System.currentTimeMillis() / 1000 / 60;
        } else {
            return System.currentTimeMillis();
        }


    }


    /**
     * @return true if the supplied when is today else false
     */
    public static boolean isToday(long when) {
        return (DateUtils.getCurrDateFormat(DateUtils.YEAR_MONTH_DAY_WEEK_CHINA)
                .equals(DateUtils.getDateFormat(when, DateUtils.YEAR_MONTH_DAY_WEEK_CHINA)));
    }

}
