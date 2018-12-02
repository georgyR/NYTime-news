package com.androidacademy.msk.exerciseproject.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class DateUtils {

    private static final String DEBUG_PARSING = DateUtils.class.getSimpleName();

    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    @NonNull
    public static Date getDate(@NonNull String timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_PATTERN, Locale.getDefault());
        Date date;
        try {
            date = formatter.parse(timestamp);
        } catch (ParseException e) {
            Log.d(DEBUG_PARSING, "parsing date error", e);
            date = new Date();
            date.setTime(0);
        }
        return date;
    }

    @NonNull
    public static String getFormattedDate(@NonNull String timestamp) {
        Date date = getDate(timestamp);

        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy ", Locale.getDefault());
        return formatter.format(date);

    }

    @NonNull
    public static String getSpecialFormattedDate(@NonNull String timestamp, @NonNull Context context) {
        Date date = getDate(timestamp);

        if (android.text.format.DateUtils.isToday(date.getTime())) {
            Date currentDate = new Date();
            long timeDiff = currentDate.getTime() - date.getTime();
            long hourAgo = TimeUnit.MILLISECONDS.toHours(timeDiff);
            if (hourAgo == 0) {
                long minute = TimeUnit.MILLISECONDS.toMinutes(timeDiff);
                return context.getString(R.string.date_utils_min_ago, minute);
            }
            return context.getString(R.string.date_utils_hr_ago, hourAgo);
        }

        String publishTime = getFormattedTime(date, context);

        if (isYesterday(date)) {
            return context.getString(R.string.date_utils_yesterday, publishTime);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("", Locale.getDefault());

        if (isCurrentYear(date)) {
            formatter.applyPattern("MMM d, ");
            return  formatter.format(date) + publishTime;
        }

        formatter.applyPattern("yyyy MMM d, ");
        return formatter.format(date) + publishTime;
    }


    @NonNull
    public static String getFormattedTime(@NonNull String timestamp, @NonNull Context context) {
        Date date = getDate(timestamp);

        SimpleDateFormat formatter = new SimpleDateFormat("", Locale.getDefault());
        if (DateFormat.is24HourFormat(context)) {
            formatter.applyPattern("HH:mm");
            return formatter.format(date);
        } else {
            formatter.applyPattern("hh:mm a");
            return formatter.format(date);
        }
    }

    @NonNull
    public static String getFormattedTime(@NonNull Date date, @NonNull Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("", Locale.getDefault());
        if (DateFormat.is24HourFormat(context)) {
            formatter.applyPattern("HH:mm");
            return formatter.format(date);
        } else {
            formatter.applyPattern("hh:mm a");
            return formatter.format(date);
        }
    }


    private static boolean isYesterday(@NonNull Date date) {
        return android.text.format.DateUtils.isToday(date.getTime() +
                android.text.format.DateUtils.DAY_IN_MILLIS);
    }

    private static boolean isCurrentYear(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        calendar.setTime(date);
        int dateYear = calendar.get(Calendar.YEAR);

        return (currentYear == dateYear);
    }

}
