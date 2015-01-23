package com.mp.movieplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieSearchResult {

	private String id;
	private String title;
    private String release_date;

    public MovieSearchResult() {}

    public MovieSearchResult(String id, String title, String release_date) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieSearchResult that = (MovieSearchResult) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (release_date != null ? !release_date.equals(that.release_date) : that.release_date != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return title + " (" + ((release_date != null && release_date.length() > 4) ? release_date.substring(0, 4) : "Date unavailable") + ")";
    }
}
