package com.androidacademy.msk.exerciseproject.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;

@Database(entities = {DbNewsItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "news.db";

    public abstract NewsDao getNewsDao();
}
