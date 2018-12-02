package com.androidacademy.msk.exerciseproject.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_item WHERE id = :id")
    fun getRxNewsById(id: Int): Single<DbNewsItem>

    @Query("SELECT * FROM news_item WHERE main_section = :section")
    fun getNewsBySection(section: String): List<DbNewsItem>

    @Query("SELECT id FROM news_item WHERE main_section = :section")
    fun getNewsIdBySection(section: String): IntArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<DbNewsItem>)

    @Query("DELETE FROM news_item WHERE main_section = :section")
    fun deleteBySection(section: String)
}