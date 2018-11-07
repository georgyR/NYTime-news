package com.androidacademy.msk.exerciseproject.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.network.model.NetworkNewsItem;
import com.androidacademy.msk.exerciseproject.utils.NewsDataUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsConverter {

    @NonNull
    public static List<DbNewsItem> toDatabase(@Nullable List<NetworkNewsItem> itemList,
                                              @Nullable String section) {
        if (itemList == null) {
            return new ArrayList<>();
        }

        List<DbNewsItem> dbNewsItems = new ArrayList<>(itemList.size());

        for (NetworkNewsItem item : itemList) {

            String uniqueString = item.getTitle() + item.getUrl();
            int id = uniqueString.hashCode();
            String previewImageUrl = NewsDataUtils.getPreviewImageUrl(item);
            String fullsizeImageUrl = NewsDataUtils.getFullsizeImageUrl(item);

            DbNewsItem dbItem = new DbNewsItem.Builder(id)
                    .mainSection(section)
                    .section(item.getSection())
                    .title(item.getTitle())
                    .abstractX(item.getAbstractX())
                    .url(item.getUrl())
                    .publishedDate(item.getPublishedDate())
                    .previewImageUrl(previewImageUrl)
                    .fullsizeImageUrl(fullsizeImageUrl)
                    .build();

            dbNewsItems.add(dbItem);
        }

        return dbNewsItems;
    }
}
