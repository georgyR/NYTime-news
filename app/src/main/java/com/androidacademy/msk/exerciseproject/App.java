package com.androidacademy.msk.exerciseproject;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.data.database.AppDatabase;
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi;
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApiProvider;

import java.io.IOException;
import java.net.SocketException;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String ERROR_RX = "ERROR_RX";

    private static NYTimesApi api;
    private static AppDatabase database;

    private static Context applicationContext;

    @NonNull
    public static NYTimesApi getApi() {
        return api;
    }

    @NonNull
    public static AppDatabase getDatabase() {
        return database;
    }

    @NonNull
    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = this;

        api = NYTimesApiProvider.createApi();

        database = AppDatabase.getDatabase(this);

        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e.getCause();
            }
            if ((e instanceof SocketException) || (e instanceof IOException)) {
                Log.d(ERROR_RX, "Irrelevant network problem or API that throws on cancellation", e);
                return;
            }
            if (e instanceof InterruptedException) {
                Log.d(ERROR_RX, "Some blocking code was interrupted by a dispose call", e);
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                Log.d(ERROR_RX, "That's likely a bug in the application", e);
                return;
            }
            if (e instanceof IllegalStateException) {
                Log.d(ERROR_RX, "That's a bug in RxJava or in a custom operator", e);
                return;
            }
            Log.d(ERROR_RX, "Undeliverable exception received, not sure what to do", e);
        });
    }

}
