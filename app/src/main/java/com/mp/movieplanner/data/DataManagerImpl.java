package com.mp.movieplanner.data;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mp.movieplanner.data.MoviePlannerContract.Movies;
import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;

public class DataManagerImpl implements DataManager {
	public static final String DB_INFO = "DB_INFO";
	private SQLiteDatabase db;
	
	private GenreDao genreDao;
	private MovieDao movieDao;
	private MoviesGenresDao moviesGenresDao;
	
	public DataManagerImpl(Context context) {
		SQLiteOpenHelper openHelper = new MoviePlannerDbHelper(context);
		db = openHelper.getWritableDatabase();
		
		Log.i(DB_INFO, "DataManagerImpl created, db open status: " + db.isOpen());
		
		genreDao = new GenreDao(db);
		movieDao = new MovieDao(db);
		moviesGenresDao = new MoviesGenresDao(db);
	}
	
	public boolean isOpen() {
		return db.isOpen();
	}
	
	public void closeDb() {
		if (isOpen()) {
			db.close();
		}
	}
	
	//
	// "Manager" data methods that wrap DAOs
	//
	// this lets us encapsulate usage of DAOs 
	// we only expose methods app is actually using, and we can combine DAOs, with logic in one place
	// 	
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
		// there movies don't have categories, but they're really used as "header" anyway, it's ok
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
		// NOTE could wrap entity manip functions in DataManagerImpl, make "manager" for each entity
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
			// an "alias" for commit		
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

}
