package com.androidacademy.msk.exerciseproject.utils;

import android.support.annotation.NonNull;

public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("There should be no class instance");
    }

    @NonNull
    public static String capitalize(@NonNull String string) {
        return string.substring(0, 1) +
                string.substring(1).toLowerCase();
    }
}
