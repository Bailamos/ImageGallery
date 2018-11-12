package com.example.kaczor.imagegallery.core.models;

import java.util.List;

public class ImagesList {
    public int totalHits;
    public List<Image> images;

    public ImagesList(int totalHits, List<Image> images) {
        this.totalHits = totalHits;
        this.images = images;
    }
}
