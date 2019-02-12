package com.androidacademy.msk.exerciseproject.data.database

import NewsDataUtils
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.data.network.model.NetworkNewsItem
import com.androidacademy.msk.exerciseproject.utils.EMPTY

object NewsConverter {

    fun toDatabase(itemList: List<NetworkNewsItem>, section: String): List<DbNewsItem> {
        return itemList.map { item ->
            DbNewsItem(
                    id = 0,
                    mainSection = section,
                    section = item.section ?: String.EMPTY,
                    title = item.title ?: String.EMPTY,
                    abstractX = item.abstractX ?: String.EMPTY,
                    url = item.url ?: String.EMPTY,
                    publishedDate = item.publishedDate ?: String.EMPTY,
                    previewImageUrl = NewsDataUtils.getPreviewImageUrl(item),
                    fullsizeImageUrl = NewsDataUtils.getFullsizeImageUrl(item)
            )
        }
    }

}