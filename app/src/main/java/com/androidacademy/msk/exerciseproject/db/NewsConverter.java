package com.androidacademy.msk.exerciseproject.db;

import android.support.annotation.NonNull;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.network.model.NetworkNewsItem;
import com.androidacademy.msk.exerciseproject.utils.DataUtils;

public class NewsConverter {

    @NonNull
    public static DbNewsItem toDatabase(@NonNull NetworkNewsItem item) {

        String id = item.getTitle() + item.getUrl();
        String previewImageUrl = DataUtils.getPreviewImageUrl(item);
        String fullsizeTamgeUrl = DataUtils.getFullsizeImageUrl(item);

        return new DbNewsItem(
                id,
                item.getSection(),
                item.getTitle(),
                item.getAbstractX(),
                item.getUrl(),
                item.getPublishedDate(),
                previewImageUrl,
                fullsizeTamgeUrl);
    }
}
