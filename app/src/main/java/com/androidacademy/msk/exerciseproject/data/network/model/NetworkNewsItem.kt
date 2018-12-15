package com.androidacademy.msk.exerciseproject.data.network.model

import com.google.gson.annotations.SerializedName

class NetworkNewsItem {

    /**
     * section : New York
     * subsection :
     * url : https://www.nytimes.com/2018/10/23/world/middleeast/khashoggi-crown-prince-erdogan.html
     * title : Pipe Bombs Sent to Hillary Clinton, Barack Obama and CNN Offices
     * abstract : The similar devices sparked an intense investigation into whether a bomber is going after people who have often faced barbed right-wing criticism.
     * published_date : 2018-10-24T09:10:56-04:00
     * multimedia : [{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"},{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-thumbLarge.jpg","format":"thumbLarge","height":150,"width":150,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"},{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-articleInline.jpg","format":"Normal","height":127,"width":190,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"},{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-mediumThreeByTwo210.jpg","format":"mediumThreeByTwo210","height":140,"width":210,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"},{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-superJumbo.jpg","format":"superJumbo","height":1366,"width":2048,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"}]
     */


    @SerializedName("section")
    val section: String? = null

    @SerializedName("subsection")
    val subsection: String? = null

    @SerializedName("title")
    val title: String? = null

    @SerializedName("abstract")
    val abstractX: String? = null

    @SerializedName("url")
    val url: String? = null

    @SerializedName("published_date")
    val publishedDate: String? = null

    @SerializedName("multimedia")
    val multimedia: List<NetworkImage> = emptyList()

}
