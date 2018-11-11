package com.androidacademy.msk.exerciseproject.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.di.scope.DbScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context appContext;

    public AppModule(@NonNull Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    @DbScope
    @NonNull
    public Context provideContext() {
        return appContext;
    }
}
