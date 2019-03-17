package com.androidacademy.msk.exerciseproject.di.module

import android.content.Context
import com.androidacademy.msk.exerciseproject.data.database.AppDatabase
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao
import javax.inject.Inject
import javax.inject.Provider


class DbProvider @Inject constructor(private val context: Context) : Provider<NewsDao> {
    override fun get() = AppDatabase.getDatabase(context).newsDao

}