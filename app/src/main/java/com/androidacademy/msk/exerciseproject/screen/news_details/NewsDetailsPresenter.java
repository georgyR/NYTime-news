package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.os.Handler;
import android.os.Looper;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.db.NewsDao;
import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class NewsDetailsPresenter extends MvpPresenter<NewsDetailsView> {

    private NewsDao database = App.getDatabase().getNewsDao();

    public void onCreateActivity(int id) {
        getNewsDetails(id);
    }

    public void onNewsEdited(int id) {
        getNewsDetails(id);
    }

    private void getNewsDetails(int id) {
        new Thread(() -> {
            DbNewsItem newsItem = database.getNewsById(id);
            new Handler(Looper.getMainLooper()).post(() -> getViewState().showNewsDetails(newsItem));
        }).start();

    }
}
