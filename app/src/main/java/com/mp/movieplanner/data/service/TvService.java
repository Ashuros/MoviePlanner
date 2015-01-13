package com.mp.movieplanner.data.service;

import android.database.Cursor;

import com.mp.movieplanner.model.Tv;

import java.util.List;

public interface TvService extends Service<Tv> {

    public long saveTv(Tv tv);

    public boolean deleteTv(Tv tv);

    public Tv getTvById(long id);

    public List<Tv> getAllTvs();

    public Tv findTvByName(String name);

    public Cursor getTvCursor();
}
