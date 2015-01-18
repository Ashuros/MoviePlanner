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

    @JsonProperty("id")
    public long getGenreId() {
        return genreId;
    }

    @JsonProperty("id")
    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Genre genre = (Genre) o;

        if (genreId != genre.genreId) return false;
        if (name != null ? !name.equals(genre.name) : genre.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (genreId ^ (genreId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
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

    @Override
    public String toString() {
        return "Genre{" +
                "genreId=" + genreId +
                ", name='" + name + '\'' +
                '}';
    }
}
