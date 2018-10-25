package com.androidacademy.msk.exerciseproject.utils;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.Section;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    private DataUtils() {
    }

    @NonNull
    public static List<String> convertEmunValuesToList(Section[] sectionArray) {
        List<String> spinnerList = new ArrayList<>(sectionArray.length);
        for (Section section : sectionArray) {
            String result = section.toString().substring(0, 1) +
                    section.toString().substring(1).toLowerCase();
            spinnerList.add(result);
        }
        return spinnerList;
    }
}
