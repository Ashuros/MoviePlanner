package com.mp.movieplanner.data.dao;

import android.database.Cursor;

import java.util.List;

public interface Dao<T> {
	
	long save(T type);
	
	void delete(T type);
	
	T get(long id);
	
	List<T> getAll();

    Cursor getAllCursor();

}
