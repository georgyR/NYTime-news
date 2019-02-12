package com.androidacademy.msk.exerciseproject.di.module;

import com.androidacademy.msk.exerciseproject.data.database.AppDatabase;
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.di.scope.DbScope;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @DbScope
    @NonNull
    public NewsDao provideDao(@NonNull Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        return database.getNewsDao();
    }
}
