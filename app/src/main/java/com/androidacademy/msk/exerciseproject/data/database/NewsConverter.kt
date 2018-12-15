package com.androidacademy.msk.exerciseproject.data.database

import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.data.network.model.NetworkNewsItem
import com.androidacademy.msk.exerciseproject.utils.NewsDataUtils

fun toDatabase(itemList: List<NetworkNewsItem>, section: String): List<DbNewsItem> {

    return itemList.map { item ->
        DbNewsItem(
                id = null,
                mainSection = section,
                section = item.section,
                title = item.title,
                abstractX = item.abstractX,
                url = item.url,
                publishedDate = item.publishedDate,
                previewImageUrl = NewsDataUtils.getPreviewImageUrl(item),
                fullsizeImageUrl = NewsDataUtils.getFullsizeImageUrl(item)
        )
    }
}