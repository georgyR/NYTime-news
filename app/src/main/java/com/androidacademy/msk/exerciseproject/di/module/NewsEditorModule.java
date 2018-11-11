package com.androidacademy.msk.exerciseproject.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.di.scope.PresenterScope;
import com.androidacademy.msk.exerciseproject.screen.news_editor.NewsEditorPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsEditorModule {

    private final int currentItemId;

    public NewsEditorModule(int currentItemId) {
        this.currentItemId = currentItemId;
    }

    @Provides
    @PresenterScope
    @NonNull
    public NewsEditorPresenter providePresenter(@NonNull NewsDao dao, @NonNull Context context) {
        return new NewsEditorPresenter(dao, currentItemId, context);
    }
}
