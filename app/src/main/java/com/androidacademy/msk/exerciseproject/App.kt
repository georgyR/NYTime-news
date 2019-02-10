package com.androidacademy.msk.exerciseproject

import android.app.Application
import android.util.Log

import com.androidacademy.msk.exerciseproject.di.Injector

import java.io.IOException
import java.net.SocketException

import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

    companion object {
        private val ERROR_RX = "ERROR_RX"
    }

    override fun onCreate() {
        super.onCreate()

        Injector.init(this)

        RxJavaPlugins.setErrorHandler { e ->
            var exception = e
            if (e is UndeliverableException) {
                exception = e.cause
            }
            if (exception is SocketException || exception is IOException) {
                Log.e(ERROR_RX, "Irrelevant network problem or API that throws on cancellation", exception)
                return@setErrorHandler
            }
            if (exception is InterruptedException) {
                Log.e(ERROR_RX, "Some blocking code was interrupted by a dispose call", exception)
                return@setErrorHandler
            }
            if (exception is NullPointerException || exception is IllegalArgumentException) {
                Log.e(ERROR_RX, "That's likely a bug in the application", exception)
                return@setErrorHandler
            }
            if (exception is IllegalStateException) {
                Log.e(ERROR_RX, "That's a bug in RxJava or in a custom operator", exception)
                return@setErrorHandler
            }
            Log.e(ERROR_RX, "Undeliverable exception received, not sure what to do", exception)
        }
    }
}
