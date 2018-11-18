package com.androidacademy.msk.exerciseproject.screen.about;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface AboutView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openSmsApp();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openEmailApp();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openTelegramApp();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openIstagramApp();

}
