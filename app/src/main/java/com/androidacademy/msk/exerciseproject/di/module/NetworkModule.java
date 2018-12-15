package com.androidacademy.msk.exerciseproject.di.module;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi;
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApiProviderKt;
import com.androidacademy.msk.exerciseproject.di.scope.NetworkScope;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @NetworkScope
    @NonNull
    public NYTimesApi provideApi() {
        return NYTimesApiProviderKt.createApi();
    }

}
