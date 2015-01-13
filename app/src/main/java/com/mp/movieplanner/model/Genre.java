package com.mp.movieplanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Genre extends ModelBase implements Comparable<Genre> {
	private long genreId;
	private String name;

	public Genre() {}

	public Genre(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getGenreId() {
		return genreId;
	}

    @JsonProperty("id")
	public void setGenreId(long genreId) {
		this.genreId = genreId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return super.toString() + " " + this.name;
	}

	@Override
	public int compareTo(Genre another) {

		if (another == null) {
			return -1;
	    }

		int idDiff = (this.genreId < another.genreId) ? -1
				: (this.genreId > another.genreId) ? 1 : 0;

		if (idDiff != 0) {
			return idDiff;
		}

	    if (this.name == null) {
	    	return 1;
	    }

	    return this.name.compareToIgnoreCase(another.name);
	}
}
