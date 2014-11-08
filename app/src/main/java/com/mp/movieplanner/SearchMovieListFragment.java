package com.mp.movieplanner;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mp.movieplanner.data.service.MovieService;
import com.mp.movieplanner.dialog.AddMovieDialog;
import com.mp.movieplanner.model.MovieSearchResult;
import com.mp.movieplanner.util.Utils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SearchMovieListFragment extends ListFragment implements
        OnItemLongClickListener, AddMovieDialog.NoticeDialogListener {

    public static final String TAG = "MOVIE_LIST_FRAG";

    private static final String TAG_DIALOG = "ADD_DIALOG_TAG";

    private OnSearchElementSelectedListener mCallback;
    private ArrayAdapter<MovieSearchResult> adapter;

    private MoviePlannerApp mApp;
    private MovieService movieService;

    private MovieSearchResult movieToAdd;

    public interface OnSearchElementSelectedListener {
        public void onSearchElementSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach(Activity)");
        try {
            mCallback = (OnSearchElementSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSearchElementSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate(Bundle)");

        final int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        adapter = new ArrayAdapter<>(getActivity(), layout);
        setListAdapter(adapter);

        setRetainInstance(true);

        mApp = (MoviePlannerApp) getActivity().getApplication();
        movieService = mApp.getMovieService();
        new SearchMovieTask().execute(getArguments().getString("QUERY"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated(Bundle)");

        getListView().setLongClickable(true);
        getListView().setOnItemLongClickListener(this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        movieToAdd = (MovieSearchResult) parent.getItemAtPosition(position);
        DialogFragment dialog = AddMovieDialog.newInstance(movieToAdd.getTitle());
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), TAG_DIALOG);
        return true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        new AddMovieToDatabase().execute(movieToAdd);
    }

    private void showToast(int id) {
        Toast.makeText(getActivity(),
                getString(id),
                Toast.LENGTH_LONG).show();
    }

    // Seperate task to add movie to database based on clicked Item on the List
    // - movieToAdd
    private class AddMovieToDatabase extends AsyncTask<MovieSearchResult, Void, Long> {
        @Override
        protected Long doInBackground(MovieSearchResult... movies) {
            try {
                return movieService.saveMovie(Utils.getJsonParser().find(movies[0].getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long movieId) {
            if (movieId != null && movieId != 0) {
                adapter.remove(movieToAdd);
                adapter.notifyDataSetChanged();
                showToast(R.string.search_movie_saved);
            } else {
                showToast(R.string.search_error_saving_movie);
            }
        }
    }

    // Inner seperate thread class which performs search for QUERY
    private class SearchMovieTask extends AsyncTask<String, Void, List<MovieSearchResult>> {
        @Override
        protected List<MovieSearchResult> doInBackground(String... query) {
            if (mApp.isConnectionPresent()) {
                try {
                    return Utils.getJsonParser().search(query[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(List<MovieSearchResult> movies) {
            if (movies.size() != 0) {
                adapter.clear();
                for (MovieSearchResult movie : movies) {
                    adapter.add(movie);
                }
                adapter.notifyDataSetChanged();
            } else {
                showToast(R.string.search_error_retrieving_data);
            }
        }
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
