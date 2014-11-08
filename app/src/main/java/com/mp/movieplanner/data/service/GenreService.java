package com.mp.movieplanner.data.service;

import com.mp.movieplanner.model.Genre;

import java.util.List;

public interface GenreService extends Service {

    public long saveGenre(Genre genre);

    public Genre getGenre(long id);

    public void deleteGenre(Genre entity);

    public List<Genre> getAllGenres();

    public Genre findGenre(String name);
}
