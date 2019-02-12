package com.androidacademy.msk.exerciseproject.screen.newsdetails

import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface NewsDetailsView : MvpView {

    @StateStrategyType(SingleStateStrategy::class)
    fun showNewsDetails(newsItem: DbNewsItem)
}
