package com.mp.movieplanner.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class MoviePlannerContract {

	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
	public MoviePlannerContract() {}
	
	/* Inner class that defines the table contents */
    public static abstract class Movies implements BaseColumns {
        public static final String TABLE_NAME ="movies";
    	public static final String MOVIE_ID = "movie_id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String OVERVIEW = "overview";
        public static final String POPULARITY = "popularity";
        public static final String POSTER_PATH = "poster_path";
        public static final String RELEASE_DATE = "release_date";
        
        private static final String SQL_CREATE_MOVIES =
        		"CREATE TABLE " + Movies.TABLE_NAME + " (" +
        	    Movies._ID + " INTEGER PRIMARY KEY, " +
        		Movies.MOVIE_ID + " INTEGER, " +
        		Movies.ORIGINAL_TITLE + " TEXT, " +
        	    Movies.OVERVIEW + " TEXT," +
        		Movies.POPULARITY + " REAL, " +
        	    Movies.POSTER_PATH + " TEXT, " +
        		Movies.RELEASE_DATE + " TEXT" + ");";
        
    	private static final String SQL_DELETE_MOVIES = 
    			"DROP TABLE IF EXISTS " + Movies.TABLE_NAME;
        
    	public static void onCreate(SQLiteDatabase db) {
    		db.execSQL(SQL_CREATE_MOVIES);
    	}
    	
    	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    		db.execSQL(SQL_DELETE_MOVIES);
    		onCreate(db);
    	}               
    }
    
    public static abstract class Genres implements BaseColumns {
    	public static final String TABLE_NAME = "genres";
    	public static final String GENRE_ID = "genre_id";
    	public static final String GENRE_NAME = "genre_name";
    	
    	private static final String SQL_CREATE_CATEGORIES =
    			"CREATE TABLE " + Genres.TABLE_NAME + " (" + 
    		    Genres._ID + " INTEGER PRIMARY KEY," + 
    			Genres.GENRE_ID + " INTEGER, " +		
    			Genres.GENRE_NAME + " TEXT UNIQUE NOT NULL" + ");";
    	
    	private static final String SQL_DELETE_GENRES = 
    			"DROP TABLE IF EXISTS " + Genres.TABLE_NAME;
    	
    	
    	public static void onCreate(SQLiteDatabase db) {
    		db.execSQL(SQL_CREATE_CATEGORIES);
    	}
    	
    	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    		db.execSQL(SQL_DELETE_GENRES);
    		onCreate(db);
    	}
    }
    
    public static abstract class MoviesGenres {
    	public static final String TABLE_NAME = "movies_genres";
    	public static final String MOVIE_ID = "movie_id";
    	public static final String GENRE_ID = "genre_id";
    	
    	private static final String SQL_CREATE_MOVIES_GENRES = 
    			"CREATE TABLE " + TABLE_NAME + "(" +
    			MOVIE_ID + " INTEGER NOT NULL, " +
    			GENRE_ID + " INTEGER NOT NULL, " +
    			"FOREIGN KEY(" + MOVIE_ID + ") REFERENCES " + Movies.TABLE_NAME + "(" + Movies._ID + ")," +
    			"FOREIGN KEY(" + GENRE_ID + ") REFERENCES " + Genres.TABLE_NAME + "(" + Genres._ID + "), " +
    			"PRIMARY KEY (" + MOVIE_ID + "," + GENRE_ID + ")" +
    			");";
    	
    	private static final String SQL_DELETE_MOVIES_GENRES =
    			"DROP TABLE IF EXISTS " + TABLE_NAME; 
    	
    	public static void onCreate(SQLiteDatabase db) {
    		db.execSQL(SQL_CREATE_MOVIES_GENRES);    		
    	}
    	
    	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    		db.execSQL(SQL_DELETE_MOVIES_GENRES);
    		onCreate(db);
    	}
    }
    
    
    
    
    
    
    
    
    
    
}
