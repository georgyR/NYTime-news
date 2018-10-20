package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.utils.DataUtils;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsListView> {

    @NonNull
    private static final String TAG = "thread_debug";
    @NonNull
    private Disposable disposable;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getNews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void getNews() {
        disposable = Single
                .fromCallable(() -> DataUtils.NEWS)
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showProgressBar())
                .subscribe(newsItems -> {
                            getViewState().hideProgressBar();
                            getViewState().addNews(newsItems);
                        },
                        throwable -> Log.d(TAG, "rx error"));
    }

    public void onItemClicked(int position) {
        getViewState().openDetailsScreen(position);
    }
}
