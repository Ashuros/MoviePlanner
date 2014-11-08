package com.mp.movieplanner.model;

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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (genreId ^ (genreId >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Genre)) {
			return false;
		}
		Genre other = (Genre) obj;
		if (genreId != other.genreId) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
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
