package com.androidacademy.msk.exerciseproject;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.db.AppDatabase;
import com.androidacademy.msk.exerciseproject.network.api.NYTimesApi;
import com.androidacademy.msk.exerciseproject.network.api.NYTimesApiProvider;

import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String TAG = "rx_exception";
    private static NYTimesApi api;
    private static AppDatabase database;


    @Override
    public void onCreate() {
        super.onCreate();

        api = NYTimesApiProvider.createApi();

        database = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .build();

        RxJavaPlugins.setErrorHandler(e -> Log.d(TAG, "RxJava exception", e));
    }

    @NonNull
    public static NYTimesApi getApi() {
        return api;
    }

    @NonNull
    public static AppDatabase getDatabase() {
        return database;
    }
}
