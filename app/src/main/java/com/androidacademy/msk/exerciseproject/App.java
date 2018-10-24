package com.androidacademy.msk.exerciseproject;

import android.app.Application;
import android.util.Log;

import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String TAG = "rx_exception";

    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(e -> Log.d(TAG, "RxJava exception", e));
    }
}
