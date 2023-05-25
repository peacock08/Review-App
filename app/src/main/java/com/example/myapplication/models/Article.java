package com.example.myapplication.models;

import java.io.Serializable;

public class Article implements Serializable {
    private String title;
    private String url;

    public Article(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
