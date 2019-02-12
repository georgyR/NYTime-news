package com.androidacademy.msk.exerciseproject.screen.base

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao
import com.arellomobile.mvp.MvpView

abstract class BaseNewsItemPresenter<T : MvpView>(
        protected val dao: NewsDao,
        protected val itemId: Int
) : BasePresenter<T>()