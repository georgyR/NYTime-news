package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

public interface NewsListView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showProgressBar();

    @StateStrategyType(SingleStateStrategy.class)
    void showNews(@NonNull List<DbNewsItem> news);

    @StateStrategyType(SingleStateStrategy.class)
    void showError();

    @StateStrategyType(SingleStateStrategy.class)
    void showEmptyView();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openDetailsScreen(int id);

    @StateStrategyType(AddToEndStrategy.class)
    void updateCertainNewsItemInList(@NonNull DbNewsItem item, int position);

    @StateStrategyType(AddToEndStrategy.class)
    void deleteNewsItemInList(int position);
}
