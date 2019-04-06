package com.androidacademy.msk.exerciseproject.screen.newslist

import NewsDataUtils
import com.androidacademy.msk.exerciseproject.data.database.NewsConverter.toDatabase
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi
import com.androidacademy.msk.exerciseproject.model.Section
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter
import com.androidacademy.msk.exerciseproject.utils.SectionUtils
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@InjectViewState
class NewsListPresenter @Inject constructor(
        private val api: NYTimesApi,
        private val newsDao: NewsDao
) : BasePresenter<NewsListView>() {

    private var currentSelectedSection = Section.HOME


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgressBar()
        getCurrentSectionNews()
    }


    fun onItemClicked(id: Int) {
        viewState.openDetailsScreen(id)
    }

    fun onTryAgainButtonClicked() {
        viewState.toggleSwipeRefreshProgress(true)
        getCurrentSectionNews()
    }

    fun onSpinnerItemClicked(section: Section) {
        if (section != currentSelectedSection) {
            currentSelectedSection = section

            viewState.toggleSwipeRefreshProgress(true)
            val sectionString = SectionUtils.getSectionForQuery(section)
            getNews(sectionString)
        }
    }


    fun onRefreshSwiped() {
        getCurrentSectionNews()
    }

    private fun getCurrentSectionNews() {
        getNews(currentSelectedSection.toString().toLowerCase())
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
                        .doFinally { viewState.toggleSwipeRefreshProgress(false) }
                        .subscribe(
                                { news -> viewState.showNews(news) },
                                { viewState.showError() }
                        )
        )
    }
}