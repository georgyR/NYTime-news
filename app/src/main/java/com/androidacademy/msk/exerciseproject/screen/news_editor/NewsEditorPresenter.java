package com.androidacademy.msk.exerciseproject.screen.news_editor;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.App;
import com.androidacademy.msk.exerciseproject.db.NewsDao;
import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsEditorPresenter extends MvpPresenter<NewsEditorView> {

    @NonNull
    private NewsDao database = App.getDatabase().getNewsDao();
    @NonNull
    private DbNewsItem currentNewsItem;
    @NonNull
    private Disposable disposable;

    @Override
    public void onDestroy() {
        disposable.dispose();
    }

    public void onCreateActivity(int id) {
        getNewsDetails(id);
    }

    public void onSaveOptionItemClicked(String editedTitle, @NonNull String editedAbstractx) {
        currentNewsItem.setTitle(editedTitle);
        currentNewsItem.setAbstractX(editedAbstractx);
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

    private void getNewsDetails(int id) {
        disposable = database.getRxNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItem -> {
                            currentNewsItem = newsItem;
                            getViewState().showNewsDetails(newsItem);
                        }
                );
    }

}
