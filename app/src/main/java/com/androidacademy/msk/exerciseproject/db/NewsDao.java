package com.androidacademy.msk.exerciseproject.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news_item")
    List<DbNewsItem> getAll();



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DbNewsItem> news);
}
