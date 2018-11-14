package com.androidacademy.msk.exerciseproject.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Storage {

    private static final String SHARED_PREF =  "com.androidacademy.msk.exerciseproject.com.data.SHARED_PREF";
    private static final String IS_INTRO_VISIBLE_KEY = SHARED_PREF + ".IS_INTRO_VISIBLE_KEY";

    private final SharedPreferences sharedPreferences;

    public Storage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    }

    public boolean needToShowIntro() {
        boolean isIntroVisible = sharedPreferences.getBoolean(IS_INTRO_VISIBLE_KEY, true);

        sharedPreferences.edit()
                .putBoolean(IS_INTRO_VISIBLE_KEY, !isIntroVisible)
                .apply();

        return isIntroVisible;
    }
}
