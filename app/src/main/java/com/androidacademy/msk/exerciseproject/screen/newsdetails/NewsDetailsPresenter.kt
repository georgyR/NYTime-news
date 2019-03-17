package com.androidacademy.msk.exerciseproject.screen.newsdetails

import android.util.Log
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao
import com.androidacademy.msk.exerciseproject.di.model.NewsId
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class NewsDetailsPresenter @Inject constructor(
        private val itemId: NewsId,
        private val dao: NewsDao
) : BasePresenter<NewsDetailsView>() {

    companion object {
        private const val LOG_TAG = "NewsDetailsPresenter"
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getNewsDetails()
    }

    private fun getNewsDetails() {
        compositeDisposable.add(
                dao.getRxNewsById(itemId.id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { newsItem -> viewState.showNewsDetails(newsItem) },
                                { throwable ->
                                    Log.d(
                                            LOG_TAG,
                                            "error in getRxNewsById() query",
                                            throwable
                                    )
                                })
        )
    }
}
