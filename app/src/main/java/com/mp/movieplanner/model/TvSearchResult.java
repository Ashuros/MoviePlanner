package com.mp.movieplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TvSearchResult {

    private String id;
    private String original_name;
    private String first_air_date;

    public TvSearchResult() {}

    public TvSearchResult(String id, String original_name, String first_air_date) {
        this.id = id;
        this.original_name = original_name;
        this.first_air_date = first_air_date;
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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvSearchResult that = (TvSearchResult) o;

        if (first_air_date != null ? !first_air_date.equals(that.first_air_date) : that.first_air_date != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (original_name != null ? !original_name.equals(that.original_name) : that.original_name != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (original_name != null ? original_name.hashCode() : 0);
        result = 31 * result + (first_air_date != null ? first_air_date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return original_name + " (" + ((first_air_date != null && first_air_date.length() > 4) ? first_air_date.substring(0, 4) : "Date unavailable") + ")";
    }
}
