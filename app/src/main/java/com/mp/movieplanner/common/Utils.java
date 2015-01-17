package com.mp.movieplanner.common;

import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;
import com.mp.movieplanner.model.Tv;
import com.mp.movieplanner.model.TvSearchResult;
import com.mp.movieplanner.themoviedb.TheMovieDbClient;
import com.mp.movieplanner.themoviedb.TheMovieDbClientImpl;

import java.util.ArrayList;
import java.util.List;

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
            movieSearchResults.add(new MovieSearchResult(id, title));
        }
        return movieSearchResults;
    }

    public static List<TvSearchResult> toTvSearchResult(List<Tv> tvs) {
        List<TvSearchResult> tvSearchResults = new ArrayList<>(tvs.size());

        for (Tv tv : tvs) {
            String id = String.valueOf(tv.getTvId());
            String title = tv.getOriginal_name();
            tvSearchResults.add(new TvSearchResult(id, title));
        }
        return tvSearchResults;
    }
}
