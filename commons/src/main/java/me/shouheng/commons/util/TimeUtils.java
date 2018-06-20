package me.shouheng.commons.util;

import android.content.Context;
import android.text.format.DateUtils;

import org.joda.time.DateTime;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.shouheng.commons.BaseApplication;

/**
 * @author shouh
 * @version $Id: TimeUtils, v 0.1 2018/6/6 22:14 shouh Exp$
 */
public class TimeUtils {

    private static int daysOfMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public enum DateFormat {
        YYYY_MMM_dd_E_hh_mm_a("yyyy MMM dd E hh:mm a"),
        YYYYMMdd_hh_mm_a("yyyy/MM/dd hh:mm a"),
        YYYYMMdd("yyyy/MM/dd");

        final String format;

        DateFormat(String format) {
            this.format = format;
        }
    }

    public static String formatDate(Date date, DateFormat dateFormat) {
        return date == null ? "null" : new SimpleDateFormat(dateFormat.format, Locale.getDefault()).format(date);
    }

    public static Date from(String strDate) {
        return new DateTime(strDate).toDate();
    }

    public static String getLongDate(Context context, Date date){
        int flags = DateUtils.FORMAT_ABBREV_MONTH;
        flags = flags | DateUtils.FORMAT_SHOW_YEAR;
        return DateUtils.formatDateTime(context, date.getTime(), flags);
    }

    public static String getLongDateWithWeekday(Context context, Date date){
        int flags = DateUtils.FORMAT_ABBREV_MONTH;
        flags = flags | DateUtils.FORMAT_SHOW_YEAR;
        return DateUtils.formatDateTime(context, date.getTime(), flags);
    }

    public static String getLongDateTime(Context context, Date date) {
        return DateUtils.formatDateTime(context, date.getTime(),
                DateUtils.FORMAT_NUMERIC_DATE
                        | DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_YEAR);
    }

    public static String getShortDateWithWeekday(Context context, Calendar calendar) {
        if (calendar == null) return "";
        Calendar now = Calendar.getInstance();
        int flags = DateUtils.FORMAT_ABBREV_MONTH;
        if (calendar.get(Calendar.YEAR) != now.get(Calendar.YEAR)){
            flags = flags | DateUtils.FORMAT_SHOW_YEAR;
        }
        return DateUtils.formatDateTime(context, calendar.getTime().getTime(), flags)
                + new SimpleDateFormat(" | E ", Locale.getDefault()).format(calendar.getTime());
    }

    public static String getShortDate(Context context, Calendar calendar){
        if (calendar == null) return "";
        Calendar now = Calendar.getInstance();
        int flags = DateUtils.FORMAT_ABBREV_MONTH;
        if (calendar.get(Calendar.YEAR) != now.get(Calendar.YEAR)){
            flags = flags | DateUtils.FORMAT_SHOW_YEAR;
        }
        return DateUtils.formatDateTime(context, calendar.getTime().getTime(), flags);
    }

    public static String getShortDate(Context context, Date date) {
        if (date == null) return "";
        Calendar now = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int flags = DateUtils.FORMAT_ABBREV_MONTH;
        if (c.get(Calendar.YEAR) != now.get(Calendar.YEAR)){
            flags = flags | DateUtils.FORMAT_SHOW_YEAR;
        }
        return DateUtils.formatDateTime(context, date.getTime(), flags);
    }

    public static String getNoMonthDay(Context context, Date date) {
        if (date == null) return "";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int flags = DateUtils.FORMAT_ABBREV_MONTH;
        flags |= DateUtils.FORMAT_NO_MONTH_DAY;
        flags = flags | DateUtils.FORMAT_SHOW_YEAR;
        return DateUtils.formatDateTime(context, date.getTime(), flags);
    }

    public static String getDateTimeShort(Context mContext, Date date) {
        if (date == null) return "";
        Calendar now = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int flags = DateUtils.FORMAT_ABBREV_MONTH;
        if (c.get(Calendar.YEAR) != now.get(Calendar.YEAR)){
            flags = flags | DateUtils.FORMAT_SHOW_YEAR;
        }
        return DateUtils.formatDateTime(mContext, date.getTime(), flags) + " "
                + DateUtils.formatDateTime(mContext, date.getTime(), DateUtils.FORMAT_SHOW_TIME);
    }

    public static String getShortTime(Context context, int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return getShortTime(context, calendar.getTime());
    }

    public static String getShortTime(Context mContext, Date time){
        return DateUtils.formatDateTime(mContext, time.getTime(), DateUtils.FORMAT_SHOW_TIME);
    }

    public static String getPrettyTime(Date date) {
        if (date == null) {
            return "";
        }
        return getPrettyTime(date, BaseApplication.getContext().getResources().getConfiguration().locale);
    }

    private static String getPrettyTime(Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        PrettyTime pt = new PrettyTime();
        if (locale != null) {
            pt.setLocale(locale);
        }
        return pt.format(date);
    }

    public static int getDaysOfMonth(int year, int month){
        final int MONTHS_YEAR = 12;
        if ((month<1) || (month>MONTHS_YEAR)){
            return 0;
        }
        int days = daysOfMonth[month-1];
        if ((month == 2) && isLeapYear(year)){
            days++;
        }
        return days;
    }

    public static String getShuXiang(int year){
        final String shuStrs[] = new String[]{"鼠年", "牛年", "虎年", "兔年",
                "龙年", "蛇年", "马年", "羊年", "猴年", "鸡年", "狗年", "猪年"};
        int x1 = year - 2008;
        if (x1<0){
            x1 = -x1;
            int x2 = x1 % 12;
            if (x2 == 0){
                return shuStrs[0];
            } else {
                x2 = 12 - x2;
                return shuStrs[x2];
            }
        } else{
            int x2 = x1 % 12;
            return shuStrs[x2];
        }
    }

    public static String getGanZhi(int year){
        final String[] ganStrs = new String[]{"甲", "乙", "丙", "丁", "戊", "己","庚", "辛", "壬", "癸"};
        final String[] zhiStrs = new String[]{"子", "丑", "寅", "卯", "辰", "巳","午", "未", "申", "酉", "戌", "亥"};
        String ganStr, zhiStr;
        int sc = year-2000;
        int gan = (7+sc)%10;
        int zhi = (5+sc)%12;
        if(gan<0){
            gan+=10;
        }
        if(zhi<0){
            zhi+=12;
        }
        if (gan == 0){
            ganStr = ganStrs[9];
        } else {
            ganStr = ganStrs[gan-1];
        }
        if (zhi == 0){
            zhiStr = zhiStrs[11];
        } else {
            zhiStr = zhiStrs[zhi-1];
        }
        return ganStr+zhiStr;
    }

    private static boolean isLeapYear(int year){
        return ((year%4 == 0) && (year%100 != 0))||(year%400 == 0);
    }
}
