package com.androidacademy.msk.exerciseproject.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Storage {

    private static final String SHARED_PREF = "com.androidacademy.msk.exerciseproject.data.SHARED_PREF";
    private static final String IS_INTRO_VISIBLE_KEY = SHARED_PREF + ".IS_INTRO_VISIBLE_KEY";

    public static boolean needToShowIntro(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        boolean isIntroVisible = sharedPreferences.getBoolean(IS_INTRO_VISIBLE_KEY, true);

        sharedPreferences.edit()
                .putBoolean(IS_INTRO_VISIBLE_KEY, !isIntroVisible)
                .apply();

        return isIntroVisible;
    }
}
