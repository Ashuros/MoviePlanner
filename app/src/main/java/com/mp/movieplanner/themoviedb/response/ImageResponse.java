package com.mp.movieplanner.themoviedb.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageResponse {
    private String id;
    private List<Backdrop> backdrops;

    public ImageResponse() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    @Override
    public String toString() {
        return "ImageResponse{" +
                "id='" + id + '\'' +
                ", backdrops=" + backdrops +
                '}';
    }
}
