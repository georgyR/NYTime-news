package com.androidacademy.msk.exerciseproject.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.Storage;
import com.androidacademy.msk.exerciseproject.di.scope.ActivityScope;
import com.androidacademy.msk.exerciseproject.screen.intro.IntroPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class IntroModule {

    @Provides
    @ActivityScope
    @NonNull
    public IntroPresenter providePresenter(@NonNull Storage storage) {
        return new IntroPresenter(storage);
    }

    @Provides
    @ActivityScope
    @NonNull
    public Storage provideStorage(@NonNull Context context) {
        return new Storage(context);
    }

}
