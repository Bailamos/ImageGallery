package com.example.kaczor.imagegallery.core.interfaces;

public interface IOnRepositoryDataReturn<T> {
    void onData(T data);

    void onError(String error);
}
