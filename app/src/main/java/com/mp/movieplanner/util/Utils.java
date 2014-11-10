package com.mp.movieplanner.util;

import com.mp.movieplanner.themoviedb.TheMovieDbClient;
import com.mp.movieplanner.themoviedb.TheMovieDbClientImpl;

public class Utils {
    private static TheMovieDbClient theMovieDbClient = new TheMovieDbClientImpl();

    public static TheMovieDbClient getTheMovieDBClient() {
        return theMovieDbClient;
    }
}
