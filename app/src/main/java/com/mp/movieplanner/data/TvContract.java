package com.mp.movieplanner.data;


import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class TvContract {

    private TvContract() {}

    public static abstract class Tv implements BaseColumns {
        public static final String TABLE_NAME = "tv";
        public static final String TV_ID = "tv_id";
        public static final String ORIGINAL_NAME = "original_name";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String FIRST_AIR_DATE = "first_air_date";
        public static final String OVERVIEW = "overview";
        public static final String VOTE_AVERAGE = "vote_average";

        private static final String SQL_CREATE_TVS =
                "CREATE TABLE " + Tv.TABLE_NAME + " (" +
                Tv._ID + " INTEGER PRIMARY KEY, " +
                Tv.TV_ID + " INTEGER, " +
                Tv.ORIGINAL_NAME + " TEXT, " +
                Tv.POSTER_PATH + " TEXT, " +
                Tv.BACKDROP_PATH + " TEXT, " +
                Tv.FIRST_AIR_DATE + " TEXT, " +
                Tv.OVERVIEW + " TEXT, " +
                Tv.VOTE_AVERAGE + " REAL);";

        private static final String SQL_DELETE_TVS =
            "DROP TABLE IF EXISTS " + Tv.TABLE_NAME;

        public static void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TVS);
        }

        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_TVS);
            onCreate(db);
        }
    }

    public static abstract class GenresTv implements BaseColumns {
        public static final String TABLE_NAME = "genres_tv";
        public static final String GENRE_ID = "genre_id";
        public static final String GENRE_NAME = "genre_name";

        private static final String SQL_CREATE_CATEGORIES =
                "CREATE TABLE " + GenresTv.TABLE_NAME + " (" +
                        GenresTv._ID + " INTEGER PRIMARY KEY," +
                        GenresTv.GENRE_ID + " INTEGER, " +
                        GenresTv.GENRE_NAME + " TEXT UNIQUE NOT NULL" + ");";

        private static final String SQL_DELETE_GENRES =
                "DROP TABLE IF EXISTS " + GenresTv.TABLE_NAME;


        public static void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_CATEGORIES);
        }

        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_GENRES);
            onCreate(db);
        }
    }

    public static abstract class TvGenres {
        public static final String TABLE_NAME = "tv_genres";
        public static final String TV_ID = "tv_id";
        public static final String GENRE_ID = "genre_id";

        private static final String SQL_CREATE_MOVIES_GENRES =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        TV_ID + " INTEGER NOT NULL, " +
                        GENRE_ID + " INTEGER NOT NULL, " +
                        "FOREIGN KEY(" + TV_ID + ") REFERENCES " + Tv.TABLE_NAME + "(" + Tv._ID + ")," +
                        "FOREIGN KEY(" + GENRE_ID + ") REFERENCES " + GenresTv.TABLE_NAME + "(" + GenresTv._ID + "), " +
                        "PRIMARY KEY (" + TV_ID + "," + GENRE_ID + ")" +
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
