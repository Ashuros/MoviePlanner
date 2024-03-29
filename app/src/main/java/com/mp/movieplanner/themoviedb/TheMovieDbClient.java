package com.mp.movieplanner.themoviedb;

import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;
import com.mp.movieplanner.model.Tv;
import com.mp.movieplanner.model.TvSearchResult;
import com.mp.movieplanner.themoviedb.response.Backdrop;

import java.util.List;


public interface TheMovieDbClient {

    public List<MovieSearchResult> searchMovies(String searchString);

    public Movie findMovie(String id);

    public List<Genre> retrieveMovieGenres();

    public List<TvSearchResult> searchTvs(String query);

    public Tv findTv(String id);

    public List<Genre> retrieveTvGenres();

    public List<Backdrop> getImagesForTv(long tvId);

    public List<Backdrop> getImagesForMovie(long movieId);
}
