package com.androidacademy.msk.exerciseproject.network.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkNewsItem {

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
    @Nullable
    private String section;

    @SerializedName("subsection")
    @Nullable
    private String subsection;

    @SerializedName("title")
    @Nullable
    private String title;

    @SerializedName("abstract")
    @Nullable
    private String abstractX;

    @SerializedName("url")
    @Nullable
    private String url;

    @SerializedName("published_date")
    @Nullable
    private String publishedDate;

    @SerializedName("multimedia")
    @Nullable
    private List<NetworkImage> multimedia;


    @Nullable
    public String getSection() {
        return section;
    }

    public void setSection(@Nullable String section) {
        this.section = section;
    }

    @Nullable
    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(@Nullable String subsection) {
        this.subsection = subsection;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getAbstractX() {
        return abstractX;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    public void setAbstractX(@Nullable String abstractX) {
        this.abstractX = abstractX;
    }

    @Nullable
    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(@Nullable String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Nullable
    public List<NetworkImage> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(@Nullable List<NetworkImage> multimedia) {
        this.multimedia = multimedia;
    }

}
