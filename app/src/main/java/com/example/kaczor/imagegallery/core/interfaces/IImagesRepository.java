package com.example.kaczor.imagegallery.core.interfaces;

import com.example.kaczor.imagegallery.core.models.Image;

import java.util.List;

public interface IImagesRepository {

    Image getImage();

    void getImages(int page, int pageSize, IOnDataFetched<List<Image>> onRepositoryDataReturn);
}
