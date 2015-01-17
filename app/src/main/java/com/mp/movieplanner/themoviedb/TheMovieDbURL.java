package com.mp.movieplanner.themoviedb;

public final class TheMovieDbURL {

    private TheMovieDbURL() {
    }

    static final String API_KEY = "api_key=dd94fa84a437364111702da7d2979812";

    static final String SEARCH_MOVIE_URL = "http://api.themoviedb.org/3/search/movie?page=1&query={query}&" + API_KEY;

    static final String FIND_MOVIE_URL = "http://api.themoviedb.org/3/movie/{id}?" + API_KEY;

    static final String GET_GENRES = "http://api.themoviedb.org/3/genre/movie/list?" + API_KEY;

    static final String SEARCH_TV_URL = "http://api.themoviedb.org/3/search/tv?page=1&query={query}&" + API_KEY;

    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";

    public static final String IMAGE_SIZE_W92 = "w92/";

    public static final String IMAGE_SIZE_W154 = "w154/";
}
