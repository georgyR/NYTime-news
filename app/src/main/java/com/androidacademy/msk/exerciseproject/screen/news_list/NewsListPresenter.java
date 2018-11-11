package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.data.database.NewsConverter;
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.androidacademy.msk.exerciseproject.data.Section;
import com.androidacademy.msk.exerciseproject.screen.base.BasePresenter;
import com.androidacademy.msk.exerciseproject.utils.NewsDataUtils;
import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends BasePresenter<NewsListView> {

    private static final String DEBUG_DB_QUERY = NewsListPresenter.class.getSimpleName();

    @NonNull
    private Section currentSelectedSection;
    @NonNull
    private NewsDao database = App.getDatabase().getNewsDao();

    private int lastClickedItemPosition;
    private int lastClickedItemId;

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
        compositeDisposable.add(database.getRxNewsById(lastClickedItemId)
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
        compositeDisposable.add(App.getApi().getNews(section)
                .map(newsResponse -> NewsConverter.toDatabase(newsResponse.getResults(), section))
                .map(dbNewsItems -> {
                    database.deleteBySection(section);
                    database.insertAll(dbNewsItems);

                    int[] ids = database.getNewsIdBySection(section);
                    return NewsDataUtils.setIds(dbNewsItems, ids);
                })
                .onErrorReturn(throwable -> {
                    List<DbNewsItem> news = database.getNewsBySection(section);
                    if (news.isEmpty()) {
                        return null;
                    } else {
                        return NewsDataUtils.sortByDate(news);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showProgressBar())
                .subscribe(
                        news -> getViewState().showNews(NewsDataUtils.sortByDate(news)),
                        throwable -> getViewState().showError())
        );
    }
}
