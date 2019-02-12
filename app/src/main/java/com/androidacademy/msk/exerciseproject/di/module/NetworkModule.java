package com.androidacademy.msk.exerciseproject.di.module;

import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi;
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApiProvider;
import com.androidacademy.msk.exerciseproject.di.scope.NetworkScope;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @NetworkScope
    @NonNull
    public NYTimesApi provideApi() {
        return NYTimesApiProvider.createApi();
    }

}
