package com.androidacademy.msk.exerciseproject.data;

import android.content.SharedPreferences;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.App;

import static android.content.Context.MODE_PRIVATE;

public class Storage {

    private static final String SHARED_PREF = App.getAppContext().getPackageName() +
            ".data.SHARED_PREF";
    private static final String IS_INTRO_VISIBLE_KEY = SHARED_PREF + ".IS_INTRO_VISIBLE_KEY";

    private final SharedPreferences sharedPreferences;

    public Storage() {
        Log.d("DEBUG_PACKAGE", "Storage: " + App.getAppContext().getPackageName());
        this.sharedPreferences = App.getAppContext().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    }

    public boolean needToShowIntro() {
        boolean isIntroVisible = sharedPreferences.getBoolean(IS_INTRO_VISIBLE_KEY, true);

        sharedPreferences.edit()
                .putBoolean(IS_INTRO_VISIBLE_KEY, !isIntroVisible)
                .apply();

        return isIntroVisible;
    }
}
