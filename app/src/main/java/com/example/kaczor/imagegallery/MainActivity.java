package com.example.kaczor.imagegallery;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kaczor.imagegallery.adapters.ImageAdapter;
import com.example.kaczor.imagegallery.core.models.Image;
import com.example.kaczor.imagegallery.listeners.InfiniteOnScrollListener;
import com.example.kaczor.imagegallery.persistance.ImagesRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.GridView)
    public GridView gridView;

    @BindView(R.id.ProgressBar)
    public ProgressBar progressBar;

    @BindView(R.id.Debug)
    public TextView debug;

    private ImagesRepository imagesRepository = new ImagesRepository(this);

    private List<Image> images = new ArrayList<>();;

    private ImageAdapter imageAdapter;

    private int currentPage = 1;

    private int pageSize = 20;

    private boolean isLoading = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {
        this.imageAdapter = new ImageAdapter(this, images);
        this.gridView.setAdapter(imageAdapter);

        populateGridView();
        this.gridView.setOnScrollListener(new InfiniteOnScrollListener((int firstVisibleItem, int visibleItemCount, int totalItemCount) -> {
            if (firstVisibleItem + visibleItemCount >= totalItemCount)
                populateGridView();
        }));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void  populateGridView() {
        if (!isLoading) {
            isLoading = true;
            this.progressBar.setVisibility(View.VISIBLE);
            imagesRepository.getImages(currentPage, pageSize, (data) -> {
                this.images.addAll(data.images);
                this.imageAdapter.notifyDataSetChanged();

                this.debug.setText(String.format("Ilosc elementow w tablicy: %d", this.images.size()));
                this.progressBar.setVisibility(View.INVISIBLE);
                isLoading = false;
                this.currentPage = (currentPage + 1);

                if (this.currentPage * data.totalHits >= data.totalHits + this.pageSize) {
                    this.currentPage = 1;
                }

            });
        }
    }
}
