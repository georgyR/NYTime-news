package com.androidacademy.msk.exerciseproject;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.db.AppDatabase;
import com.androidacademy.msk.exerciseproject.network.api.NYTimesApi;
import com.androidacademy.msk.exerciseproject.network.api.NYTimesApiProvider;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    public static final String UI_DEBUG_TAG = "UI_DEBUG_TAG";
    private static final String RX_DEBUG_TAG = "RX_DEBUG_TAG";
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

        database = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .build();

        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e.getCause();
            }
            if ((e instanceof SocketException) || (e instanceof IOException)) {
                Log.d(RX_DEBUG_TAG, "Irrelevant network problem or API that throws on cancellation", e);
                return;
            }
            if (e instanceof InterruptedException) {
                Log.d(RX_DEBUG_TAG, "Some blocking code was interrupted by a dispose call", e);
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                Log.d(RX_DEBUG_TAG, "That's likely a bug in the application", e);
                return;
            }
            if (e instanceof IllegalStateException) {
                Log.d(RX_DEBUG_TAG, "That's a bug in RxJava or in a custom operator", e);
                return;
            }
            Log.d(RX_DEBUG_TAG, "Undeliverable exception received, not sure what to do", e);
        });
    }

}
