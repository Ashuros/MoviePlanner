package com.mp.movieplanner.common;

import android.app.Activity;
import android.database.Cursor;
import android.widget.Toast;

import com.mp.movieplanner.data.MovieContract;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;
import com.mp.movieplanner.model.Tv;
import com.mp.movieplanner.model.TvSearchResult;
import com.mp.movieplanner.themoviedb.TheMovieDbClient;
import com.mp.movieplanner.themoviedb.TheMovieDbClientImpl;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.mp.movieplanner.data.TvContract.Tv.BACKDROP_PATH;
import static com.mp.movieplanner.data.TvContract.Tv.FIRST_AIR_DATE;
import static com.mp.movieplanner.data.TvContract.Tv.ORIGINAL_NAME;
import static com.mp.movieplanner.data.TvContract.Tv.OVERVIEW;
import static com.mp.movieplanner.data.TvContract.Tv.POSTER_PATH;
import static com.mp.movieplanner.data.TvContract.Tv.TV_ID;
import static com.mp.movieplanner.data.TvContract.Tv.VOTE_AVERAGE;

public class Utils {
    private static TheMovieDbClient theMovieDbClient = new TheMovieDbClientImpl();

    public static TheMovieDbClient getTheMovieDBClient() {
        return theMovieDbClient;
    }

    public static List<MovieSearchResult> toMovieSearchResult(List<Movie> movies) {
        List<MovieSearchResult> movieSearchResults = new ArrayList<>(movies.size());

        for (Movie movie : movies) {
            String id = String.valueOf(movie.getMovieId());
            String title = movie.getOriginal_title();
            String releaseDate = movie.getRelease_date();
            movieSearchResults.add(new MovieSearchResult(id, title, releaseDate));
        }
        return movieSearchResults;
    }

    public static List<TvSearchResult> toTvSearchResult(List<Tv> tvs) {
        List<TvSearchResult> tvSearchResults = new ArrayList<>(tvs.size());

        for (Tv tv : tvs) {
            String id = String.valueOf(tv.getTvId());
            String title = tv.getOriginal_name();
            String firstAirDate = tv.getFirst_air_date();
            tvSearchResults.add(new TvSearchResult(id, title, firstAirDate));
        }
        return tvSearchResults;
    }

    public static void showToastById(Activity activity, int id) {
        Toast.makeText(activity, id, Toast.LENGTH_SHORT).show();
    }

    public static void showToastByIdInUiThread(final Activity activity, final int id) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Movie buildMovieFromCursor(Cursor c) {
        Movie m = null;
        if (c != null) {
            m = new Movie();
            m.setId(c.getLong(c.getColumnIndex(MovieContract.Movies._ID)));
            m.setMovieId(c.getLong(c.getColumnIndex(MovieContract.Movies.MOVIE_ID)));
            m.setOriginal_title(c.getString(c.getColumnIndex(MovieContract.Movies.ORIGINAL_TITLE)));
            m.setOverview(c.getString(c.getColumnIndex(MovieContract.Movies.OVERVIEW)));
            m.setPopularity(c.getDouble(c.getColumnIndex(MovieContract.Movies.POPULARITY)));
            m.setPoster_path(c.getString(c.getColumnIndex(MovieContract.Movies.POSTER_PATH)));
            m.setRelease_date(c.getString(c.getColumnIndex(MovieContract.Movies.RELEASE_DATE)));
        }
        return m;
    }

    public static Tv buildTvFromCursor(Cursor c) {
        Tv tv = null;
        if (c != null) {
            tv = new Tv();
            tv.setId(c.getLong(c.getColumnIndex(_ID)));
            tv.setTvId(c.getLong(c.getColumnIndex(TV_ID)));
            tv.setOriginal_name(c.getString(c.getColumnIndex(ORIGINAL_NAME)));
            tv.setPoster_path(c.getString(c.getColumnIndex(POSTER_PATH)));
            tv.setBackdrop_path(c.getString(c.getColumnIndex(BACKDROP_PATH)));
            tv.setFirst_air_date(c.getString(c.getColumnIndex(FIRST_AIR_DATE)));
            tv.setOverview(c.getString(c.getColumnIndex(OVERVIEW)));
            tv.setVote_average(c.getDouble(c.getColumnIndex(VOTE_AVERAGE)));
        }
        return tv;
    }
}
