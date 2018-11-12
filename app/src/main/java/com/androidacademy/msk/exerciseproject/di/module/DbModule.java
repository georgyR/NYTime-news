package com.androidacademy.msk.exerciseproject.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.database.AppDatabase;
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    @NonNull
    public NewsDao provideDao(@NonNull Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        return database.getNewsDao();
    }
}
