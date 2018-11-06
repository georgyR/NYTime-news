package com.androidacademy.msk.exerciseproject.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news_item")
    List<DbNewsItem> getAll();

    @Query("SELECT * FROM news_item WHERE id = :id")
    DbNewsItem getNewsById(int id);

    @Query("SELECT * FROM news_item WHERE main_section = :section")
    List<DbNewsItem> getNewsBySection(String section);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DbNewsItem> news);

    @Query("DELETE FROM news_item WHERE main_section = :section")
    void deleteBySection(String section);

    @Update
    void updateUserItem(DbNewsItem newsItem);
}
