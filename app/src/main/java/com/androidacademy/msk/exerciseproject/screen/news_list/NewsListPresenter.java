package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.App;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsListView> {

    @NonNull
    private static final String TAG = "rx_exception";
    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String currentSelectedSection = null;

    @Override
    public void attachView(NewsListView view) {
        super.attachView(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void getNews(String section) {
        compositeDisposable.add(App.getApiEndpoint().getNews(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showProgressBar())
                .subscribe(
                        newsRequest -> getViewState().showNews(newsRequest.getResults()),
                        throwable -> {
                            getViewState().showError();
                            Log.d(TAG, "getNews: view_error " + throwable.getMessage());
                        }));
    }

    public void onItemClicked(String url) {
        getViewState().openDetailsScreen(url);
    }

    public void onTryAgainButtonClicked() {
        getNews(currentSelectedSection);
    }


    public void onSpinnerItemClicked(@NonNull String section) {
        Log.d(TAG, "onSpinnerItemClicked: " + section);
        if (!section.equals(currentSelectedSection)) {
            getNews(section);
            currentSelectedSection = section;
        }

    }
}
