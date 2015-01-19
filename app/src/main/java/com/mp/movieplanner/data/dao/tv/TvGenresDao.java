package com.mp.movieplanner.data.dao.tv;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mp.movieplanner.data.TvContract;
import com.mp.movieplanner.data.TvGenreKey;
import com.mp.movieplanner.model.Genre;

import java.util.ArrayList;
import java.util.List;

import static com.mp.movieplanner.data.TvContract.TvGenres;

public class TvGenresDao {

    private static final String TAG = TvGenresDao.class.getSimpleName();

    private SQLiteDatabase db;

    public TvGenresDao(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(TvGenreKey entity) {
        ContentValues values = new ContentValues();
        values.put(TvGenres.TV_ID, entity.getTvId());
        values.put(TvGenres.GENRE_ID, entity.getGenreId());
        return db.insert(TvGenres.TABLE_NAME, null, values);
    }

    public void delete(TvGenreKey key) {
        Log.d(TAG, "Deleting TvGenreKey " + key);
        if ((key.getTvId() > 0) && (key.getGenreId() > 0)) {

            String selection = TvGenres.TV_ID + " = ? AND " +
                    TvGenres.GENRE_ID + " = ? ";

            String[] selectionArgs = {String.valueOf(key.getTvId()),
                    String.valueOf(key.getGenreId())};

            db.delete(TvGenres.TABLE_NAME, selection, selectionArgs);
        }
    }

    public boolean exists(TvGenreKey key) {
        boolean result = false;
        String selection = TvGenres.TV_ID + " = ? AND " +
                TvGenres.GENRE_ID + " = ?";

        String[] selectionsArgs = new String[]{String.valueOf(key.getTvId()),
                String.valueOf(key.getGenreId())};

        Cursor c = db.query(
                TvGenres.TABLE_NAME,
                new String[]{TvGenres.TV_ID, TvGenres.GENRE_ID},
                selection,
                selectionsArgs,
                null,
                null,
                null,
                "1"
        );

        if (c.moveToFirst()) {
            result = true;
        }

        if (!c.isClosed()) {
            c.close();
        }

        return result;
    }

    public List<Genre> getGenresForTvId(long tvId) {
        List<Genre> genres = new ArrayList<>();

        String sql = "select " + "tv." + TvGenres.GENRE_ID + ", " + "g." + TvContract.GenresTv.GENRE_NAME + " from " +
                TvGenres.TABLE_NAME + " tv " + ", " + TvContract.GenresTv.TABLE_NAME + " g " + " where " +
                " tv. " + TvGenres.TV_ID + " = ? and " + "tv." + TvGenres.GENRE_ID + " = " + "g." + TvContract.GenresTv._ID;

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(tvId)});
        if (c.moveToFirst()) {
            do {
                Genre genre = new Genre(c.getLong(0), c.getString(1));
                Log.d(TAG, genre.toString());
                genres.add(genre);
            } while (c.moveToNext());
        }

        if (!c.isClosed()) {
            c.close();
        }
        return genres;
    }

}
