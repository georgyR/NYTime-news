package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.model.NewsItem;
import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface NewsListView extends MvpView {

    void showProgressBar();

    void hideProgressBar();

    void showNews(@NonNull List<NewsItem> news);
}
