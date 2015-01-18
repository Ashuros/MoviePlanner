package com.mp.movieplanner;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.mp.movieplanner.data.MovieContract.Movies;
import com.mp.movieplanner.data.service.MovieService;

public class MovieListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    public static String TAG = MovieListFragment.class.getSimpleName();

    private OnMovieSelectedListener mCallback;
    private MovieService movieService;

    private MovieCursorAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach(Activity)");
        movieService = ((MoviePlannerApp) activity.getApplication()).getMovieService();
        try {
            mCallback = (OnMovieSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMovieSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated(Bundle)");
        super.onActivityCreated(savedInstanceState);
        adapter = new MovieCursorAdapter(getActivity(), null, 0, ((MoviePlannerApp) getActivity().getApplication()).getImageCache());
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
        if (getFragmentManager().findFragmentById(R.id.movie_details_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
        mCallback = null;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        if (mCallback != null) {
            listView.setItemChecked(position, true);
            Cursor c = ((MovieCursorAdapter) listView.getAdapter()).getCursor();
            c.moveToPosition(position);
            long movieId = c.getLong(c.getColumnIndex(Movies._ID));
            mCallback.onMovieSelected(movieId);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity()) {
            @Override
            public Cursor loadInBackground() {
                return movieService.getMovieCursor();
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

    public interface OnMovieSelectedListener {
        public void onMovieSelected(long position);
    }

}
