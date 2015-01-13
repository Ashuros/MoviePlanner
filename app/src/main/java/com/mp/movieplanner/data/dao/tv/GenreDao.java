package com.mp.movieplanner.data.dao.tv;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mp.movieplanner.data.TvContract;
import com.mp.movieplanner.data.dao.Dao;
import com.mp.movieplanner.model.Genre;

public class GenreDao implements Dao<Genre> {
	private SQLiteDatabase db;

	public GenreDao(SQLiteDatabase db) {
		this.db = db;
	}
	
	@Override
	public long save(Genre genre) {
		ContentValues values = new ContentValues();
		values.put(TvContract.GenresTv.GENRE_ID, genre.getGenreId());
		values.put(TvContract.GenresTv.GENRE_NAME, genre.getName());
		return db.insert(TvContract.GenresTv.TABLE_NAME, null, values);
	}

	@Override
	public void delete(Genre genre) {
		if (genre.getId() > 0) {
			String selection = TvContract.GenresTv.GENRE_NAME + " LIKE ?";
			String[] selectionArgs = { genre.getName() };
			db.delete(TvContract.GenresTv.TABLE_NAME, selection, selectionArgs);
		}
	}

	@Override
	public Genre get(long id) {
		Genre genre = null;
		String[] projection = {
			TvContract.GenresTv._ID,
			TvContract.GenresTv.GENRE_ID,
			TvContract.GenresTv.GENRE_NAME
		};
		
		String selection = TvContract.GenresTv._ID + " = ?";
		String[] selectionArgs = {String.valueOf(id)};
		
		Cursor c = db.query(
			TvContract.GenresTv.TABLE_NAME,
			projection,
			selection,
			selectionArgs,
			null,
			null,
			null
		);
		
		if (c.moveToFirst()) {
			genre = new Genre();
			genre.setId(c.getLong(c.getColumnIndex(TvContract.GenresTv._ID)));
			genre.setGenreId(c.getLong(c.getColumnIndex(TvContract.GenresTv.GENRE_ID)));
			genre.setName(c.getString(c.getColumnIndex(TvContract.GenresTv.GENRE_NAME)));
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		
		return genre;
	}

	@Override
	public List<Genre> getAll() {
		List<Genre> genres = new ArrayList<>();
		Cursor c = db.query(
			TvContract.GenresTv.TABLE_NAME,
			new String[] { TvContract.GenresTv._ID, TvContract.GenresTv.GENRE_ID, TvContract.GenresTv.GENRE_NAME },
			null,
			null,
			null,
			null,
			TvContract.GenresTv.GENRE_NAME
		);
		
		if (c.moveToFirst()) {
			do {
				Genre g = new Genre();
				g.setId(c.getLong(c.getColumnIndex(TvContract.GenresTv._ID)));
				g.setGenreId(c.getLong(c.getColumnIndex(TvContract.GenresTv.GENRE_ID)));
				g.setName(c.getString(c.getColumnIndex(TvContract.GenresTv.GENRE_NAME)));
				genres.add(g);
			} while (c.moveToNext());
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		
		return genres;
	}

    @Override
    public Cursor getAllCursor() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Genre find(String name) {
		Genre genre = null;
		String[] projection = {
				TvContract.GenresTv._ID,
				TvContract.GenresTv.GENRE_ID,
				TvContract.GenresTv.GENRE_NAME
			};
		String selection = "UPPER(" + TvContract.GenresTv.GENRE_NAME + ")" + " LIKE ?";
		String[] selectionArgs = { name.toUpperCase() };
		
		Cursor c = db.query(
				TvContract.GenresTv.TABLE_NAME,
				projection,
				selection,
				selectionArgs,
				null,
				null,
				null,
				"1"
			);
		
		if (c.moveToFirst()) {
			genre = new Genre();
			genre.setId(c.getLong(c.getColumnIndex(TvContract.GenresTv._ID)));
			genre.setGenreId(c.getLong(c.getColumnIndex(TvContract.GenresTv.GENRE_ID)));
			genre.setName(c.getString(c.getColumnIndex(TvContract.GenresTv.GENRE_NAME)));
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		
		return genre;
	}
	
}
