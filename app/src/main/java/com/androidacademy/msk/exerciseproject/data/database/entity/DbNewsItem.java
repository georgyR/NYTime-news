package com.androidacademy.msk.exerciseproject.data.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "news_item", indices = {@Index(value = "url", unique = true)})
public class DbNewsItem {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "main_section")
    @Nullable
    private String mainSection;

    @ColumnInfo(name = "section")
    @Nullable
    private String section;

    @ColumnInfo(name = "title")
    @Nullable
    private String title;

    @ColumnInfo(name = "abstract")
    @Nullable
    private String abstractX;

    @ColumnInfo(name = "url")
    @Nullable
    private String url;

    @ColumnInfo(name = "published_date")
    @Nullable
    private String publishedDate;

    @ColumnInfo(name = "preview_image_url")
    @Nullable
    private String previewImageUrl;

    @ColumnInfo(name = "fullsize_image_url")
    @Nullable
    private String fullsizeImageUrl;

    public DbNewsItem() {
    }

    @Ignore
    private DbNewsItem(DbNewsItem.Builder builder) {
        id = builder.id;
        mainSection = builder.mainSection;
        section = builder.section;
        title = builder.title;
        abstractX = builder.abstractX;
        url = builder.url;
        publishedDate = builder.publishedDate;
        previewImageUrl = builder.previewImageUrl;
        fullsizeImageUrl = builder.fullsizeImageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getMainSection() {
        return mainSection;
    }

    public void setMainSection(@Nullable String mainSection) {
        this.mainSection = mainSection;
    }

    @Nullable
    public String getSection() {
        return section;
    }

    public void setSection(@Nullable String section) {
        this.section = section;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(@Nullable String abstractX) {
        this.abstractX = abstractX;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Nullable
    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(@Nullable String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Nullable
    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(@Nullable String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    @Nullable
    public String getFullsizeImageUrl() {
        return fullsizeImageUrl;
    }

    public void setFullsizeImageUrl(@Nullable String fullsizeImageUrl) {
        this.fullsizeImageUrl = fullsizeImageUrl;
    }

    public static class Builder {

        private int id;
        private String mainSection;
        private String section;
        private String title;
        private String abstractX;
        private String url;
        private String publishedDate;
        private String previewImageUrl;
        private String fullsizeImageUrl;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder mainSection(@Nullable String mainSection) {
            this.mainSection = mainSection;
            return this;
        }

        public Builder section(@Nullable String section) {
            this.section = section;
            return this;
        }

        public Builder title(@Nullable String title) {
            this.title = title;
            return this;
        }

        public Builder abstractX(@Nullable String abstractX) {
            this.abstractX = abstractX;
            return this;
        }

        public Builder url(@Nullable String url) {
            this.url = url;
            return this;
        }

        public Builder publishedDate(@Nullable String publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder previewImageUrl(@Nullable String previewImageUrl) {
            this.previewImageUrl = previewImageUrl;
            return this;
        }

        public Builder fullsizeImageUrl(@Nullable String fullsizeImageUrl) {
            this.fullsizeImageUrl = fullsizeImageUrl;
            return this;
        }

        public DbNewsItem build() {
            return new DbNewsItem(this);
        }

    }
}
