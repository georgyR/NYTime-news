package com.androidacademy.msk.exerciseproject.di.module

import com.androidacademy.msk.exerciseproject.screen.newslist.NewsListPresenter
import toothpick.config.Module


class NewsListModule : Module() {
    init {
        bind(NewsListPresenter::class.java).singletonInScope()
    }
}