package com.androidacademy.msk.exerciseproject.screen.newslist

import android.util.Log
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.data.database.toDatabase
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi
import com.androidacademy.msk.exerciseproject.model.Section
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter
import com.androidacademy.msk.exerciseproject.utils.NewsDataUtils
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@InjectViewState
class NewsListPresenter @Inject constructor(
        private val api: NYTimesApi,
        private val newsDao: NewsDao) : BasePresenter<NewsListView>() {

    companion object {
        private const val LOG_TAG = "NewsListPresenter"
    }

    private var currentSelectedSection = Section.HOME

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showEmptyView()
    }

    fun onItemClicked(id: Int) {
        viewState.openDetailsScreen(id)
    }

    fun onTryAgainButtonClicked() {
        getNews(currentSelectedSection.toString().toLowerCase())
    }

    fun onFabClicked() {
        getNews(currentSelectedSection.toString().toLowerCase())
    }

    fun onSpinnerItemClicked(section: Section) {
        if (section != currentSelectedSection) {
            currentSelectedSection = section
            getNews(section.name.toLowerCase())
        }
    }

    private fun getNews(section: String) {
        compositeDisposable.add(
                api.getNews(section)
                        .map<List<DbNewsItem>> { newsResponse ->
                            val dbNewsItems = toDatabase(newsResponse.results, section)
                            newsDao.deleteBySection(section)
                            newsDao.insertAll(dbNewsItems)
                            val ids = newsDao.getNewsIdBySection(section)
                            NewsDataUtils.setIds(dbNewsItems, ids)
                        }
                        .onErrorReturn {
                            val news = newsDao.getNewsBySection(section)
                            if (news.isNotEmpty()) news else null
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { viewState.showProgressBar() }
                        .subscribe(
                                { news -> viewState.showNews(news) },
                                { viewState.showError() })
        )
    }
}