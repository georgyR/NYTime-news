package com.androidacademy.msk.exerciseproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsItem {

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
    private String section;
    @SerializedName("subsection")
    private String subsection;
    @SerializedName("title")
    private String title;
    @SerializedName("abstract")
    private String abstractX;
    @SerializedName("url")
    private String url;
    @SerializedName("published_date")
    private String publishedDate;
    @SerializedName("multimedia")
    private List<MultimediaBean> multimedia;


    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<MultimediaBean> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediaBean> multimedia) {
        this.multimedia = multimedia;
    }

    public static class MultimediaBean {
        /**
         * url : https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-thumbStandard.jpg
         * height : 75
         * width : 75
         */

        @SerializedName("url")
        private String url;
        @SerializedName("height")
        private int height;
        @SerializedName("width")
        private int width;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
