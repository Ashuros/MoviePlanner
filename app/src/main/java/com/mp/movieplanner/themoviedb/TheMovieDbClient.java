package com.mp.movieplanner.themoviedb;
import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface TheMovieDbClient {
	
	public List<MovieSearchResult> searchMovies(String searchString);
	public Movie findMovie(String id) throws IOException;
	public Set<Genre> retrieveMovieGenres();
}
