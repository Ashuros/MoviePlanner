package com.mp.movieplanner.data.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mp.movieplanner.data.MoviePlannerDbHelper;
import com.mp.movieplanner.data.dao.GenreDao;
import com.mp.movieplanner.data.dao.MovieDao;
import com.mp.movieplanner.data.dao.MoviesGenresDao;
import com.mp.movieplanner.data.service.GenreService;
import com.mp.movieplanner.model.Genre;

import java.util.List;

public class GenreServiceImpl implements GenreService{

    public static final String DB_INFO = "DB_INFO";
    private SQLiteDatabase db;

    private GenreDao genreDao;
    private MovieDao movieDao;
    private MoviesGenresDao moviesGenresDao;

    public GenreServiceImpl(Context context) {
        SQLiteOpenHelper openHelper = new MoviePlannerDbHelper(context);
        db = openHelper.getWritableDatabase();

        Log.i(DB_INFO, "MovieServiceImpl created, db open status: " + db.isOpen());

        genreDao = new GenreDao(db);
        movieDao = new MovieDao(db);
        moviesGenresDao = new MoviesGenresDao(db);
    }

    @Override
    public long saveGenre(Genre genre) {
        Log.i(DB_INFO, "Saving genre " + genre);
        return genreDao.save(genre);
    }

    @Override
    public Genre getGenre(long id) {
        Log.i(DB_INFO, "Fetching genre id " + id);
        return genreDao.get(id);
    }

    @Override
    public void deleteGenre(Genre entity) {
        Log.i(DB_INFO, "Deleting genre " + entity);
        genreDao.delete(entity);
    }

    @Override
    public List<Genre> getAllGenres() {
        Log.i(DB_INFO, "Getting all genres");
        return genreDao.getAll();
    }

    @Override
    public Genre findGenre(String name) {
        Log.i(DB_INFO, "Finding genre " + name);
        return genreDao.find(name);
    }

    @Override
    public boolean isOpen() {
        return db.isOpen();
    }

    @Override
    public void close() {
        if (isOpen()) {
            db.close();
        }
    }
}
