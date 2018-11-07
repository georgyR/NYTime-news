package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.db.NewsConverter;
import com.androidacademy.msk.exerciseproject.db.NewsDao;
import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.network.api.Section;
import com.androidacademy.msk.exerciseproject.utils.NewsDataUtils;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsListView> {

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
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
        new Thread(() -> {
            DbNewsItem newsItem = database.getNewsById(lastClickedItemId);
            Log.d("SAVE_DEBUG", "onListItemChanged: " + newsItem);
            new Handler(Looper.getMainLooper()).post(() ->
                    getViewState().updateCertainNewsItemInList(newsItem, lastClickedItemPosition)
            );
        }).start();

    }

    public void onListItemDeleted() {
        getViewState().deleteNewsItemInList(lastClickedItemPosition);
    }

    private void getNews(@NonNull String section) {
        compositeDisposable.add(App.getApi().getNews(section)
                .map(newsResponse -> NewsConverter.toDatabase(newsResponse.getResults(), newsResponse.getSection()))
                .doOnSuccess(
                        newsItems -> {
                            database.deleteBySection(section);
                            database.insertAll(newsItems);
                        }
                )
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
                        throwable -> getViewState().showError()));
    }
}
