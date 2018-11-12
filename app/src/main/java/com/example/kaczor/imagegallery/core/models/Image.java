package com.example.kaczor.imagegallery.core.models;

import java.net.URL;

public class Image {
    private URL link;
    private String author;

    public Image (URL link) {
        this.link = link;
        this.author = "No author";
    }

    public Image (URL link, String author) {
        this.link = link;
        this.author = author;
    }

    public URL getLink() {
        return this.link;
    }

    public String getAuthor() {
        return this.author;
    }
}
