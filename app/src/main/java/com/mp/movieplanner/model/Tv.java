package com.mp.movieplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tv extends ModelBase {

    private long tvId;

    private String original_name;

    private String poster_path;

    private String backdrop_path;

    private String first_air_date;

    private String overview;

    private double vote_average;

    private Set<Genre> genres;

    public Tv() {
        genres = new HashSet<>();
    }

    @JsonProperty("id")
    public long getTvId() {
        return tvId;
    }

    @JsonProperty("id")
    public void setTvId(long tvId) {
        this.tvId = tvId;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
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

        Tv tv = (Tv) o;

        if (tvId != tv.tvId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (tvId ^ (tvId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Tv{" +
                "tvId=" + tvId +
                ", original_name='" + original_name + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", first_air_date='" + first_air_date + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_average=" + vote_average +
                ", genres=" + genres +
                "} " + super.toString();
    }
}
