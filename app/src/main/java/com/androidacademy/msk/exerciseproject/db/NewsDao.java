package com.androidacademy.msk.exerciseproject.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news_item WHERE id = :id")
    Single<DbNewsItem> getRxNewsById(int id);

    @Query("SELECT * FROM news_item WHERE main_section = :section")
    List<DbNewsItem> getNewsBySection(String section);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DbNewsItem> news);

    @Query("DELETE FROM news_item WHERE main_section = :section")
    void deleteBySection(String section);

    @Update
    void updateNewsItem(DbNewsItem newsItem);

    @Query("DELETE FROM news_item WHERE id = :id")
    void deleteNewsItemById(int id);
}
