package com.androidacademy.msk.exerciseproject.screen.main_container;

import android.support.annotation.NonNull;

public interface FragmentContainer {

    boolean isTwoPanel();

    void setSpinnerVisibility(int visibility);

    @NonNull
    String getCurrentSelectedSection();
}
