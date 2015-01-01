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

import com.mp.movieplanner.data.MoviePlannerContract.Movies;
import com.mp.movieplanner.data.service.MovieService;

public class MovieListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    public static String TAG = MovieListFragment.class.getSimpleName();

    private OnMovieSelectedListener mCallback;
    //private ArrayAdapter<Movie> adapter;
    private MovieService movieService;

    private MovieCursorAdapter mAdapter;

    public interface OnMovieSelectedListener {
        public void onMovieSelected(long position);
    }

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");
        final int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.layout.simple_list_item_activated_1
                : android.R.layout.simple_list_item_1;

        //adapter = new ArrayAdapter<>(getActivity(), layout);
        //setListAdapter(adapter);
        //setListAdapter(mAdapter);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated(Bundle)");
        super.onActivityCreated(savedInstanceState);
        mAdapter = new MovieCursorAdapter(getActivity(), null, 0, ((MoviePlannerApp) getActivity().getApplication()).getImageCache());
        setListAdapter(mAdapter);
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
        //new GetMoviesTask().execute();
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
        // Notify the parent activity of selected item
        if (mCallback != null) {
            // Set the item as checked to be highlighted when in two-pane layout
            Log.i("CHECKABLE", String.valueOf(position));
            //getListView().setItemChecked(position, true);
            listView.setItemChecked(position, true);
            Cursor c = ((MovieCursorAdapter) listView.getAdapter()).getCursor();
            c.moveToPosition(position);
            long movieId = c.getLong(c.getColumnIndex(Movies._ID));
            Log.i("DAMN IT!", movieId + "");
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
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
