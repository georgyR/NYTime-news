package com.androidacademy.msk.exerciseproject.screen.about;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.model.AppError;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface AboutView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openApp(@NonNull Intent smsAppIntent);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorSnackbar(AppError appError);
}
