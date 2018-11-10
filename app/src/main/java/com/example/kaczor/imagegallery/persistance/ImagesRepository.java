package com.example.kaczor.imagegallery.persistance;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kaczor.imagegallery.core.interfaces.IImagesRepository;
import com.example.kaczor.imagegallery.core.interfaces.IOnDataFetched;
import com.example.kaczor.imagegallery.core.interfaces.IOnRequestFinished;
import com.example.kaczor.imagegallery.core.models.Image;
import com.example.kaczor.imagegallery.factories.JsonObjectRequestFactory;
import com.example.kaczor.imagegallery.handlers.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ImagesRepository implements IImagesRepository {

    private String API_MAIN = "https://pixabay.com/api/?key=5392552-b73411f3e24de10ac998d3ecd";

    private Context context;

    public ImagesRepository(Context context) {
        this.context = context;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getImages(int page, int pageSize, IOnDataFetched<List<Image>> onRepositoryDataReturn) {
        JsonObjectRequest pixabayImageListRequest = JsonObjectRequestFactory.Create(
                Request.Method.GET,
                String.format("%s&page=%d&per_page=%d", API_MAIN, page, pageSize),
                new IOnRequestFinished() {
                    @Override
                    public void ActionOK(JSONObject jsonObject) {
                        try {
                            List<Image> images = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("hits");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                images.add(new Image(
                                        new URI(jsonArray.getJSONObject(i).getString("previewURL")),
                                        jsonArray.getJSONObject(i).getString("user")));
                            }
                            onRepositoryDataReturn.action(images);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void ActionERROR(VolleyError jsonObject) {

                    }
                });
        RequestHandler.GetInstance(this.context).AddToRequestQueue(pixabayImageListRequest);
    }
}
