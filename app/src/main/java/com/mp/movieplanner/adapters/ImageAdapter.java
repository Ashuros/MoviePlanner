package com.mp.movieplanner.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.mp.movieplanner.common.ImageCache;
import com.mp.movieplanner.tasks.DownloadImageTask;
import com.mp.movieplanner.themoviedb.response.Backdrop;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Backdrop> backdrops;
    private ImageCache imageCache;

    private int width;
    private int height;

    public ImageAdapter(Context context, List<Backdrop> backdrops, ImageCache imageCache) {
        this.context = context;
        this.backdrops = backdrops;
        this.imageCache = imageCache;
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, context.getResources().getDisplayMetrics());
        height =  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getCount() {
        return backdrops.size();
    }

    @Override
    public Object getItem(int position) {
        return backdrops.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(width, height));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setTag(position);
        Backdrop backdrop = backdrops.get(position);
        String filePath = backdrop.getFilePath();
        if (filePath != null) {
            if (imageCache.get(filePath) == null) {
                new DownloadImageTask(imageCache, imageView).execute(filePath);
            } else {
                imageView.setImageBitmap(imageCache.get(filePath));
            }
        }
        return imageView;
    }

    public void addAll(List<Backdrop> backdrops) {
        this.backdrops.addAll(backdrops);
        notifyDataSetChanged();
    }
}