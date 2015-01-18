package com.mp.movieplanner.data.dao.movie;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mp.movieplanner.data.MovieContract.Movies;
import com.mp.movieplanner.data.dao.Dao;
import com.mp.movieplanner.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDao implements Dao<Movie> {

    private static String TAG = MovieDao.class.getSimpleName();

    private SQLiteDatabase db;

    public MovieDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long save(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(Movies.MOVIE_ID, movie.getMovieId());
        values.put(Movies.ORIGINAL_TITLE, movie.getOriginal_title());
        values.put(Movies.RELEASE_DATE, movie.getRelease_date());
        values.put(Movies.POSTER_PATH, movie.getPoster_path());
        values.put(Movies.POPULARITY, movie.getPopularity());
        values.put(Movies.OVERVIEW, movie.getOverview());
        return db.insert(Movies.TABLE_NAME, null, values);
    }

    @Override
    public void delete(Movie movie) {
        if (movie.getId() > 0) {
            String selection = Movies._ID + " = ?";
            String[] selectionArgs = {String.valueOf(movie.getId())};
            db.delete(Movies.TABLE_NAME, selection, selectionArgs);
        }
    }

    @Override
    public Movie get(long id) {
        Movie movie = null;

        String selection = Movies._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor c = db.query(
                Movies.TABLE_NAME,
                new String[]{Movies._ID, Movies.MOVIE_ID, Movies.ORIGINAL_TITLE,
                        Movies.OVERVIEW, Movies.POPULARITY, Movies.POSTER_PATH,
                        Movies.RELEASE_DATE},
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            movie = buildMovieFromCursor(c);
        }

        if (!c.isClosed()) {
            c.close();
        }

        return movie;
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movies = new ArrayList<>();
        Cursor c = db.query(
                Movies.TABLE_NAME,
                new String[]{Movies._ID, Movies.MOVIE_ID, Movies.ORIGINAL_TITLE,
                        Movies.OVERVIEW, Movies.POPULARITY, Movies.POSTER_PATH,
                        Movies.RELEASE_DATE},
                null,
                null,
                null,
                null,
                Movies._ID
        );

        if (c.moveToFirst()) {
            do {
                Movie m = buildMovieFromCursor(c);
                if (m != null) {
                    movies.add(m);
                }
            } while (c.moveToNext());
        }

        if (!c.isClosed()) {
            c.close();
        }

        return movies;
    }

    @Override
    public Cursor getAllCursor() {
        return db.rawQuery("SELECT * FROM " + Movies.TABLE_NAME, null);
    }

    public Movie find(String name) {
        Movie movie = null;

        String selection = "UPPER(" + Movies.ORIGINAL_TITLE + ") LIKE ?";
        String[] selectionArgs = {name.toUpperCase()};

        Cursor c = db.query(
                Movies.TABLE_NAME,
                new String[]{Movies._ID, Movies.MOVIE_ID, Movies.ORIGINAL_TITLE,
                        Movies.OVERVIEW, Movies.POPULARITY, Movies.POSTER_PATH,
                        Movies.RELEASE_DATE},
                selection,
                selectionArgs,
                null,
                null,
                null,
                "1"
        );

        if (c.moveToFirst()) {
            movie = buildMovieFromCursor(c);
        }

        if (!c.isClosed()) {
            c.close();
        }

        return movie;
    }

    private Movie buildMovieFromCursor(Cursor c) {
        Movie m = null;
        if (c != null) {
            m = new Movie();
            m.setId(c.getLong(c.getColumnIndex(Movies._ID)));
            m.setMovieId(c.getLong(c.getColumnIndex(Movies.MOVIE_ID)));
            m.setOriginal_title(c.getString(c.getColumnIndex(Movies.ORIGINAL_TITLE)));
            m.setOverview(c.getString(c.getColumnIndex(Movies.OVERVIEW)));
            m.setPopularity(c.getDouble(c.getColumnIndex(Movies.POPULARITY)));
            m.setPoster_path(c.getString(c.getColumnIndex(Movies.POSTER_PATH)));
            m.setRelease_date(c.getString(c.getColumnIndex(Movies.RELEASE_DATE)));
        }
        return m;
    }

}
