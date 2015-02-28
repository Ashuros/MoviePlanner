package com.mp.movieplanner.themoviedb.response;

import com.mp.movieplanner.model.MovieSearchResult;

import java.util.List;

public class MovieSearchResultResponse {

    private int page;
    private int total_pages;
    private int total_results;

    private List<MovieSearchResult> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieSearchResult> getResults() {
        return results;
    }

    public void setResults(List<MovieSearchResult> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieSearchResultResponse that = (MovieSearchResultResponse) o;

        if (page != that.page) return false;
        if (total_pages != that.total_pages) return false;
        if (total_results != that.total_results) return false;
        if (results != null ? !results.equals(that.results) : that.results != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + total_pages;
        result = 31 * result + total_results;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieSearchResultResponse{" +
                "page=" + page +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                ", results=" + results +
                '}';
    }
}
