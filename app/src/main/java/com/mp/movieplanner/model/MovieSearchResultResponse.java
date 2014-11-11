package com.mp.movieplanner.model;

import java.util.Arrays;
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
    public String toString() {
        return "MovieSearchResultResponse{" +
                "page=" + page +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                ", results=" + results +
                '}';
    }
}
