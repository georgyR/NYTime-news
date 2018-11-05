package com.androidacademy.msk.exerciseproject.screen;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.androidacademy.msk.exerciseproject.App;

public class ViewVisibilitySwitcher {
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
        View[] views = {dataView, loadingIndicatorView, errorView, emptyListView};
        switch (state) {
            case HAS_DATA:
                show(dataView, views);
                return;

            case LOADING:
                show(loadingIndicatorView, views);
                return;

            case ERROR:
                show(errorView, views);
                return;

            case EMPTY:
                show(emptyListView, views);
                return;

            default:
                Log.d(App.UI_DEBUG_TAG, "Unknown ui state");
        }
    }

    private void show(@Nullable View targetView, @NonNull View[] viewArray) {
        if (targetView == null) {
            Log.d(App.UI_DEBUG_TAG, "targetView is null");
            return;
        }
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
