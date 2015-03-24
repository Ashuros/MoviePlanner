package com.mp.movieplanner.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mp.movieplanner.R;
import com.mp.movieplanner.common.ImageCache;
import com.mp.movieplanner.data.MovieContract.Movies;
import com.mp.movieplanner.tasks.DownloadListImageTask;

public class MovieCursorAdapter extends CursorAdapter {

    private final String TAG = MovieCursorAdapter.class.getSimpleName();

    private final LayoutInflater mInflater;
    private final ImageCache mCache;


    public MovieCursorAdapter(Context context, Cursor c, int flags, ImageCache cache) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCache = cache;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        populateView(view, cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);

        TextView originalTitle = (TextView) view.findViewById(R.id.list_item_original_title);
        ImageView imageView = (ImageView) view.findViewById(R.id.list_item_image);

        ViewHolder holder = new ViewHolder(originalTitle, imageView);
        view.setTag(holder);

        populateView(view, cursor);

        return view;
    }

    public void populateView(final View listItem, final Cursor c) {
        ViewHolder holder = (ViewHolder) listItem.getTag();

        long id = c.getLong(c.getColumnIndex(Movies._ID));
        String originalTitle = c.getString(c.getColumnIndex(Movies.ORIGINAL_TITLE));
        String thumbUrl = c.getString(c.getColumnIndex(Movies.POSTER_PATH));

        holder.text.setText(originalTitle);

        holder.image.setImageDrawable(null);
        holder.image.setTag(id);

        if (thumbUrl != null) {
            if (mCache.get(thumbUrl) == null) {
                new DownloadListImageTask(mCache, holder.image, id).execute(thumbUrl);
            } else {
                holder.image.setImageBitmap(mCache.get(thumbUrl));
            }
        }
    }

    private static class ViewHolder {
        protected final TextView text;
        protected final ImageView image;

        public ViewHolder(TextView text, ImageView image) {
            this.text = text;
            this.image = image;
        }
    }

}
