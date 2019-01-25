package com.example.katerynastorozh.newsapi.main.model;

public class News {
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;
    public String content;

    public News() {
    }

    public News(String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }
}
