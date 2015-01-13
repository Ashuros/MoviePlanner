package com.mp.movieplanner;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.mp.movieplanner.common.ImageCache;

public class TvCursorAdapter extends CursorAdapter {

    private final LayoutInflater inflater;
    private final ImageCache cache;

    public TvCursorAdapter(Context context, Cursor c, int flags, ImageCache cache) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cache = cache;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return null;
    }
}
