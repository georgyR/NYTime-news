package com.androidacademy.msk.exerciseproject.di.module

import android.content.Context
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao
import com.androidacademy.msk.exerciseproject.data.network.api.NYTimesApi
import toothpick.config.Module


class AppModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(NewsDao::class.java).toProvider(DbProvider::class.java)
        bind(NYTimesApi::class.java).toProvider(NetworkProvider::class.java)
    }
}