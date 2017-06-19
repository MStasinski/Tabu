package com.michal_stasinski.tabu.Menu.Models;

/**
 * Created by win8 on 26.03.2017.
 */

public class NewsItem {
    private String date;
    private String title;
    private String url;
    private String rank;
    private String news;
    private String imageUrl;

    public NewsItem(){}

    public  NewsItem(String title, String news, String imageUrl, String date, String rank) {
        this.title = title;
        this.news = news;
        this.date = date;
        this.rank = rank;
        this.imageUrl = imageUrl;

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
