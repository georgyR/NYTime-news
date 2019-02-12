package com.androidacademy.msk.exerciseproject.screen.intro

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface IntroView : MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun setLayout()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun startNewsListActivity()
}
