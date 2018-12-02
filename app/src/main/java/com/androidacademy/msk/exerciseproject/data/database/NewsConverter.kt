package com.androidacademy.msk.exerciseproject.data.database

import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.data.network.model.NetworkNewsItem
import com.androidacademy.msk.exerciseproject.utils.NewsDataUtils

fun toDatabase(itemList: List<NetworkNewsItem>?, section: String): List<DbNewsItem> {
    if (itemList == null) {
        return ArrayList()
    }

    val dbNewsItems = ArrayList<DbNewsItem>(itemList.size)

    for (item in itemList) {
        val dbItem = DbNewsItem(
                mainSection = section,
                section = item.section,
                title = item.title,
                abstractX = item.abstractX,
                url = item.url,
                publishedDate = item.publishedDate,
                previewImageUrl = NewsDataUtils.getPreviewImageUrl(item),
                fullsizeImageUrl = NewsDataUtils.getFullsizeImageUrl(item)
        )

        dbNewsItems.add(dbItem)
    }
    return dbNewsItems
}