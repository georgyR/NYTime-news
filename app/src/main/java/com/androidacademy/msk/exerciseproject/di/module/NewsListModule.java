package com.androidacademy.msk.exerciseproject.di.module;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi;
import com.androidacademy.msk.exerciseproject.di.scope.ActivityScope;
import com.androidacademy.msk.exerciseproject.screen.news_list.NewsListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsListModule {

    @Provides
    @ActivityScope
    @NonNull
    public NewsListPresenter providePresenter(@NonNull NYTimesApi api, @NonNull NewsDao dao) {
        return new NewsListPresenter(api, dao);
    }
}
