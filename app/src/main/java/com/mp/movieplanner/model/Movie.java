package com.mp.movieplanner.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class Movie extends ModelBase {
	
	private long movieId;
	private String originalTitle;
	private String releaseDate;
	private String posterPath;
	private double popularity;
	private String overview;
	private Set<Genre> genres;
	
	public Movie() {
		genres = new LinkedHashSet<>();
	}
			
	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}
	
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getPosterPath() {
		return posterPath;
	}
	
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	
	public double getPopularity() {
		return popularity;
	}
	
	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}
	
	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
	
	@Override
	public String toString() {
		return originalTitle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((genres == null) ? 0 : genres.hashCode());
		result = prime * result + (int) (movieId ^ (movieId >>> 32));
		result = prime * result
				+ ((originalTitle == null) ? 0 : originalTitle.hashCode());
		result = prime * result
				+ ((overview == null) ? 0 : overview.hashCode());
		long temp;
		temp = Double.doubleToLongBits(popularity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((posterPath == null) ? 0 : posterPath.hashCode());
		result = prime * result
				+ ((releaseDate == null) ? 0 : releaseDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Movie)) {
			return false;
		}
		Movie other = (Movie) obj;
		if (genres == null) {
			if (other.genres != null) {
				return false;
			}
		} else if (!genres.equals(other.genres)) {
			return false;
		}
		if (movieId != other.movieId) {
			return false;
		}
		if (originalTitle == null) {
			if (other.originalTitle != null) {
				return false;
			}
		} else if (!originalTitle.equals(other.originalTitle)) {
			return false;
		}
		if (overview == null) {
			if (other.overview != null) {
				return false;
			}
		} else if (!overview.equals(other.overview)) {
			return false;
		}
		if (Double.doubleToLongBits(popularity) != Double
				.doubleToLongBits(other.popularity)) {
			return false;
		}
		if (posterPath == null) {
			if (other.posterPath != null) {
				return false;
			}
		} else if (!posterPath.equals(other.posterPath)) {
			return false;
		}
		if (releaseDate == null) {
			if (other.releaseDate != null) {
				return false;
			}
		} else if (!releaseDate.equals(other.releaseDate)) {
			return false;
		}
		return true;
	}
}
