package com.mp.movieplanner;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mp.movieplanner.adapters.MovieCursorAdapter;
import com.mp.movieplanner.common.Utils;
import com.mp.movieplanner.data.MovieContract.Movies;
import com.mp.movieplanner.data.service.MovieService;
import com.mp.movieplanner.dialog.RemoveDialog;
import com.mp.movieplanner.model.Movie;

public class MovieListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemLongClickListener, RemoveDialog.RemoveDialogListener {

    public static String TAG = MovieListFragment.class.getSimpleName();

    private OnMovieSelectedListener mCallback;

    private MovieService movieService;
    private MovieCursorAdapter adapter;

    private Movie movieToRemove;

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
        getListView().setOnItemLongClickListener(this);
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
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
        movieToRemove = Utils.buildMovieFromCursor(cursor);
        DialogFragment dialog = RemoveDialog.newInstance(movieToRemove.getOriginal_title());
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getFragmentManager(), "REMOVE_DIALOG_TAG");
        return true;
    }

    @Override
    public void onRemoveDialogPositiveClick(DialogFragment dialog) {
        new RemoveMovieTask().execute(movieToRemove);
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

    private  class RemoveMovieTask extends AsyncTask<Movie, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Movie... movie) {
            return movieService.deleteMovie(movie[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Utils.showToastById(getActivity(), R.string.entry_deleted);
            } else {
                Utils.showToastById(getActivity(), R.string.delete_error);
            }
            getLoaderManager().restartLoader(0, null, MovieListFragment.this);
        }
    }

}
