package com.example.kaczor.imagegallery.factories;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kaczor.imagegallery.core.interfaces.IOnRequestFinished;


import java.util.HashMap;
import java.util.Map;

public class JsonObjectRequestFactory {
    public static JsonObjectRequest Create(int method, String url, IOnRequestFinished onRequestFinished) {
        return new JsonObjectRequest(method, url, null,
                response -> onRequestFinished.ActionOK(response),
                error -> onRequestFinished.ActionERROR(error));

    }

}
