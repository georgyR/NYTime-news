package com.androidacademy.msk.exerciseproject.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.database.AppDatabase;
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao;
import com.androidacademy.msk.exerciseproject.di.scope.DbScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @DbScope
    @NonNull
    public NewsDao provideDao(@NonNull Context context) {
        AppDatabase database = Room.databaseBuilder(
                context,
                AppDatabase.class,
                AppDatabase.DATABASE_NAME)
                .build();
        return database.getNewsDao();
    }
}