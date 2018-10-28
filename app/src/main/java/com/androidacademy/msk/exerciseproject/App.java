package com.androidacademy.msk.exerciseproject;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.network.api.NYTimesApi;
import com.androidacademy.msk.exerciseproject.network.api.NYTimesApiProvider;

import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String TAG = "rx_exception";
    private static NYTimesApi api;

    @Override
    public void onCreate() {
        super.onCreate();

        api = NYTimesApiProvider.createApi();

        RxJavaPlugins.setErrorHandler(e -> Log.d(TAG, "RxJava exception", e));
    }

    @NonNull
    public static NYTimesApi getApi() {
        return api;
    }
}
