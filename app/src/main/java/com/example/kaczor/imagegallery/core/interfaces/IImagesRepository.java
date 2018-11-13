package com.example.kaczor.imagegallery.core.interfaces;

import com.example.kaczor.imagegallery.core.models.ImagesList;

public interface IImagesRepository {
    void getImages(int page, int pageSize, IOnRepositoryDataReturn<ImagesList> onRepositoryDataReturn);
}
