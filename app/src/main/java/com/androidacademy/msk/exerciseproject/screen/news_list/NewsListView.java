package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.network.model.NewsItem;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

public interface NewsListView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showProgressBar();

    @StateStrategyType(SingleStateStrategy.class)
    void showNews(@NonNull List<NewsItem> news);

    @StateStrategyType(SingleStateStrategy.class)
    void showError();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openDetailsScreen(String url);
}
