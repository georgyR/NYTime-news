package com.androidacademy.msk.exerciseproject.di.module;

import com.androidacademy.msk.exerciseproject.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsItemIdModule {

    private final int currentItemId;

    public NewsItemIdModule(int currentItemId) {
        this.currentItemId = currentItemId;
    }

    @Provides
    @FragmentScope
    public int provideNewsItemId() {
        return currentItemId;
    }
}
