package com.mp.movieplanner;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.mp.movieplanner.data.MovieContract;
import com.mp.movieplanner.data.service.TvService;

public class TvListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static String TAG = TvListFragment.class.getSimpleName();

    private OnTvSelectedListener callback;

    private TvService tvService;

    private TvCursorAdapter adapter;

    public interface OnTvSelectedListener {
        public void onTvSelected(long position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach(Activity)");
        tvService = ((MoviePlannerApp) activity.getApplication()).getTvService();
        try {
            callback = (OnTvSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMovieSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");
        final int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.layout.simple_list_item_activated_1
                : android.R.layout.simple_list_item_1;
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated(Bundle)");
        super.onActivityCreated(savedInstanceState);
        adapter = new TvCursorAdapter(getActivity(), null, 0, ((MoviePlannerApp) getActivity().getApplication()).getImageCache());
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        if (callback != null) {
            listView.setItemChecked(position, true);

            Cursor c = ((MovieCursorAdapter) listView.getAdapter()).getCursor();
            c.moveToPosition(position);
            long movieId = c.getLong(c.getColumnIndex(MovieContract.Movies._ID));

            callback.onTvSelected(movieId);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity()) {
            @Override
            public Cursor loadInBackground() {
                return tvService.getTvCursor();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        adapter.swapCursor(null);
    }

}
