package com.androidacademy.msk.exerciseproject.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "news_item")
public class DbNewsItem {

    @ColumnInfo(name = "id")
    @PrimaryKey
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
        this.id = builder.id;
        this.mainSection = builder.mainSection;
        this.section = builder.section;
        this.title = builder.title;
        this.abstractX = builder.abstractX;
        this.url = builder.url;
        this.publishedDate = builder.publishedDate;
        this.previewImageUrl = builder.previewImageUrl;
        this.fullsizeImageUrl = builder.fullsizeImageUrl;
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

        private final int id;
        private String mainSection;
        private String section;
        private String title;
        private String abstractX;
        private String url;
        private String publishedDate;
        private String previewImageUrl;
        private String fullsizeImageUrl;

        public Builder(int id) {
            this.id = id;
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
