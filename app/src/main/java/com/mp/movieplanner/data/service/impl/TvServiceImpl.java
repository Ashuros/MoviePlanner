package com.mp.movieplanner.data.service.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mp.movieplanner.data.MoviePlannerDbHelper;
import com.mp.movieplanner.data.TvGenreKey;
import com.mp.movieplanner.data.dao.tv.GenreDao;
import com.mp.movieplanner.data.dao.tv.TvDao;
import com.mp.movieplanner.data.dao.tv.TvGenresDao;
import com.mp.movieplanner.data.service.TvService;
import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Tv;

import java.util.List;

public class TvServiceImpl implements TvService {

    private static final String TAG = TvServiceImpl.class.getSimpleName();
    private SQLiteDatabase db;

    private GenreDao genreDao;
    private TvDao tvDao;
    private TvGenresDao tvGenresDao;

    public TvServiceImpl(Context context) {
        SQLiteOpenHelper openHelper = new MoviePlannerDbHelper(context);
        db = openHelper.getWritableDatabase();

        Log.i(TAG, "TvServiceImpl created, db open status: " + db.isOpen());

        genreDao = new GenreDao(db);
        tvDao = new TvDao(db);
        tvGenresDao = new TvGenresDao(db);
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

    @Override
    public long saveTv(Tv tv) {
        Log.i(TAG, "Saving TV " + tv);

        long tvId = 0L;

        try {
            db.beginTransaction();

            tvId = tvDao.save(tv);

            if (tv.getGenres().size() > 0) {
                for (Genre g : tv.getGenres()) {
                    long genreId = 0L;
                    Genre dbGenre = genreDao.find(g.getName());
                    if (dbGenre == null) {
                        genreId = genreDao.save(g);
                    } else {
                        genreId = dbGenre.getId();
                    }
                    TvGenreKey tvKey = new TvGenreKey(tvId, genreId);
                    if (!tvGenresDao.exists(tvKey)) {
                        tvGenresDao.save(tvKey);
                    }
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e(TAG, "Error saving tv (transaction rolled back)", e);
        } finally {
            db.endTransaction();
        }

        return tvId;
    }

    @Override
    public boolean deleteTv(Tv tv) {
        Log.d(TAG, "Deleting Tv " + tv);

        boolean result = false;

        try {
            db.beginTransaction();

            Tv t = getTvById(tv.getId());

            if (t != null) {
                for (Genre g : t.getGenres()) {
                    TvGenreKey tgk = new TvGenreKey(t.getId(), g.getId());
                    tvGenresDao.delete(tgk);
                }
                tvDao.delete(t);
            }
            db.setTransactionSuccessful();
            result = true;
        } catch (SQLException e) {
            Log.e(TAG, "Error deleting movie (transaction rolledback)", e);
        } finally {
            db.endTransaction();
        }
        return result;
    }

    @Override
    public Tv getTvById(long id) {
        Log.d(TAG, "Fetching my by ID: " + id);
        Tv tv = tvDao.get(id);
        if (tv != null) {
            tv.getGenres().addAll(tvGenresDao.getGenresForTvId(tv.getId()));
        }
        return tv;
    }

    @Override
    public List<Tv> getAllTvs() {
        Log.d(TAG, "Fetching all TVs");
        return tvDao.getAll();
    }

    @Override
    public Tv findTvByName(String name) {
        Log.d(TAG, "Finding TV by name: " + name);
        throw new UnsupportedOperationException("Find TV By Name is not supported yet");
    }

    @Override
    public Cursor getTvCursor() {
        return tvDao.getAllCursor();
    }
}
