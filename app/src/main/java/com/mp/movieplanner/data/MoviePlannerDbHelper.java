package com.mp.movieplanner.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mp.movieplanner.MoviePlannerApp;
import com.mp.movieplanner.data.MoviePlannerContract.Genres;
import com.mp.movieplanner.data.MoviePlannerContract.Movies;
import com.mp.movieplanner.data.MoviePlannerContract.MoviesGenres;
import com.mp.movieplanner.data.dao.GenreDao;
import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.util.Utils;

public class MoviePlannerDbHelper extends SQLiteOpenHelper {
	public static final String DB_INFO = "DB_INFO";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "MoviePlanner.db";
	
	private Context ctx;
	
	public MoviePlannerDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.ctx = context;
	}
	
	//Android calls callback methods 
	@Override
	public void onOpen(final SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
	         // versions of SQLite older than 3.6.19 don't support foreign keys
	         // and neither do any version compiled with SQLITE_OMIT_FOREIGN_KEY
	         // http://www.sqlite.org/foreignkeys.html#fk_enable
	         // 
	         // make sure foreign key support is turned on if it's there (should be already, just a double-checker)
			db.execSQL("PRAGMA foreign_keys=ON;");
	         // then we check to make sure they're on 
	         // (if this returns no data they aren't even available, so we shouldn't even TRY to use them)
			
			Cursor c = db.rawQuery("PRAGMA foreign_keys", null);
			if (c.moveToFirst()) {
				int result = c.getInt(0);
				Log.i(DB_INFO, "SQLite foreign key support (1 is on, 0 is off): " + result);				
			} else {
				// could use this approach in onCreate, and not rely on foreign keys it not available, etc.
	            Log.i(DB_INFO, "SQLite foreign key support NOT AVAILABLE");
	            // if you had to here you could fall back to triggers
			}
			
	        if (!c.isClosed()) {
	            c.close();
	        } 					
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(DB_INFO, "Creating database " + DATABASE_NAME);
		Genres.onCreate(db);				
		Movies.onCreate(db);
		MoviesGenres.onCreate(db);
		
		
		if (((MoviePlannerApp)ctx).isConnectionPresent()) {
			final GenreDao genreDao = new GenreDao(db);
			new Thread( new Runnable() {
				@Override
				public void run() {
					for (Genre genre : Utils.getTheMovieDBClient().getGenres()) {
						genreDao.save(genre);
					}
				}				
			}).start();
		};
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(DB_INFO, "SQLiteOpenHelper onUpgrade - oldVersion:" + oldVersion + " newVersion:"
                + newVersion);
		MoviesGenres.onUpgrade(db, oldVersion, newVersion);
		Movies.onUpgrade(db, oldVersion, newVersion);
		Genres.onUpgrade(db, oldVersion, newVersion);	
	}
	
}
