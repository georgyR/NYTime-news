package com.androidacademy.msk.exerciseproject.screen;

import android.support.annotation.NonNull;
import android.view.View;

public class ViewVisibilitySwitcher {
    @NonNull
    private final View dataView;
    @NonNull
    private final View loadingIndicatorView;
    @NonNull
    private final View errorView;

    public ViewVisibilitySwitcher(@NonNull View dataView,
                                  @NonNull View loadingIndicatorView,
                                  @NonNull View errorView) {
        this.dataView = dataView;
        this.loadingIndicatorView = loadingIndicatorView;
        this.errorView = errorView;
    }

    public void setUiState(@NonNull UiState state) {
        switch (state) {
            case HAS_DATA:
                dataView.setVisibility(View.VISIBLE);
                loadingIndicatorView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                return;
            case LOADING:
                dataView.setVisibility(View.GONE);
                loadingIndicatorView.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
                return;
            case ERROR:
                dataView.setVisibility(View.GONE);
                loadingIndicatorView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);

        }
    }
}
