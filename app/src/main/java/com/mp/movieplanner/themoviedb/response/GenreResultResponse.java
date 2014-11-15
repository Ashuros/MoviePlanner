package com.mp.movieplanner.themoviedb.response;

import com.mp.movieplanner.model.Genre;

import java.util.Set;

public class GenreResultResponse {

    private Set<Genre> genres;

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

        GenreResultResponse that = (GenreResultResponse) o;

        if (!genres.equals(that.genres)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return genres.hashCode();
    }

    @Override
    public String toString() {
        return "GenreResultResponse{" +
                "genres=" + genres +
                '}';
    }
}
