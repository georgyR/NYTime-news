package com.androidacademy.msk.exerciseproject.screen;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ViewVisibilitySwitcher {

    private static final String DEBUG_UI_STATE = ViewVisibilitySwitcher.class.getSimpleName();
    @Nullable
    private final View dataView;
    @Nullable
    private final View loadingIndicatorView;
    @Nullable
    private final View errorView;
    @Nullable
    private final View emptyListView;

    public ViewVisibilitySwitcher(@Nullable View dataView,
                                  @Nullable View loadingIndicatorView,
                                  @Nullable View errorView,
                                  @Nullable View emptyListView) {
        this.dataView = dataView;
        this.loadingIndicatorView = loadingIndicatorView;
        this.errorView = errorView;
        this.emptyListView = emptyListView;
    }

    public void setUiState(@NonNull UiState state) {
        switch (state) {
            case HAS_DATA:
                show(dataView);
                return;

            case LOADING:
                show(loadingIndicatorView);
                return;

            case ERROR:
                show(errorView);
                return;

            case EMPTY:
                show(emptyListView);
                return;

            default:
                Log.d(DEBUG_UI_STATE, "Unknown ui state");
        }
    }

    private void show(@Nullable View targetView) {
        if (targetView == null) {
            Log.d(DEBUG_UI_STATE, "targetView is null");
            return;
        }
        View[] viewArray = {dataView, loadingIndicatorView, errorView, emptyListView};
        boolean isViewFound = false;
        for (@Nullable View view: viewArray) {
            if (view == null) {
                return;
            }
            if (!isViewFound && view.getId() == targetView.getId()) {
                view.setVisibility(View.VISIBLE);
                isViewFound = true;
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }
}
