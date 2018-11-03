package com.androidacademy.msk.exerciseproject.network.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class NetworkImage {
    /**
     * url : https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-thumbStandard.jpg
     * height : 75
     * width : 75
     */

    @SerializedName("url")
    @Nullable
    private String url;

    @SerializedName("height")
    private int height;

    @SerializedName("width")
    private int width;

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
