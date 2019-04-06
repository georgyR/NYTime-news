package com.androidacademy.msk.exerciseproject.screen.newslist

import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface NewsListView : MvpView {
    @StateStrategyType(SingleStateStrategy::class)
    fun showProgressBar()

    @StateStrategyType(SingleStateStrategy::class)
    fun showNews(news: List<DbNewsItem>)

    @StateStrategyType(SingleStateStrategy::class)
    fun showError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openDetailsScreen(id: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setCurrentSectionInSpinner(position: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun toggleSwipeRefreshProgress(show: Boolean)
}