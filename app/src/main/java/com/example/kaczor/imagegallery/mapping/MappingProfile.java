package com.example.kaczor.imagegallery.mapping;

import com.example.kaczor.imagegallery.core.models.Image;
import com.example.kaczor.imagegallery.core.models.ImagesList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MappingProfile {
    public static ImagesList mapJSONToImagesList(JSONObject json) throws JSONException {
        List<Image> images = new ArrayList<>();
        JSONArray jsonArray = json.getJSONArray("hits");
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                images.add(new Image(
                        new URL(jsonArray.getJSONObject(i).getString("previewURL"))));
            } catch (MalformedURLException e) {
                continue;
            }
        }

        int totalHits = json.getInt("totalHits");
        return  new ImagesList(totalHits, images);
    }
}
