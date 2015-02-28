package com.mp.movieplanner;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;

import com.mp.movieplanner.common.Utils;
import com.mp.movieplanner.data.service.MovieService;
import com.mp.movieplanner.dialog.AddDialog;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;

import java.util.Collections;
import java.util.List;

public class SearchMovieListFragment extends ListFragment implements
        OnItemLongClickListener, AddDialog.AddDialogListener {

    public static final String TAG = SearchMovieListFragment.class.getSimpleName();

    private OnSearchMovieSelectedListener mCallback;
    private ArrayAdapter<MovieSearchResult> adapter;

    private MoviePlannerApp mApp;
    private MovieService movieService;

    private MovieSearchResult movieToAdd;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach(Activity)");
        try {
            mCallback = (OnSearchMovieSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSearchElementSelectedListener");
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
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        movieToAdd = (MovieSearchResult) parent.getItemAtPosition(position);
        new FetchMovieInfo().execute(movieToAdd);
        return true;
    }

    @Override
    public void onAddDialogPositiveClick(DialogFragment dialog) {
        new AddMovieToDatabase(movieToAdd).execute();
    }

    public interface OnSearchMovieSelectedListener {
        public void onSearchMovieSelected(int position);
    }

    private class AddMovieToDatabase extends AsyncTask<Void, Void, Long> {

        private final MovieSearchResult movieSearchResult;

        public AddMovieToDatabase(MovieSearchResult movie) {
            this.movieSearchResult = movie;
        }

        @Override
        protected Long doInBackground(Void... movies) {
            if (mApp.isConnectionPresent()) {
                Movie movie = Utils.getTheMovieDBClient().findMovie(movieSearchResult.getId());
                return movieService.saveMovie(movie);
            }
            Utils.showToastByIdInUiThread(getActivity(), R.string.search_network_unavailable);
            return 0L;
        }

        @Override
        protected void onPostExecute(Long movieId) {
            if (movieId != 0) {
                adapter.remove(movieSearchResult);
                adapter.notifyDataSetChanged();
                Utils.showToastById(getActivity(), R.string.search_movie_saved);
            } else {
                Utils.showToastById(getActivity(), R.string.search_error_saving_movie);
            }
        }
    }

    private class SearchMovieTask extends AsyncTask<String, Void, List<MovieSearchResult>> {
        @Override
        protected List<MovieSearchResult> doInBackground(String... query) {
            if (mApp.isConnectionPresent()) {
                return Utils.getTheMovieDBClient().searchMovies(query[0]);
            }
            return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(List<MovieSearchResult> movies) {
            if (movies.isEmpty()) {
                Utils.showToastById(getActivity(), R.string.search_error_retrieving_data);
                ((Activity) mCallback).finish();
                return;
            }
            movies = filterMoviesNotPresentInDatabase(movies);
            adapter.clear();
            for (MovieSearchResult movie : movies) {
                adapter.add(movie);
            }
            adapter.notifyDataSetChanged();
        }

        private List<MovieSearchResult> filterMoviesNotPresentInDatabase(List<MovieSearchResult> movies) {
            List<Movie> allMovies = movieService.getAllMovies();
            List<MovieSearchResult> dbMovies = Utils.toMovieSearchResult(allMovies);
            movies.removeAll(dbMovies);
            return movies;
        }
    }

    private class FetchMovieInfo extends AsyncTask<MovieSearchResult, Void, Movie> {

        @Override
        protected Movie doInBackground(MovieSearchResult... movies) {
            if (mApp.isConnectionPresent()) {
                return Utils.getTheMovieDBClient().findMovie(movies[0].getId());
            }
            Utils.showToastByIdInUiThread(getActivity(), R.string.search_network_unavailable);
            return null;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            if (mApp.isConnectionPresent()) {
                DialogFragment dialog = AddDialog.newInstance(movie.getOriginal_title(), movie.getOverview(), movie.getRelease_date());
                dialog.setTargetFragment(SearchMovieListFragment.this, 0);
                dialog.show(getActivity().getFragmentManager(), "ADD_DIALOG_TAG");
            }
        }
    }
}
