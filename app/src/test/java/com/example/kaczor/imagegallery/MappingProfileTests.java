package com.example.kaczor.imagegallery;

import com.example.kaczor.imagegallery.core.models.ImagesList;
import com.example.kaczor.imagegallery.mapping.MappingProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class MappingProfileTests {

    @Test
    public void mapJSONToImagesList_ShouldReturnImageListWhenJSONisCorrect() throws JSONException {
        JSONObject test = new JSONObject();
        test.put("totalHits",10)
                .put("hits", createHitArray(createHit("https://www.test.com", "example_user")));

        ImagesList imagesList = MappingProfile.mapJSONToImagesList(test);

        Assert.assertEquals(imagesList.images.size(),1);
        Assert.assertEquals(imagesList.totalHits,10);
    }

    @Test
    public void mapJSONToImagesList_ShouldIgnoreElementWhenURLisInvalid() throws JSONException {
        JSONObject test = new JSONObject();
        test.put("totalHits",10)
                .put("hits", createHitArray(createHit("test", "example_user")));

        ImagesList imagesList = MappingProfile.mapJSONToImagesList(test);

        Assert.assertEquals(imagesList.images.size(),0);
    }

    @Test(expected = JSONException.class)
    public void mapJSONToImagesList_ShouldThrowExceptionWhenThereIsNoHitsArray() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("totalHits",10);

        ImagesList imagesList = MappingProfile.mapJSONToImagesList(result);
    }

    @Test(expected = JSONException.class)
    public void mapJSONToImagesList_ShouldThrowExceptionWhenThereIsTotalNoHitsProperty() throws JSONException {
        JSONObject test = new JSONObject();
        test.put("hits", createHitArray(createHit("test", "example_user")));

        ImagesList imagesList = MappingProfile.mapJSONToImagesList(test);
    }

    private JSONObject createHit(String url, String user) throws JSONException {
        JSONObject hit = new JSONObject();
        hit.put("previewURL",url);
        hit.put("user",user);
        return hit;
    }

    private JSONArray createHitArray(JSONObject hit) throws JSONException {
        JSONArray array = new JSONArray();
        array.put(hit);
        return array;
    }
}
