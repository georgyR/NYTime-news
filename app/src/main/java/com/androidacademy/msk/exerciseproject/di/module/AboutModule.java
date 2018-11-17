package com.androidacademy.msk.exerciseproject.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.di.scope.FragmentScope;
import com.androidacademy.msk.exerciseproject.screen.about.AboutPresenter;
import com.androidacademy.msk.exerciseproject.utils.IntentUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class AboutModule {

    @Provides
    @FragmentScope
    @NonNull
    public IntentUtils provideIntentUtils(@NonNull Context context) {
        return new IntentUtils(context);
    }

    @Provides
    @FragmentScope
    @NonNull
    public AboutPresenter providePresenter(@NonNull IntentUtils intentUtils) {
        return new AboutPresenter(intentUtils);
    }
}
