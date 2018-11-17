package com.androidacademy.msk.exerciseproject.di.module;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.di.scope.FragmentScope;
import com.androidacademy.msk.exerciseproject.screen.news_details.NewsDetailsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsDetailsModule {

    private final int currentItemId;

    public NewsDetailsModule(int currentItemId) {
        this.currentItemId = currentItemId;
    }

    @Provides
    @FragmentScope
    @NonNull
    public NewsDetailsPresenter providePresenter(NewsDao dao) {
        return new NewsDetailsPresenter(dao, currentItemId);
    }
}
