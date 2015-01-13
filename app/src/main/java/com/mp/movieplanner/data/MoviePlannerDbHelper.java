package com.mp.movieplanner.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mp.movieplanner.MoviePlannerApp;
import com.mp.movieplanner.data.MovieContract.Movies;
import com.mp.movieplanner.data.MovieContract.MoviesGenres;
import com.mp.movieplanner.data.dao.movie.GenreDao;
import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.common.Utils;

import static com.mp.movieplanner.data.TvContract.Tv;
import static com.mp.movieplanner.data.TvContract.TvGenres;

public class MoviePlannerDbHelper extends SQLiteOpenHelper {
	public static final String DB_INFO = "DB_INFO";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "MoviePlanner.db";
	
	private Context ctx;
	
	public MoviePlannerDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.ctx = context;
	}
	
	@Override
	public void onOpen(final SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys=ON;");

			Cursor c = db.rawQuery("PRAGMA foreign_keys", null);
			if (c.moveToFirst()) {
				int result = c.getInt(0);
				Log.i(DB_INFO, "SQLite foreign key support (1 is on, 0 is off): " + result);				
			} else {
	            Log.i(DB_INFO, "SQLite foreign key support NOT AVAILABLE");
			}
			
	        if (!c.isClosed()) {
	            c.close();
	        } 					
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(DB_INFO, "Creating database " + DATABASE_NAME);
		MovieContract.GenresMovie.onCreate(db);
		Movies.onCreate(db);
		MoviesGenres.onCreate(db);

        initMovieGenres(db);

        TvContract.GenresTv.onCreate(db);
        Tv.onCreate(db);
        TvGenres.onCreate(db);


	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(DB_INFO, "SQLiteOpenHelper onUpgrade - oldVersion:" + oldVersion + " newVersion:" + newVersion);
		MoviesGenres.onUpgrade(db, oldVersion, newVersion);
		Movies.onUpgrade(db, oldVersion, newVersion);
		MovieContract.GenresMovie.onUpgrade(db, oldVersion, newVersion);
	}

    private void initMovieGenres(SQLiteDatabase db) {
        if (((MoviePlannerApp)ctx).isConnectionPresent()) {
            final GenreDao genreDao = new GenreDao(db);
            new Thread( new Runnable() {
                @Override
                public void run() {
                    for (Genre genre : Utils.getTheMovieDBClient().retrieveMovieGenres()) {
                        genreDao.save(genre);
                    }
                }
            }).start();
        }
    }
}
