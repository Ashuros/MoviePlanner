package com.mp.movieplanner.themoviedb;

public class TheMovieDbURL {
    static final String API_KEY = "api_key=dd94fa84a437364111702da7d2979812";

    static final String SEARCH_FEED_URL = "http://api.themoviedb.org/3/search/movie?page=1&query={query}&" + API_KEY;

    static final String FIND_FEED_URL = "http://api.themoviedb.org/3/movie/";

    static final String GET_GENRES = "http://api.themoviedb.org/3/genre/movie/list?" + API_KEY;
}
