package com.androidacademy.msk.exerciseproject.screen.news_editor;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface NewsEditorView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showNewsDetails(DbNewsItem newsItem);

    @StateStrategyType(AddToEndStrategy.class)
    void updateTime(@NonNull String formattedTime);

    @StateStrategyType(AddToEndStrategy.class)
    void updateDate(@NonNull String formattedDate);

    @StateStrategyType(AddToEndStrategy.class)
    void openTimePicker(int hour, int minute);

    @StateStrategyType(AddToEndStrategy.class)
    void openDatePicker(int year, int month, int day);
}
