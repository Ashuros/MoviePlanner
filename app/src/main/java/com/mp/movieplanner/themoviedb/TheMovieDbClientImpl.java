package com.mp.movieplanner.themoviedb;

import android.util.Log;

import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;
import com.mp.movieplanner.model.TvSearchResult;
import com.mp.movieplanner.themoviedb.response.GenreResultResponse;
import com.mp.movieplanner.themoviedb.response.MovieSearchResultResponse;
import com.mp.movieplanner.themoviedb.response.TvSearchResultResponse;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static com.mp.movieplanner.themoviedb.TheMovieDbURL.FIND_MOVIE_URL;
import static com.mp.movieplanner.themoviedb.TheMovieDbURL.SEARCH_MOVIE_URL;

public class TheMovieDbClientImpl implements TheMovieDbClient {

    private static final String TAG = TheMovieDbClientImpl.class.getSimpleName();

    private RestTemplate restTemplate;

    public TheMovieDbClientImpl() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public List<MovieSearchResult> searchMovies(String query) {
        Log.d(TAG, "Searching for: " + query);
        MovieSearchResultResponse response = restTemplate.getForObject(SEARCH_MOVIE_URL, MovieSearchResultResponse.class, query);
        return response.getResults();
    }

    @Override
    public Movie findMovie(String id) {
        Log.d(TAG, "Query for: " + id);
        return restTemplate.getForObject(FIND_MOVIE_URL, Movie.class, id);
    }

    @Override
    public Set<Genre> retrieveMovieGenres() {
        Log.d(TAG, "Downloading all genres");
        GenreResultResponse genres =  restTemplate.getForObject(TheMovieDbURL.GET_GENRES, GenreResultResponse.class, "");
        return genres.getGenres();
    }

    @Override
    public List<TvSearchResult> searchTvs(String query) {
        Log.d(TAG, "Searching TV with query: " + query);
        TvSearchResultResponse response = restTemplate.getForObject(TheMovieDbURL.SEARCH_TV_URL, TvSearchResultResponse.class, query);
        return response.getResults();
    }
}
