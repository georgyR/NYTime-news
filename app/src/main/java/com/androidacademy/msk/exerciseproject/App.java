package com.androidacademy.msk.exerciseproject;

import android.app.Application;
import android.util.Log;

import com.androidacademy.msk.exerciseproject.di.Injector;

import java.io.IOException;
import java.net.SocketException;

import javax.inject.Inject;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    private static final String ERROR_RX = "ERROR_RX";

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.init(this);

        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }
            if ((e instanceof SocketException) || (e instanceof IOException)) {
                Log.e(ERROR_RX, "Irrelevant network problem or API that throws on cancellation", e);
                return;
            }
            if (e instanceof InterruptedException) {
                Log.e(ERROR_RX, "Some blocking code was interrupted by a dispose call", e);
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                Log.e(ERROR_RX, "That's likely a bug in the application", e);
                return;
            }
            if (e instanceof IllegalStateException) {
                Log.e(ERROR_RX, "That's a bug in RxJava or in a custom operator", e);
                return;
            }
            Log.e(ERROR_RX, "Undeliverable exception received, not sure what to do", e);
        });
    }

}
