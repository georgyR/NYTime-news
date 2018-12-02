package com.androidacademy.msk.exerciseproject.screen.newsdetails;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface NewsDetailsView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showNewsDetails(@NonNull DbNewsItem newsItem);
}
