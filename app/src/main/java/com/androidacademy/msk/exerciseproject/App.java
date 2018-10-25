package com.androidacademy.msk.exerciseproject;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.data.network.ApiEndpoint;
import com.androidacademy.msk.exerciseproject.data.network.NYTimesApi;

import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String TAG = "rx_exception";
    private static ApiEndpoint apiEndpoint;

    @Override
    public void onCreate() {
        super.onCreate();

        apiEndpoint = NYTimesApi.createApiEndpoint();

        RxJavaPlugins.setErrorHandler(e -> Log.d(TAG, "RxJava exception", e));
    }

    @NonNull
    public static ApiEndpoint getApiEndpoint() {
        return apiEndpoint;
    }
}
