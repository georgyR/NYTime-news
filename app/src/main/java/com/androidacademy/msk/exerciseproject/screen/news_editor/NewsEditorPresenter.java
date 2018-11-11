package com.androidacademy.msk.exerciseproject.screen.news_editor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.androidacademy.msk.exerciseproject.screen.base.BaseNewsItemPresenter;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.arellomobile.mvp.InjectViewState;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsEditorPresenter extends BaseNewsItemPresenter<NewsEditorView> {

    private static final String DEBUG_DB_QUERY = NewsEditorPresenter.class.getSimpleName();

    @NonNull
    private DbNewsItem currentNewsItem;
    @NonNull
    private final Context appContext;

    public NewsEditorPresenter(@NonNull NewsDao dao, int id, @NonNull Context context) {
        super(dao, id);
        appContext = context;
    }

    @Override
    protected void onFirstViewAttach() {
        getNewsDetails(itemId);
    }

    public void onSaveOptionItemClicked(String editedTitle, @NonNull String editedAbstractx) {
        currentNewsItem.setTitle(editedTitle);
        currentNewsItem.setAbstractX(editedAbstractx);
        compositeDisposable.add(Completable.complete()
                .observeOn(Schedulers.io())
                .subscribe(() -> database.updateNewsItem(currentNewsItem))
        );
    }

    public void onSetTime(int hourOfDay, int minute) {
        String publishedDate = currentNewsItem.getPublishedDate();
        Date date = DateUtils.getDate(publishedDate, hourOfDay, minute);
        String timestamp = DateUtils.getTimestampFromDate(date);
        currentNewsItem.setPublishedDate(timestamp);

        String formattedTime = DateUtils.getFormattedTime(date, appContext);
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
        compositeDisposable.add(database.getRxNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newsItem -> {
                            currentNewsItem = newsItem;
                            getViewState().showNewsDetails(newsItem);
                        },
                        throwable -> Log.d(
                                DEBUG_DB_QUERY,
                                "error in getRxNewsById() query",
                                throwable))
        );
    }

    public void onTimeButtonClicked() {
        Calendar calendar = DateUtils.getCalendarFromTimestamp(currentNewsItem.getPublishedDate());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        getViewState().openTimePicker(hour, minute);
    }

    public void onDateButtonClicked() {

        Calendar calendar = DateUtils.getCalendarFromTimestamp(currentNewsItem.getPublishedDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        getViewState().openDatePicker(year, month, day);
    }
}
