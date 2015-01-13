package com.mp.movieplanner.common;

import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;
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
}
