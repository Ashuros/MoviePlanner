package com.mp.movieplanner.data.service;

import android.database.Cursor;

import com.mp.movieplanner.model.Movie;

import java.util.List;

public interface MovieService extends Service<Movie> {

    public long saveMovie(Movie movie);

    public boolean deleteMovie(Movie movie);

    public Movie getMovie(long id);

    public List<Movie> getAllMovies();

    public Movie findMovie(String name);

    public Cursor getMovieCursor();
}
