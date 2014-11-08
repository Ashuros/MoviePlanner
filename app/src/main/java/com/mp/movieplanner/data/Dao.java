package com.mp.movieplanner.data;

import java.util.List;

public interface Dao<T> {
	
	long save(T type);
	
	void delete(T type);
	
	T get(long id);
	
	List<T> getAll();

}
