package com.mp.movieplanner.themoviedb.response;


import com.mp.movieplanner.model.TvSearchResult;

import java.util.List;

public class TvSearchResultResponse {

    private int page;
    private int total_pages;
    private int total_results;
    private List<TvSearchResult> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public List<TvSearchResult> getResults() {
        return results;
    }

    public void setResults(List<TvSearchResult> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvSearchResultResponse response = (TvSearchResultResponse) o;

        if (page != response.page) return false;
        if (total_pages != response.total_pages) return false;
        if (total_results != response.total_results) return false;
        if (results != null ? !results.equals(response.results) : response.results != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + total_pages;
        result = 31 * result + total_results;
        return result;
    }

    @Override
    public String toString() {
        return "TvSearchResultResponse{" +
                "page=" + page +
                ", results=" + results +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
