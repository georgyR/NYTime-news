package com.androidacademy.msk.exerciseproject.utils

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import com.androidacademy.msk.exerciseproject.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object DateUtils {

    private val EMPTY_DATE = Date().apply { time = 0 }

    private const val LOG_TAG = "DateUtils"

    private const val TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ"

    //TODO EMPTY_DATE?
    @JvmStatic
    fun getDate(timestamp: String): Date {
        val formatter = SimpleDateFormat(TIMESTAMP_PATTERN, Locale.getDefault())
        return try {
            formatter.parse(timestamp)
        } catch (e: ParseException) {
            Log.d(LOG_TAG, "parsing date error", e)
            EMPTY_DATE
        }
    }

    @JvmStatic
    fun getFormattedDate(timestamp: String): String {
        val date = getDate(timestamp)
        val formatter = SimpleDateFormat("MMM d, yyyy ", Locale.getDefault())
        return formatter.format(date)

    }

    fun getSpecialFormattedDate(timestamp: String, context: Context): String {
        val date = getDate(timestamp)

        val formatter = SimpleDateFormat("", Locale.getDefault())
        val publishTime = getFormattedTime(date, context)

        return when {
            android.text.format.DateUtils.isToday(date.time) -> {
                val currentDate = Date()
                val timeDiff = currentDate.time - date.time
                val hourAgo = TimeUnit.MILLISECONDS.toHours(timeDiff)
                if (hourAgo == 0L) {
                    val minute = TimeUnit.MILLISECONDS.toMinutes(timeDiff)
                    context.getString(R.string.date_utils_min_ago, minute)
                }
                context.getString(R.string.date_utils_hr_ago, hourAgo)
            }

            isYesterday(date) -> {
                context.getString(R.string.date_utils_yesterday, publishTime)
            }

            isCurrentYear(date) -> {
                formatter.applyPattern("MMM d, ")
                formatter.format(date) + publishTime
            }

            else -> {
                formatter.applyPattern("yyyy MMM d, ")
                formatter.format(date) + publishTime
            }


        }

    }

    @JvmStatic
    fun getFormattedTime(timestamp: String, context: Context): String {
        val date = getDate(timestamp)

        val formatter = SimpleDateFormat("", Locale.getDefault())

        if (DateFormat.is24HourFormat(context)) {
            formatter.applyPattern("HH:mm")
        } else {
            formatter.applyPattern("hh:mm a")
        }

        return formatter.format(date)

    }

    private fun getFormattedTime(date: Date, context: Context): String {
        val formatter = SimpleDateFormat("", Locale.getDefault())

        if (DateFormat.is24HourFormat(context)) {
            formatter.applyPattern("HH:mm")
        } else {
            formatter.applyPattern("hh:mm a")
        }

        return formatter.format(date)

    }


    private fun isYesterday(date: Date): Boolean {
        return android.text.format.DateUtils.isToday(date.time + android.text.format.DateUtils.DAY_IN_MILLIS)
    }

    private fun isCurrentYear(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)

        calendar.time = date
        val dateYear = calendar.get(Calendar.YEAR)

        return currentYear == dateYear
    }

}
