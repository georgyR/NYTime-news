package com.androidacademy.msk.exerciseproject.screen.news_editor;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface NewsEditorView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showNewsDetails(DbNewsItem newsItem);

    void updateTime(@NonNull String formattedTime);

    void updateDate(@NonNull String formattedDate);

}
