package com.androidacademy.msk.exerciseproject.data.model;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.data.Category;

import java.util.Date;

public class OfflineNewsItem {
    @NonNull
    private final String title;
    @NonNull
    private final String imageUrl;
    @NonNull
    private final Category category;
    @NonNull
    private final Date publishDate;
    @NonNull
    private final String previewText;
    @NonNull
    private final String fullText;

    public OfflineNewsItem(@NonNull String title,
                           @NonNull String imageUrl,
                           @NonNull Category category,
                           @NonNull Date publishDate,
                           @NonNull String previewText,
                           @NonNull String fullText) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.publishDate = publishDate;
        this.previewText = previewText;
        this.fullText = fullText;
    }

    @NonNull
    public String getTitle() {
        return title;
    }
    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }
    @NonNull
    public Category getCategory() {
        return category;
    }
    @NonNull
    public Date getPublishDate() {
        return publishDate;
    }
    @NonNull
    public String getPreviewText() {
        return previewText;
    }
    @NonNull
    public String getFullText() {
        return fullText;
    }
}


