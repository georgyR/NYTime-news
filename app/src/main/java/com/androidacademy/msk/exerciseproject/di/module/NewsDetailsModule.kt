package com.androidacademy.msk.exerciseproject.di.module

import com.androidacademy.msk.exerciseproject.di.model.NewsId
import com.androidacademy.msk.exerciseproject.screen.newsdetails.NewsDetailsPresenter
import toothpick.config.Module
import javax.inject.Inject


class NewsDetailsModule @Inject constructor(newsId: NewsId) : Module() {
    init {
        bind(NewsId::class.java).toInstance(newsId)
        bind(NewsDetailsPresenter::class.java).singletonInScope()
    }
}