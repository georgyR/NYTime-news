package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.db.NewsDao;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsDetailsPresenter extends MvpPresenter<NewsDetailsView> {

    @NonNull
    private NewsDao database = App.getDatabase().getNewsDao();
    @NonNull
    private Disposable disposable;

    public void onCreateActivity(int id) {
        getNewsDetails(id);
    }

    public void onNewsEdited(int id) {
        getNewsDetails(id);
    }

    public void onDeleteOptionsItemSelected(int id) {
        new Thread(() -> database.deleteNewsItemById(id)).start();
    }

    private void getNewsDetails(int id) {
        disposable = database.getRxNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItem -> getViewState().showNewsDetails(newsItem));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
