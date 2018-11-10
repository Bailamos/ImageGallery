package com.example.kaczor.imagegallery.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaczor.imagegallery.R;
import com.example.kaczor.imagegallery.core.models.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private final List<Image> images;

    public ImageAdapter(Context context, List<Image> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_grid_item, null);

        ImageView imageView = view.findViewById(R.id.PreviewView);
        Picasso
                .get()
                .load(images.get(position).getLink().toString())
                .placeholder(R.mipmap.ic_spinner_foreground)
                .into(imageView);

        TextView descriptionView = view.findViewById(R.id.DescriptionView);
        descriptionView.setText("Author: " + images.get(position).getAuthor());
        return view;
    }
}
