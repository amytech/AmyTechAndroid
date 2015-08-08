package com.amytech.android.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 时间相关工具类
 *
 * @author AmyTech
 */
public class TimeUtils {

    public static final SimpleDateFormat FORMAT_YYYY_MM_DD__HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat FORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    public static final SimpleDateFormat FORMAT_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHH:mm:ss", Locale.getDefault());

    private TimeUtils() {
        throw new AssertionError();
    }

    public static String getMM_SS(int milliSecond) {
        double secondTime = (double) milliSecond / 1000d;
        int minute = (int) secondTime / 60;
        int second = (int) (secondTime - minute * 60);
        if (String.valueOf(second).length() < 2) {
            return String.valueOf(minute) + ":0" + String.valueOf(second);
        } else {
            return String.valueOf(minute) + ":" + String.valueOf(second);
        }
    }

    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, FORMAT_YYYY_MM_DD__HH_MM_SS);
    }

    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
}