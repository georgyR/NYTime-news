package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.network.api.Section;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsListView> {

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Section currentSelectedSection = null;

    @Override
    public void attachView(NewsListView view) {
        super.attachView(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void getNews(Section section) {
        compositeDisposable.add(App.getApi().getNews(section.getLowerCaseName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showProgressBar())
                .subscribe(
                        newsRequest -> {
                            if (newsRequest.getResults() != null) {
                                getViewState().showNews(newsRequest.getResults());
                            } else {
                                getViewState().showError();
                            }
                        },
                        throwable -> getViewState().showError()));
    }

    public void onItemClicked(String url) {
        getViewState().openDetailsScreen(url);
    }

    public void onTryAgainButtonClicked() {
        getNews(currentSelectedSection);
    }


    public void onSpinnerItemClicked(@NonNull Section section) {
        if (!section.equals(currentSelectedSection)) {
            getNews(section);
            currentSelectedSection = section;
        }

    }
}
