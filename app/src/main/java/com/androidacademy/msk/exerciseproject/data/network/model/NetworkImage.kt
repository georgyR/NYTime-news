package com.androidacademy.msk.exerciseproject.data.network.model

import com.google.gson.annotations.SerializedName


class NetworkImage {
    /**
     * url : https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-thumbStandard.jpg
     * height : 75
     * width : 75
     */

    @SerializedName("url")
    val url: String? = null

    @SerializedName("height")
    val height: Int = 0

    @SerializedName("width")
    val width: Int = 0

}