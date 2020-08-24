package com.example.common.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    //2015-08-25T21:00:00+03:00
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private final Calendar time;

    public DateTimeUtil(Calendar time) {
        this.time = time;
    }

    public void setMonth(int month) {
        this.time.set(Calendar.MONTH, month);
    }

    public void setYear(int year){
        this.time.set(Calendar.YEAR,year);
    }

    /**
     * Returns the current time. However, this behaviour can be changed by setting time via calling setCurrentTime() during testing
     *
     * @return
     */
    public Calendar getCurrentTime() {
        if (time == null) {
            return Calendar.getInstance();
        }
        return (Calendar) time.clone();
    }

    public Calendar getFirstDay(Calendar date) {
        date = getStartOfDay(date);
        date.set(Calendar.DAY_OF_MONTH, 1);
        return date;
    }

    public Calendar getLastDay(Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, 1);
        date.add(Calendar.MONTH, 1);
        date.add(Calendar.DAY_OF_YEAR, -1);
        date = getEndOfDay(date);
        return date;
    }

    public Calendar getThisMonthStartDate() {
        Calendar date = getCurrentTime();
        return getFirstDay(date);
    }


    public Calendar getThisMonthEndDate() {
        Calendar date = getCurrentTime();
        getLastDay(date);
        return date;
    }

    public Calendar getLastMonthStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
//        date.set(Calendar.DATE, 1);
//        date.add(Calendar.DAY_OF_MONTH, -1);
        date.add(Calendar.MONTH, -1);
        date.set(Calendar.DAY_OF_MONTH, 1);
        return date;
    }

    public Calendar getLastMonthEndDate() {
        // Calendar date = getCurrentTime();
//        date = getEndOfDay(date);
//        date.set(Calendar.DAY_OF_MONTH, 1);
//        date.add(Calendar.DAY_OF_YEAR, -1);
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        date.set(Calendar.DATE, 1);
        date.add(Calendar.DAY_OF_MONTH, -1);
        return date;
    }

    public Calendar getThisWeekStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        // first day of current week
        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return date;
    }

    public Calendar getThisWeekEndDate() {
        Calendar date = getThisWeekStartDate();
        date.add(Calendar.DAY_OF_YEAR, 6);
        date = getEndOfDay(date);
        return date;
    }


    public Calendar getThisYearStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        date.set(Calendar.MONTH, 1);
        date.set(Calendar.DAY_OF_YEAR, 1);
        return date;
    }

    public Calendar getThisYearEndDate() {
        Calendar date = getCurrentTime();
        date = getEndOfDay(date);
        date.set(Calendar.MONTH, 11);
        date.set(Calendar.DAY_OF_MONTH, 31);
        return date;
    }

    public Calendar getLastYearStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        date.add(Calendar.YEAR, -1);
        date.set(Calendar.MONTH, 1);
        date.set(Calendar.DAY_OF_YEAR, 1);
        return date;
    }

    public Calendar getLastYearEndDate() {
        Calendar date = getCurrentTime();
        date = getEndOfDay(date);
        date.add(Calendar.YEAR, -1);
        date.set(Calendar.MONTH, 11);
        date.set(Calendar.DAY_OF_MONTH, 31);
        return date;
    }

    public Calendar getLastWeekStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        // first day of current week
        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.add(Calendar.DAY_OF_YEAR, -7);
        return date;
    }

    public Calendar getLastWeekEndDate() {
        Calendar date = getLastWeekStartDate();
        date.add(Calendar.DAY_OF_YEAR, 6);
        date = getEndOfDay(date);
        return date;
    }

    public Calendar getBeginningFromDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        date.set(1900, Calendar.JANUARY, 1);
        return date;
    }

    public static String toDateString(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(calendar.getTime());
    }

    public static String toDateString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(date);
    }

    public static String toDateTimeString(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
        return df.format(calendar.getTime());
    }

    public static Date toDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(date);
    }

    public static Calendar toCalender(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        cal.setTime(sdf.parse(date));
        return cal;
    }

    public static Calendar toDateTime(String dateTime) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        cal.setTime(sdf.parse(dateTime));
        return cal;
    }

    public static String toString(Calendar calendar, String format) {
        if (format == null)
            format = DATE_TIME_FORMAT;
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }

    public static boolean isValidDate(String dateString, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            df.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Calendar getCalender(String dateString, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date date = df.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Calendar getEndOfDay(Calendar date) {
        date.setTime(DateUtils.addMilliseconds(DateUtils.ceiling(date.getTime(), Calendar.DATE), -1));
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }

    public static Calendar getStartOfDay(Calendar date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }
}
