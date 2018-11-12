package com.example.kaczor.imagegallery.persistance;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kaczor.imagegallery.core.interfaces.IImagesRepository;
import com.example.kaczor.imagegallery.core.interfaces.IOnRepositoryDataReturn;
import com.example.kaczor.imagegallery.core.interfaces.IOnRequestFinished;
import com.example.kaczor.imagegallery.core.models.Image;
import com.example.kaczor.imagegallery.core.models.ImagesList;
import com.example.kaczor.imagegallery.factories.JsonObjectRequestFactory;
import com.example.kaczor.imagegallery.handlers.RequestHandler;
import com.example.kaczor.imagegallery.mapping.MappingProfile;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void getImages(int page, int pageSize, IOnRepositoryDataReturn<ImagesList> onRepositoryDataReturn) {
        JsonObjectRequest pixabayImageListRequest = JsonObjectRequestFactory.Create(
                Request.Method.GET,
                String.format("%s&page=%d&per_page=%d", API_MAIN, page, pageSize),
                new IOnRequestFinished() {
                    @Override
                    public void ActionOK(JSONObject json) {
                        try {
                            onRepositoryDataReturn.onData(MappingProfile.mapJSONToImagesList(json));
                        } catch(JSONException ex) {
                            onRepositoryDataReturn.onError("Not able to load data");
                        }
                    }
                    @Override
                    public void ActionERROR(VolleyError jsonObject) {
                        onRepositoryDataReturn.onError("Not able to fetch data");
                    }
                });
        RequestHandler.GetInstance(this.context).AddToRequestQueue(pixabayImageListRequest);
    }
}
