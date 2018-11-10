package com.example.kaczor.imagegallery.core.models;

import java.net.URI;

public class Image {
    private URI link;
    private String author;

    public Image (URI link) {
        this.link = link;
        this.author = "No author";
    }

    public Image (URI link, String author) {
        this.link = link;
        this.author = author;
    }

    public URI getLink() {
        return this.link;
    }

    public String getAuthor() {
        return this.author;
    }
}
