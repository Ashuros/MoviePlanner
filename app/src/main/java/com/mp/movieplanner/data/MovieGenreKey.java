package com.mp.movieplanner.data;

public class MovieGenreKey {
	private final long movieId;
	private final long genreId;
	
	public MovieGenreKey(long movieId, long genreId) {
		this.movieId = movieId;
		this.genreId = genreId;
	}

	public long getMovieId() {
		return movieId;
	}

	public long getGenreId() {
		return genreId;
	}

	@Override
	public String toString() {
		return "MovieGenreKey [movieId=" + movieId + ", genreId=" + genreId
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (genreId ^ (genreId >>> 32));
		result = prime * result + (int) (movieId ^ (movieId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MovieGenreKey)) {
			return false;
		}
		MovieGenreKey other = (MovieGenreKey) obj;
		if (genreId != other.genreId) {
			return false;
		}
		if (movieId != other.movieId) {
			return false;
		}
		return true;
	}
	
	
}
