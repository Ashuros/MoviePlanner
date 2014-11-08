package com.mp.movieplanner.data.service.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mp.movieplanner.data.MovieGenreKey;
import com.mp.movieplanner.data.MoviePlannerContract.Movies;
import com.mp.movieplanner.data.MoviePlannerDbHelper;
import com.mp.movieplanner.data.dao.GenreDao;
import com.mp.movieplanner.data.dao.MovieDao;
import com.mp.movieplanner.data.dao.MoviesGenresDao;
import com.mp.movieplanner.data.service.MovieService;
import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;

import java.util.List;

public class MovieServiceImpl implements MovieService {
    public static final String DB_INFO = "DB_INFO";
    private SQLiteDatabase db;

    private GenreDao genreDao;
    private MovieDao movieDao;
    private MoviesGenresDao moviesGenresDao;

    public MovieServiceImpl(Context context) {
        SQLiteOpenHelper openHelper = new MoviePlannerDbHelper(context);
        db = openHelper.getWritableDatabase();

        Log.i(DB_INFO, "MovieServiceImpl created, db open status: " + db.isOpen());

        genreDao = new GenreDao(db);
        movieDao = new MovieDao(db);
        moviesGenresDao = new MoviesGenresDao(db);
    }

    public boolean isOpen() {
        return db.isOpen();
    }

    @Override
    public void close() {
        if (isOpen()) {
            db.close();
        }
    }

    @Override
    public Movie getMovie(long id) {
        Log.i(DB_INFO, "Getting movie " + id);
        Movie movie = movieDao.get(id);
        if (movie != null) {
            movie.getGenres().addAll(moviesGenresDao.getGenres(movie.getId()));
        }
        return movie;
    }

    @Override
    public List<Movie> getAllMovies() {
        Log.i(DB_INFO, "Fetching all movies");
        return movieDao.getAll();
    }

    @Override
    public Cursor getMovieCursor() {
        return db.rawQuery("SELECT * FROM " + Movies.TABLE_NAME, null);
    }

    @Override
    public Movie findMovie(String name) {
        Log.i(DB_INFO, "Finding movie " + name);
        Movie movie = movieDao.find(name);
        if (movie != null) {
            movie.getGenres().addAll(moviesGenresDao.getGenres(movie.getId()));
        }
        return movie;
    }


    @Override
    public long saveMovie(Movie movie) {
        Log.i(DB_INFO, "Saving movie " + movie);
        // NOTE could wrap entity manip functions in MovieServiceImpl, make "manager" for each entity
        // here though, to keep it simpler, we use the DAOs directly (even when multiple are involved)
        long movieId = 0L;

        // put it in a transaction, since we're touching multiple tables
        try {
            db.beginTransaction();

            // first save movie
            movieId = movieDao.save(movie);

            // second, make sure categories exist, and save movie/category association
            // (this makes multiple quries, but usually not many genres, could just save and catch exception
            // too, but that's ugly)

            if (movie.getGenres().size() > 0) {
                for (Genre g : movie.getGenres()) {
                    long genreId = 0L;
                    Genre dbGenre = genreDao.find(g.getName());
                    if (dbGenre == null) {
                        genreId = genreDao.save(g);
                    } else {
                        genreId = dbGenre.getId();
                    }
                    MovieGenreKey mgKey = new MovieGenreKey(movieId, genreId);
                    if (!moviesGenresDao.exists(mgKey)) {
                        moviesGenresDao.save(mgKey);
                    }
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e(DB_INFO, "Error saving movie (transaction rolled back)", e);
            movieId = 0L;
        } finally {
            db.endTransaction();
        }
        return movieId;
    }

    @Override
    public boolean deleteMovie(Movie movie) {
        Log.i(DB_INFO, "Deleting movie " + movie);
        //movieDao.delete(movie);
        boolean result = false;
        // NOTE switch this order around to see constraint error(foreign keys work)

        try {
            db.beginTransaction();
            // make sure to use getMovie and not movieDao directly, categories need to be included
            Movie m = getMovie(movie.getId());
            Log.i(DB_INFO, "Fetched\n" + m);
            if (m != null) {
                for (Genre g : m.getGenres()) {
                    MovieGenreKey mgk = new MovieGenreKey(m.getId(), g.getId());
                    Log.i(DB_INFO, "Trying to delete Key " + mgk);
                    moviesGenresDao.delete(mgk);
                }
                movieDao.delete(m);
            }
            db.setTransactionSuccessful();
            result = true;
        } catch (SQLException e) {
            Log.e(DB_INFO, "Error deleting movie (transaction rolled back)", e);
        } finally {
            db.endTransaction();
        }
        return result;
    }


}
