package com.example.kaczor.imagegallery.core.interfaces;

import com.example.kaczor.imagegallery.core.models.Image;
import com.example.kaczor.imagegallery.core.models.ImagesList;

public interface IImagesRepository {

    Image getImage();

    void getImages(int page, int pageSize, IOnRepositoryDataReturn<ImagesList> onRepositoryDataReturn);
}
