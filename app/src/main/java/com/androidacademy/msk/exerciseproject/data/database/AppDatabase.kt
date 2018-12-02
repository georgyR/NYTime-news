package com.androidacademy.msk.exerciseproject.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.androidacademy.msk.exerciseproject.data.database.dao.NewsDao
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem

/*
@Database(entities = [DbNewsItem::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}

const val DATABASE_NAME = "news.db"

fun getDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()
}*/
