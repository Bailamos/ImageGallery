package com.example.kaczor.imagegallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kaczor.imagegallery.adapters.ImageAdapter;
import com.example.kaczor.imagegallery.core.interfaces.IOnRepositoryDataReturn;
import com.example.kaczor.imagegallery.core.models.Image;
import com.example.kaczor.imagegallery.core.models.ImagesList;
import com.example.kaczor.imagegallery.listeners.BasicOnScrollListener;
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

    private ImagesRepository imagesRepository = new ImagesRepository(this);

    private List<Image> images = new ArrayList<>();;

    private ImageAdapter imageAdapter;

    private int currentPage = 1;

    private int pageSize = 40;

    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        this.imageAdapter = new ImageAdapter(this, images);
        this.gridView.setAdapter(imageAdapter);

        populateGridView();
        this.gridView.setOnScrollListener(new BasicOnScrollListener(() -> {
            populateGridView();
        }));
    }

    private void  populateGridView() {
        if (!isLoading) {
            isLoading = true;
            this.progressBar.setVisibility(View.VISIBLE);
            imagesRepository.getImages(currentPage, pageSize, new IOnRepositoryDataReturn<ImagesList>() {
                @Override
                public void onData(ImagesList data) {
                    images.addAll(data.images);
                    imageAdapter.notifyDataSetChanged();

                    clearIfNeeded();
                    onFinish();
                    checkIfAnyImagesLeft(data.totalHits);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(),error, Toast.LENGTH_LONG).show();
                }

                private void checkIfAnyImagesLeft(int totalHits) {
                    if (currentPage * totalHits >= totalHits + pageSize) {
                        currentPage = 1;
                    }
                }

                private void clearIfNeeded() {
                    if (images.size() > pageSize * 20) {
                        images = images.subList(0, 100);
                    }
                }

                private void onFinish() {
                    progressBar.setVisibility(View.INVISIBLE);
                    isLoading = false;
                    currentPage = currentPage + 1;
                }
            });
        }
    }
}
