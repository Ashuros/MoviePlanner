package com.mp.movieplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedHashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie extends ModelBase {
	
	private long id;
	private String original_title;
	private String release_date;
	private String poster_path;
	private double popularity;
	private String overview;
	private Set<Genre> genres;
	
	public Movie() {
		genres = new LinkedHashSet<>();
	}

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (Double.compare(movie.popularity, popularity) != 0) return false;
        if (!genres.equals(movie.genres)) return false;
        if (!original_title.equals(movie.original_title)) return false;
        if (!overview.equals(movie.overview)) return false;
        if (!poster_path.equals(movie.poster_path)) return false;
        if (!release_date.equals(movie.release_date)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + original_title.hashCode();
        result = 31 * result + release_date.hashCode();
        result = 31 * result + poster_path.hashCode();
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + overview.hashCode();
        result = 31 * result + genres.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", original_title='" + original_title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", popularity=" + popularity +
                ", overview='" + overview + '\'' +
                ", genres=" + genres +
                '}';
    }
}
