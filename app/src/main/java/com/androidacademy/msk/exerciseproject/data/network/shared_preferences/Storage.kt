package com.androidacademy.msk.exerciseproject.data.network.shared_preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.androidacademy.msk.exerciseproject.BuildConfig
import javax.inject.Inject


class Storage @Inject constructor(context: Context) {

    companion object {
        private const val SHARED_PREF = BuildConfig.APPLICATION_ID + ".data.SHARED_PREF"
        private const val KEY_IS_FIRST_APP_LAUNCH = "$SHARED_PREF.KEY_IS_FIRST_APP_LAUNCH"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE)

    fun isFirstAppLaunch(): Boolean {
        val isFirstAppLaunch = sharedPreferences.getBoolean(KEY_IS_FIRST_APP_LAUNCH, true)

        sharedPreferences.edit()
                .putBoolean(KEY_IS_FIRST_APP_LAUNCH, false)
                .apply()

        return isFirstAppLaunch
    }
}