package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;

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

    private Section currentSelectedSection = null;
    private NewsDao database = App.getDatabase().getNewsDao();

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
                    List<DbNewsItem> news = database.getNews(section);
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

    public void onItemClicked(@NonNull String url) {
        getViewState().openDetailsScreen(url);
    }

    public void onTryAgainButtonClicked() {
        getNews(currentSelectedSection.toString().toLowerCase());
    }

    public void onFabClicked() {
        getNews(currentSelectedSection.toString().toLowerCase());
    }

    public void onSpinnerItemClicked(@NonNull Section section) {
        if (!section.equals(currentSelectedSection)) {
            /*getNews(section);*/
            currentSelectedSection = section;
        }

    }
}
