package com.androidacademy.msk.exerciseproject.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class EnumUtils {

    private EnumUtils() {
        throw new UnsupportedOperationException("There should be no class instance");
    }

    @NonNull
    public static <T extends Enum<T>> List<String> convertEnumValuesToCapitalizedList(@NonNull T[] enumArray) {
        List<String> list = new ArrayList<>(enumArray.length);
        for (T enumItem : enumArray) {
            String result = StringUtils.capitalize(enumItem.toString());
            list.add(result);
        }
        return list;
    }
}
