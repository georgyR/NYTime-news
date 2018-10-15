package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.util.Log;

import com.androidacademy.msk.exerciseproject.Utils.DataUtils;
import com.androidacademy.msk.exerciseproject.data.model.NewsItem;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsListView> {

    private static final String TAG = "thread_debug";

    public void getNews() {
        Disposable subscribe = Observable
                .create((ObservableOnSubscribe<List<NewsItem>>) emitter -> {
                    Log.d(TAG, "loading news: " + Thread.currentThread());
                    Thread.sleep(2000);
                    emitter.onNext(DataUtils.NEWS);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    Log.d(TAG, "doOnSubscribe: " + Thread.currentThread());
                    getViewState().showProgressBar();
                })
                .subscribe(newsItems -> {
                    Log.d(TAG, "subscribe: " + Thread.currentThread());

                    getViewState().showNews(newsItems);
                    getViewState().hideProgressBar();
                });
    }
}
