package com.androidacademy.msk.exerciseproject.utils;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.network.api.Section;

import java.util.ArrayList;
import java.util.List;

public class EnumUtils {

    private EnumUtils() {
        throw new UnsupportedOperationException("There should be no class instance");
    }

    @NonNull
    public static List<String> convertEnumValuesToList(Section[] sectionArray) {
        List<String> spinnerList = new ArrayList<>(sectionArray.length);
        for (Section section : sectionArray) {
            String result = section.getHeadwordName();
            spinnerList.add(result);
        }
        return spinnerList;
    }
}
