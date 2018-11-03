package com.androidacademy.msk.exerciseproject.utils;

import android.support.annotation.Nullable;
import android.view.View;

import com.androidacademy.msk.exerciseproject.network.model.NetworkNewsItem;
import com.bumptech.glide.Glide;

public class DataUtils {

    private DataUtils() {
        throw new UnsupportedOperationException("There should be no class instance");
    }

    @Nullable
    public static String getPreviewImageUrl(NetworkNewsItem item) {
        if (item.getMultimedia() != null && item.getMultimedia().size() > 1) {
            // Get the position of the best quality preview image.
            // It is the second position from the end of a list.
            int previewImagePosition = item.getMultimedia().size() - 2;
            return item.getMultimedia().get(previewImagePosition).getUrl();
        }
        return null;
    }

    @Nullable
    public static String getFullsizeImageUrl(NetworkNewsItem item) {
        if (item.getMultimedia() != null && item.getMultimedia().size() > 1) {
            // Get the position of the fullsize image. It is the latest position a list.
            int previewImagePosition = item.getMultimedia().size() - 1;
            return item.getMultimedia().get(previewImagePosition).getUrl();
        }
        return null;
    }
}
