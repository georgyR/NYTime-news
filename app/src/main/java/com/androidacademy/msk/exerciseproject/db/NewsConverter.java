package com.androidacademy.msk.exerciseproject.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.network.api.Section;
import com.androidacademy.msk.exerciseproject.network.model.NetworkNewsItem;
import com.androidacademy.msk.exerciseproject.utils.NewsDataUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsConverter {

    @NonNull
    public static DbNewsItem toDatabase(@NonNull NetworkNewsItem item, String section) {

        String uniqueString = item.getTitle() + item.getUrl();
        int id = uniqueString.hashCode();
        String previewImageUrl = NewsDataUtils.getPreviewImageUrl(item);
        String fullsizeTamgeUrl = NewsDataUtils.getFullsizeImageUrl(item);

        return new DbNewsItem(
                id,
                section,
                item.getSection(),
                item.getTitle(),
                item.getAbstractX(),
                item.getUrl(),
                item.getPublishedDate(),
                previewImageUrl,
                fullsizeTamgeUrl);
    }

    @NonNull
    public static List<DbNewsItem> toDatabase(@Nullable List<NetworkNewsItem> itemList, String section) {
        if (itemList == null) {
            return new ArrayList<>();
        }

        List<DbNewsItem> dbNewsItems = new ArrayList<>(itemList.size());

        for (NetworkNewsItem item : itemList) {

            String uniqueString = item.getTitle() + item.getUrl();
            int id = uniqueString.hashCode();
            String previewImageUrl = NewsDataUtils.getPreviewImageUrl(item);
            String fullsizeTamgeUrl = NewsDataUtils.getFullsizeImageUrl(item);

            DbNewsItem dbItem = new DbNewsItem(
                    id,
                    section,
                    item.getSection(),
                    item.getTitle(),
                    item.getAbstractX(),
                    item.getUrl(),
                    item.getPublishedDate(),
                    previewImageUrl,
                    fullsizeTamgeUrl);

            dbNewsItems.add(dbItem);
        }

        return dbNewsItems;
    }
}
