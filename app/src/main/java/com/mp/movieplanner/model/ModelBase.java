package com.mp.movieplanner.model;

public class ModelBase {
	protected long id;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "[id=" + id + "]";
	}
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.id ^ (this.id >>> 32));
		return result;
	}
	
	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof ModelBase)) {
			return false;
		}
		ModelBase other = (ModelBase) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}		
}
