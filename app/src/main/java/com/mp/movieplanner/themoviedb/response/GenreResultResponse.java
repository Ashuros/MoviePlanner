package com.mp.movieplanner.themoviedb.response;

import com.mp.movieplanner.model.Genre;
import java.util.List;

public class GenreResultResponse {

    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
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
