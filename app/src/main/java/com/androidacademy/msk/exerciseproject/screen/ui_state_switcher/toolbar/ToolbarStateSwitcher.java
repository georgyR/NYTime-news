package com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.toolbar;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.androidacademy.msk.exerciseproject.model.Section;
import com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.screen.ViewVisibilitySwitcher;
import com.androidacademy.msk.exerciseproject.utils.StringUtils;

public class ToolbarStateSwitcher {

    private static final String DEBUG_TOOLBAR_STATE = ViewVisibilitySwitcher.class.getSimpleName();

    private final ActionBar actionBar;
    private final Spinner spinner;

    public ToolbarStateSwitcher(@NonNull ActionBar actionBar, @NonNull Spinner spinner) {
        this.actionBar = actionBar;
        this.spinner = spinner;
    }

    public void setToolbarState(@NonNull ToolbarState state) {
        switch (state) {
            case SPINNER:
                actionBar.setDisplayShowTitleEnabled(false);
                spinner.setVisibility(View.VISIBLE);
                actionBar.setDisplayHomeAsUpEnabled(false);
                break;
            case BACK_BUTTON_AND_TITLE:
                int position = spinner.getSelectedItemPosition();
                String section = Section.values()[position].name();
                section = StringUtils.capitalize(section);
                actionBar.setTitle(section);
                actionBar.setDisplayShowTitleEnabled(true);
                spinner.setVisibility(View.GONE);
                actionBar.setDisplayHomeAsUpEnabled(true);
                break;
            default:
                Log.d(DEBUG_TOOLBAR_STATE, "Unknown toolbar state");
        }
    }
}
