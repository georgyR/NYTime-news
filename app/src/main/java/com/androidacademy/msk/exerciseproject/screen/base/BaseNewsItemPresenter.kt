package com.androidacademy.msk.exerciseproject.screen.base

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable

abstract class BaseNewsItemPresenter<T : MvpView>(
        protected val dao: NewsDao,
        protected val itemId: Int
) : BasePresenter<T>()