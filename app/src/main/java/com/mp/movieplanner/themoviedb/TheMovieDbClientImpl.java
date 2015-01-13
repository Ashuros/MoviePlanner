package com.mp.movieplanner.themoviedb;

import android.util.Log;

import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;
import com.mp.movieplanner.themoviedb.response.GenreResultResponse;
import com.mp.movieplanner.themoviedb.response.MovieSearchResultResponse;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static com.mp.movieplanner.themoviedb.TheMovieDbURL.FIND_FEED_URL;
import static com.mp.movieplanner.themoviedb.TheMovieDbURL.SEARCH_FEED_URL;

public class TheMovieDbClientImpl implements TheMovieDbClient {

    private static final String THE_MOVIE_DB_CLIENT_TAG = TheMovieDbClientImpl.class.getSimpleName();

    private RestTemplate restTemplate;

    public TheMovieDbClientImpl() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public List<MovieSearchResult> searchMovies(String query) {
        Log.i(THE_MOVIE_DB_CLIENT_TAG, "Searching for: " + query);
        MovieSearchResultResponse response = restTemplate.getForObject(SEARCH_FEED_URL, MovieSearchResultResponse.class, query);
        return response.getResults();
    }

    @Override
    public Movie findMovie(String id) {
        Log.i(THE_MOVIE_DB_CLIENT_TAG, "Query for: " + id);
        return restTemplate.getForObject(FIND_FEED_URL, Movie.class, id);
    }

    @Override
    public Set<Genre> retrieveMovieGenres() {
        Log.i(THE_MOVIE_DB_CLIENT_TAG, "Downloading all genres");
        GenreResultResponse genres =  restTemplate.getForObject(TheMovieDbURL.GET_GENRES, GenreResultResponse.class, "");
        return genres.getGenres();
    }
}
