package com.androidacademy.msk.exerciseproject.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@Database(entities = {DbNewsItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "news.db";

    public abstract NewsDao getNewsDao();
}
