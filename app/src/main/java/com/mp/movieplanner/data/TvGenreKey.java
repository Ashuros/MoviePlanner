package com.mp.movieplanner.data;


public class TvGenreKey {
    private final long tvId;
    private final long genreId;

    public TvGenreKey(long tvId, long genreId) {
        this.tvId = tvId;
        this.genreId = genreId;
    }

    public long getTvId() {
        return tvId;
    }

    public long getGenreId() {
        return genreId;
    }

    @Override
    public String toString() {
        return "TvGenreKey{" +
                "tvId=" + tvId +
                ", genreId=" + genreId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvGenreKey that = (TvGenreKey) o;

        if (genreId != that.genreId) return false;
        if (tvId != that.tvId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (tvId ^ (tvId >>> 32));
        result = 31 * result + (int) (genreId ^ (genreId >>> 32));
        return result;
    }
}
