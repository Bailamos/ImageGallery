package com.example.kaczor.imagegallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_NUMBER_OF_IMAGES = 1000;
    private static final int PAGE_SIZE = 100;

    private static final int IMAGES_PER_ROW = 3;
    private static final int NUMBER_OF_ROWS_TO_CLEAR = 100;

    @BindView(R.id.GridView)
    public GridView gridView;

    @BindView(R.id.ProgressSpinner)
    public ProgressBar progressSpinner;

    @BindView(R.id.Debug)
    public TextView debug;

    @BindView(R.id.Test)
    public Button test;

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
                    clearIfNeeded();
                    images.addAll(data.images);
                    imageAdapter.notifyDataSetChanged();

                    onFinish();
                    checkIfThereAreMoreImages(data.totalHits);
                    debug.setText(String.format("List length: %d page_n: %d pos: %d", images.size(), currentPage, gridView.getFirstVisiblePosition()));
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                }

                private void clearIfNeeded() {
                    if (images.size() > MAX_NUMBER_OF_IMAGES) {
                        int pos = gridView.getFirstVisiblePosition();
                        images.subList(0, IMAGES_PER_ROW * NUMBER_OF_ROWS_TO_CLEAR).clear();
                        gridView.setSelection(0);
                    }
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

    @OnClick(R.id.Test)
    public void submit() {
        images.subList(0, 3).clear();
        /*try {
            images.add(new Image(new URL("https://cdn.pixabay.com/photo/2018/10/31/21/49/dresden-3786745_150.jpg")));
            images.add(new Image(new URL("https://cdn.pixabay.com/photo/2018/10/31/21/49/dresden-3786745_150.jpg")));
            images.add(new Image(new URL("https://cdn.pixabay.com/photo/2018/10/31/21/49/dresden-3786745_150.jpg")));
        } catch (Exception ex) {

        }*/
        imageAdapter.notifyDataSetChanged();
    }

}
