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

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.image_grid_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Picasso
                .get()
                .load(images.get(position).getLink().toString())
                .placeholder(R.mipmap.ic_spinner_foreground)
                .into(holder.imageView);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.PreviewView) ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
