package com.androidacademy.msk.exerciseproject.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.androidacademy.msk.exerciseproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    private DateUtils() {
    }

    @NonNull
    public static Date createDate(int year, int month, int date, int hrs, int min) {
        return new GregorianCalendar(year, month - 1, date, hrs, min).getTime();
    }

    @NonNull
    public static String convertDateToString(@NonNull Date date, @NonNull Context context) {
        if (android.text.format.DateUtils.isToday(date.getTime())) {
            Date currentDate = new Date();
            long hourAgo = TimeUnit.MILLISECONDS.toHours(currentDate.getTime() - date.getTime());
            return context.getString(R.string.date_utils_hr_ago, hourAgo);
        }

        String publishTime = getFormattedTime(date, context);

        if (isYesterday(date)) {
            return context.getString(R.string.date_utils_yesterday, publishTime);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("", Locale.US);

        if (isCurrentYear(date)) {
            formatter.applyPattern("MMM d, ");
            return  formatter.format(date) + publishTime;
        }

        formatter.applyPattern("yyyy MMM d, ");
        return formatter.format(date) + publishTime;
    }

    @NonNull
    private static String getFormattedTime(@NonNull Date date, @NonNull Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("", Locale.US);
        if (DateFormat.is24HourFormat(context)) {
            formatter.applyPattern("HH:mm");
            return formatter.format(date);
        } else {
            formatter.applyPattern("hh:mm a");
            return formatter.format(date);
        }
    }

    private static boolean isYesterday(@NonNull Date date) {
        return android.text.format.DateUtils.isToday(date.getTime() + android.text.format.DateUtils.DAY_IN_MILLIS);
    }

    private static boolean isCurrentYear(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        calendar.setTime(date);
        int dateYear = calendar.get(Calendar.YEAR);

        return (currentYear == dateYear);
    }
}
