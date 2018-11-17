package com.androidacademy.msk.exerciseproject.screen.intro;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface IntroView extends MvpView {

    @StateStrategyType(AddToEndStrategy.class)
    void setLayout();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startNewsListActivity();
}
