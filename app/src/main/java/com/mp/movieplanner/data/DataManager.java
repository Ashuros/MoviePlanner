package com.mp.movieplanner.data;

import java.util.List;

import android.database.Cursor;

import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;

public interface DataManager {
	//Movies
	public long saveMovie(Movie movie);
	public boolean deleteMovie(Movie movie);
	public Movie getMovie(long id);
	public List<Movie> getAllMovies();
	public Movie findMovie(String name);
	
	public Cursor getMovieCursor();
	
	//Genres
	public long saveGenre(Genre genre);
	public Genre getGenre(long id);
	public void deleteGenre(Genre entity);
	public List<Genre> getAllGenres();
	public Genre findGenre(String name);
	
	public boolean isOpen();
}
