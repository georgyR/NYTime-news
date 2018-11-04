package com.androidacademy.msk.exerciseproject.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.androidacademy.msk.exerciseproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    private DateUtils() {
        throw new UnsupportedOperationException("There should be no class instance");
    }

    public static long convertTimestampToUnixDate(@NonNull String timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        Date date;
        try {
            date = formatter.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return date.getTime();
    }

    @NonNull
    public static String convertTimestampToString(@NonNull String timestamp, @NonNull Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        Date date;
        try {
             date = formatter.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return "parsing time error";
        }

        if (android.text.format.DateUtils.isToday(date.getTime())) {
            Date currentDate = new Date();
            long hourAgo = TimeUnit.MILLISECONDS.toHours(currentDate.getTime() - date.getTime());
            return context.getString(R.string.date_utils_hr_ago, hourAgo);
        }

        String publishTime = getFormattedTime(date, context);

        if (isYesterday(date)) {
            return context.getString(R.string.date_utils_yesterday, publishTime);
        }

        formatter = new SimpleDateFormat("", Locale.US);

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
