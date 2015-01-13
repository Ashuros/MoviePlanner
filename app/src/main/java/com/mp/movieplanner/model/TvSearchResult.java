package com.mp.movieplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TvSearchResult {

    private String id;
    private String original_name;

    public TvSearchResult() {

    }

    public TvSearchResult(String id, String original_name) {
        this.id = id;
        this.original_name = original_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvSearchResult that = (TvSearchResult) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (original_name != null ? !original_name.equals(that.original_name) : that.original_name != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (original_name != null ? original_name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TvSearchResult{" +
                "id='" + id + '\'' +
                ", original_name='" + original_name + '\'' +
                '}';
    }
}
