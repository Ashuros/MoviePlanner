package com.mp.movieplanner;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mp.movieplanner.data.MoviePlannerContract.Movies;
import com.mp.movieplanner.tasks.DownloadListItemTask;
import com.mp.movieplanner.util.ImageCache;

public class MovieCursorAdapter extends CursorAdapter {

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
        View view = mInflater.inflate(R.layout.movie_list_item, parent, false);

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

        Log.i("MovieCursorAdapter", "" + id);

        holder.text.setText(id + " " + originalTitle);

        holder.image.setImageDrawable(null);
        holder.image.setTag(id);

        if (thumbUrl != null) {
            if (mCache.get(thumbUrl) == null) {
                new DownloadListItemTask(mCache, holder.image, id).execute(thumbUrl);
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
