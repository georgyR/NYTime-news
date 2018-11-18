package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.data.database.NewsConverter;
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi;
import com.androidacademy.msk.exerciseproject.model.Section;
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter;
import com.androidacademy.msk.exerciseproject.utils.NewsDataUtils;
import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends BasePresenter<NewsListView> {

    private static final String DEBUG_DB_QUERY = NewsListPresenter.class.getSimpleName();

    @NonNull
    private final NYTimesApi api;
    @NonNull
    private final NewsDao database;

    @NonNull
    private Section currentSelectedSection;

    private int lastClickedItemPosition;
    private int lastClickedItemId;

    @Inject
    public NewsListPresenter(@NonNull NYTimesApi api, @NonNull NewsDao database) {
        this.api = api;
        this.database = database;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showEmptyView();
    }


    public void onItemClicked(int id, int position) {
        lastClickedItemId = id;
        lastClickedItemPosition = position;
        getViewState().openDetailsScreen(id);
    }

    public void onTryAgainButtonClicked() {
        getNews(currentSelectedSection.toString().toLowerCase());
    }

    public void onFabClicked() {
        getNews(currentSelectedSection.toString().toLowerCase());
    }

    public void onSpinnerItemClicked(@NonNull Section section) {
        if (!section.equals(currentSelectedSection)) {
            currentSelectedSection = section;
        }
    }

    public void onListItemChanged() {
        compositeDisposable.add(
                database.getRxNewsById(lastClickedItemId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                newsItem -> getViewState().updateCertainNewsItemInList(
                                        newsItem,
                                        lastClickedItemPosition),
                                throwable -> Log.d(
                                        DEBUG_DB_QUERY,
                                        "error in getRxNewsById() query",
                                        throwable)
                        )
        );
    }

    public void onListItemDeleted() {
        getViewState().deleteNewsItemInList(lastClickedItemPosition);
    }

    private void getNews(@NonNull String section) {
        compositeDisposable.add(
                api.getNews(section)
                        .map(newsResponse -> {
                            List<DbNewsItem> dbNewsItems = NewsConverter.toDatabase(
                                    newsResponse.getResults(),
                                    section);
                            database.deleteBySection(section);
                            database.insertAll(dbNewsItems);
                            int[] ids = database.getNewsIdBySection(section);
                            return NewsDataUtils.setIds(dbNewsItems, ids);
                        })
                        .onErrorReturn(throwable -> {
                            List<DbNewsItem> news = database.getNewsBySection(section);
                            if (news.isEmpty()) {
                                return null;
                            }
                            return news;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showProgressBar())
                        .subscribe(
                                news -> getViewState().showNews(news),
                                throwable -> getViewState().showError())
        );
    }
}
