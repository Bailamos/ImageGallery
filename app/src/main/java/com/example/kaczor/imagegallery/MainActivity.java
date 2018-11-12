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
    private static final int PAGE_SIZE = 100;

    @BindView(R.id.GridView)
    public GridView gridView;

    @BindView(R.id.ProgressSpinner)
    public ProgressBar progressSpinner;

    private ImageAdapter imageAdapter;

    private ImagesRepository imagesRepository = new ImagesRepository(this);

    private List<Image> images = new ArrayList<>();;

    private int currentPage = 1;

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
            this.progressSpinner.setVisibility(View.VISIBLE);
            imagesRepository.getImages(currentPage, PAGE_SIZE, new IOnRepositoryDataReturn<ImagesList>() {
                @Override
                public void onData(ImagesList data) {
                    images.addAll(data.images);
                    imageAdapter.notifyDataSetChanged();

                    onFinish();
                    checkIfThereAreMoreImages(data.totalHits);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                }

                private void checkIfThereAreMoreImages(int totalHits) {
                    if (currentPage * PAGE_SIZE >= totalHits + PAGE_SIZE) {
                        currentPage = 1;
                    }
                }

                private void onFinish() {
                    progressSpinner.setVisibility(View.INVISIBLE);
                    isLoading = false;
                    currentPage = currentPage + 1;
                }
            });
        }
    }
}
