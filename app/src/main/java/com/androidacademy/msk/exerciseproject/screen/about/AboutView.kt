package com.androidacademy.msk.exerciseproject.screen.about

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface AboutView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openSmsApp()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openEmailApp()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openTelegramApp()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openIstagramApp()
}
