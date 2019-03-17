package com.androidacademy.msk.exerciseproject.di

import android.app.Application
import com.androidacademy.msk.exerciseproject.BuildConfig
import com.androidacademy.msk.exerciseproject.di.model.NewsId
import com.androidacademy.msk.exerciseproject.di.module.AppModule
import com.androidacademy.msk.exerciseproject.di.module.IntroModule
import com.androidacademy.msk.exerciseproject.di.module.NewsDetailsModule
import com.androidacademy.msk.exerciseproject.di.module.NewsListModule
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration


object DI {

    private const val APP_SCOPE = "APP_SCOPE"
    private const val INTRO_SCOPE = "INTRO_SCOPE"
    private const val NEWS_LIST_SCOPE = "NEWS_LIST_SCOPE"
    private const val NEW_DETAILS_SCOPE = "NEW_DETAILS_SCOPE"

    fun init() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }

    fun initAppScope(app: Application) {
        Toothpick.openScope(APP_SCOPE).installModules(AppModule(app))
    }


    fun openIntroScope(): Scope {
        return Toothpick.openScopes(APP_SCOPE, INTRO_SCOPE).apply { installModules(IntroModule()) }

    }

    fun closeIntroScope() {
        Toothpick.closeScope(INTRO_SCOPE)
    }


    fun openNewsListScope(): Scope {
        return Toothpick.openScopes(APP_SCOPE, NEWS_LIST_SCOPE).apply { installModules(NewsListModule()) }
    }

    fun closeNewsListScope() {
        Toothpick.closeScope(NEWS_LIST_SCOPE)
    }


    fun openNewsDetailsScope(newsId: NewsId): Scope {
        return Toothpick.openScopes(APP_SCOPE, NEW_DETAILS_SCOPE).apply { installModules(NewsDetailsModule(newsId)) }
    }

    fun closeNewsDetailsScope() {
        Toothpick.closeScope(NEW_DETAILS_SCOPE)
    }

}