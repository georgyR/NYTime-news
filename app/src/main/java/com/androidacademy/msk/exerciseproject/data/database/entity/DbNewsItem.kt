package com.androidacademy.msk.exerciseproject.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "news_item", indices = [Index("url", unique = true)])
data class DbNewsItem(
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,

        @ColumnInfo(name = "main_section")
        var mainSection: String?,

        @ColumnInfo(name = "section")
        var section: String?,

        @ColumnInfo(name = "title")
        var title: String?,

        @ColumnInfo(name = "abstract")
        var abstractX: String?,

        @ColumnInfo(name = "url")
        var url: String?,

        @ColumnInfo(name = "published_date")
        var publishedDate: String?,

        @ColumnInfo(name = "preview_image_url")
        var previewImageUrl: String?,

        @ColumnInfo(name = "fullsize_image_url")
        var fullsizeImageUrl: String?
)