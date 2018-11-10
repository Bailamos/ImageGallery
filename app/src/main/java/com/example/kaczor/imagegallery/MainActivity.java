package com.example.kaczor.imagegallery;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kaczor.imagegallery.adapters.ImageAdapter;
import com.example.kaczor.imagegallery.core.interfaces.IOnRequestFinished;
import com.example.kaczor.imagegallery.core.models.Image;
import com.example.kaczor.imagegallery.factories.JsonObjectRequestFactory;
import com.example.kaczor.imagegallery.handlers.RequestHandler;
import com.example.kaczor.imagegallery.listeners.InfiniteOnScrollListener;
import com.example.kaczor.imagegallery.persistance.ImagesRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private ProgressBar progressBar;
    private TextView debug;

    private ImagesRepository imagesRepository = new ImagesRepository(this);

    private List<Image> images;
    private ImageAdapter imageAdapter;

    private int currentPage = 1;
    private int pageSize = 5;
    private boolean isLoading = false;

    private String API_MAIN = "https://pixabay.com/api/?key=5392552-b73411f3e24de10ac998d3ecd";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initVariables() {
        this.gridView = findViewById(R.id.GridView);
        this.progressBar = findViewById(R.id.ProgressBar);
        this.debug = findViewById(R.id.Debug);
        this.images = new ArrayList<>();
        this.imageAdapter = new ImageAdapter(this, images);
        this.gridView.setAdapter(imageAdapter);
        this.imagesRepository = new ImagesRepository(this);


        this.progressBar.setVisibility(View.VISIBLE);
        this.imagesRepository.getImages(1, 15, (images) -> {
            this.images.addAll(images);
            this.imageAdapter.notifyDataSetChanged();
            this.progressBar.setVisibility(View.INVISIBLE);

            debug.setText(String.format("Images size %d adaptersize %d", images.size(), this.images.size()));
        });

        this.gridView.setOnScrollListener(new InfiniteOnScrollListener(
                (firstVisibleItem, visibleItemCount, totalItemCount) -> {
                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                        this.progressBar.setVisibility(View.VISIBLE);
                        this.currentPage = (currentPage + 1) % 35;

                        imagesRepository.getImages(currentPage, pageSize, (images) -> {
                            this.images.addAll(images);
                            if (this.images.size() > 20) {
                                this.images.clear();
                            }
                            this.imageAdapter.notifyDataSetChanged();
                            this.progressBar.setVisibility(View.INVISIBLE);
                        });
                    }
                }
        ));
    }
}
