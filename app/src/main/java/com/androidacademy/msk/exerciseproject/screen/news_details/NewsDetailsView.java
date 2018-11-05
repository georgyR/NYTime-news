package com.androidacademy.msk.exerciseproject.screen.news_details;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface NewsDetailsView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showNewsDetails(DbNewsItem newsItem);
}
