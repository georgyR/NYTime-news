package com.androidacademy.msk.exerciseproject.screen.news_editor;

import android.os.Handler;
import android.os.Looper;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.db.NewsDao;
import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Date;

@InjectViewState
public class NewsEditorPresenter extends MvpPresenter<NewsEditorView> {

    private NewsDao database = App.getDatabase().getNewsDao();

    private DbNewsItem currentNewsItem;

    private boolean isFirstViewAttaching = true;


    public void onCreateActivity(int id) {
        getNewsDetails(id);
    }

    public void onSaveOptionItemClicked() {
        new Thread(() -> database.updateNewsItem(currentNewsItem)).start();
    }

    public void onSetTime(int hourOfDay, int minute) {
        String publishedDate = currentNewsItem.getPublishedDate();
        Date date = DateUtils.getDate(publishedDate, hourOfDay, minute);
        String timestamp = DateUtils.getTimestampFromDate(date);
        currentNewsItem.setPublishedDate(timestamp);

        String formattedTime = DateUtils.getFormattedTime(date, App.getContext());
        getViewState().updateTime(formattedTime);
    }

    public void onSetDate(int year, int month, int dayOfMonth) {
        String publishedDate = currentNewsItem.getPublishedDate();
        Date date = DateUtils.getDate(publishedDate, year, month, dayOfMonth);
        String timestamp = DateUtils.getTimestampFromDate(date);
        currentNewsItem.setPublishedDate(timestamp);

        String formattedDate = DateUtils.getFormattedDate(date);
        getViewState().updateDate(formattedDate);
    }

    public void onTitleChanged(String title) {
        currentNewsItem.setTitle(title);
    }

    public void onAbstractChanged(String abstractx) {
        currentNewsItem.setAbstractX(abstractx);
    }

    private void getNewsDetails(int id) {
        new Thread(() -> {
            if (isFirstViewAttaching) {
                currentNewsItem = database.getNewsById(id);
                isFirstViewAttaching = false;
            }
            new Handler(Looper.getMainLooper()).post(() ->
                    getViewState().showNewsDetails(currentNewsItem));
        }).start();

    }
}
