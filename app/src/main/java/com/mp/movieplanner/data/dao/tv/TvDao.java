package com.mp.movieplanner.data.dao.tv;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.mp.movieplanner.data.TvContract;
import com.mp.movieplanner.data.dao.Dao;
import com.mp.movieplanner.model.Tv;

import java.util.ArrayList;
import java.util.List;

import static com.mp.movieplanner.data.TvContract.Tv.*;

public class TvDao implements Dao<Tv> {

    private static String TAG = TvDao.class.getSimpleName();

    private SQLiteDatabase db;

    public TvDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long save(Tv tv) {
        Log.d(TAG, "Saving " + tv);
        ContentValues values = new ContentValues();
        values.put(TV_ID, tv.getTvId());
        values.put(ORIGINAL_NAME, tv.getOriginal_name());
        values.put(POSTER_PATH, tv.getPoster_path());
        values.put(BACKDROP_PATH, tv.getBackdrop_path());
        values.put(FIRST_AIR_DATE, tv.getFirst_air_date());
        values.put(OVERVIEW, tv.getOverview());
        values.put(VOTE_AVERAGE, tv.getVote_average());
        return db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void delete(Tv tv) {
        Log.d(TAG, "Deleting " + tv);
        if (tv.getId() > 0) {
            String selection = _ID + " = ?";
            String[] selectionArgs = { String.valueOf(tv.getId()) };
            db.delete(TABLE_NAME, selection, selectionArgs);
        }
    }

    @Override
    public Tv get(long id) {
        Tv tv = null;

        String selection = _ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor c = db.query(
                TABLE_NAME,
                new String[] { _ID, TV_ID, ORIGINAL_NAME, POSTER_PATH, BACKDROP_PATH,
                        FIRST_AIR_DATE, OVERVIEW, VOTE_AVERAGE },
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            tv = buildTvFromCursor(c);
        }

        if (!c.isClosed()) {
            c.close();
        }

        return tv;
    }

    @Override
    public List<Tv> getAll() {
        Log.d(TAG, "Getting all TVs");
        List<Tv> tvs = new ArrayList<>();
        Cursor c = db.query(
                TABLE_NAME,
                new String[] { _ID, TV_ID, ORIGINAL_NAME, POSTER_PATH, BACKDROP_PATH,
                        FIRST_AIR_DATE, OVERVIEW, VOTE_AVERAGE },
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Tv tv = buildTvFromCursor(c);
                if (tv != null) {
                    tvs.add(tv);
                }
            } while (c.moveToNext());
        }

        if (!c.isClosed()) {
            c.close();
        }

        return tvs;
    }

    @Override
    public Cursor getAllCursor() {
        Log.d(TAG, "Fetching Tv Cursor getAll()");
        return db.rawQuery("SELECT * FROM " + TvContract.Tv.TABLE_NAME, null);
    }

    private Tv  buildTvFromCursor(Cursor c) {
        Tv tv = null;
        if (c != null) {
            tv = new Tv();
            tv.setId(c.getLong(c.getColumnIndex(_ID)));
            tv.setTvId(c.getLong(c.getColumnIndex(TV_ID)));
            tv.setOriginal_name(c.getString(c.getColumnIndex(ORIGINAL_NAME)));
            tv.setPoster_path(c.getString(c.getColumnIndex(POSTER_PATH)));
            tv.setBackdrop_path(c.getString(c.getColumnIndex(BACKDROP_PATH)));
            tv.setFirst_air_date(c.getString(c.getColumnIndex(FIRST_AIR_DATE)));
            tv.setOverview(c.getString(c.getColumnIndex(OVERVIEW)));
            tv.setVote_average(c.getDouble(c.getColumnIndex(VOTE_AVERAGE)));
        }
        return tv;
    }
}
