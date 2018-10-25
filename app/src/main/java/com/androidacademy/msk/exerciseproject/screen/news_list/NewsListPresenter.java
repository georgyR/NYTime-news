package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.data.Section;
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

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getNews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void getNews() {

        /*disposable = Single
                .fromCallable(() -> DataUtils.NEWS)
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showProgressBar())
                .subscribe(newsItems -> getViewState().showNews(newsItems),
                        throwable -> {
                            getViewState().showError();
                            Log.d(TAG, "failed creating single from news list", throwable);
                        });*/
        compositeDisposable.add(App.getApiEndpoint().getNews(Section.HOME.toString().toLowerCase())
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

    public void onItemClicked(int position) {
        getViewState().openDetailsScreen(position);
    }

    public void onTryAgainButtonClicked() {
        getNews();
    }
}
