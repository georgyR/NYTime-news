package com.androidacademy.msk.exerciseproject.screen.newsdetails

import android.util.Log
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao
import com.androidacademy.msk.exerciseproject.screen.base.BaseNewsItemPresenter
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class NewsDetailsPresenter @Inject constructor(
        dao: NewsDao,
        id: Int
) : BaseNewsItemPresenter<NewsDetailsView>(dao, id) {

    companion object {
        private val DEBUG_DB_QUERY = NewsDetailsPresenter::class.java.simpleName
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getNewsDetails()
    }

    private fun getNewsDetails() {
        compositeDisposable.add(
                dao.getRxNewsById(itemId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { newsItem -> viewState.showNewsDetails(newsItem) },
                                { throwable ->
                                    Log.d(
                                            DEBUG_DB_QUERY,
                                            "error in getRxNewsById() query",
                                            throwable
                                    )
                                })
        )
    }
}
