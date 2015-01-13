package com.mp.movieplanner.data.dao.movie;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mp.movieplanner.data.MovieContract;
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
		values.put(MovieContract.GenresMovie.GENRE_ID, genre.getGenreId());
		values.put(MovieContract.GenresMovie.GENRE_NAME, genre.getName());
		return db.insert(MovieContract.GenresMovie.TABLE_NAME, null, values);
	}

	@Override
	public void delete(Genre genre) {
		if (genre.getId() > 0) {
			String selection = MovieContract.GenresMovie.GENRE_NAME + " LIKE ?";
			String[] selectionArgs = { genre.getName() };
			db.delete(MovieContract.GenresMovie.TABLE_NAME, selection, selectionArgs);
		}
	}

	@Override
	public Genre get(long id) {
		Genre genre = null;
		String[] projection = {
			MovieContract.GenresMovie._ID,
			MovieContract.GenresMovie.GENRE_ID,
			MovieContract.GenresMovie.GENRE_NAME
		};
		
		String selection = MovieContract.GenresMovie._ID + " = ?";
		String[] selectionArgs = {String.valueOf(id)};
		
		Cursor c = db.query(
			MovieContract.GenresMovie.TABLE_NAME,
			projection,
			selection,
			selectionArgs,
			null,
			null,
			null
		);
		
		if (c.moveToFirst()) {
			genre = new Genre();
			genre.setId(c.getLong(c.getColumnIndex(MovieContract.GenresMovie._ID)));
			genre.setGenreId(c.getLong(c.getColumnIndex(MovieContract.GenresMovie.GENRE_ID)));
			genre.setName(c.getString(c.getColumnIndex(MovieContract.GenresMovie.GENRE_NAME)));
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
			MovieContract.GenresMovie.TABLE_NAME,
			new String[] { MovieContract.GenresMovie._ID, MovieContract.GenresMovie.GENRE_ID, MovieContract.GenresMovie.GENRE_NAME },
			null,
			null,
			null,
			null,
			MovieContract.GenresMovie.GENRE_NAME
		);
		
		if (c.moveToFirst()) {
			do {
				Genre g = new Genre();
				g.setId(c.getLong(c.getColumnIndex(MovieContract.GenresMovie._ID)));
				g.setGenreId(c.getLong(c.getColumnIndex(MovieContract.GenresMovie.GENRE_ID)));
				g.setName(c.getString(c.getColumnIndex(MovieContract.GenresMovie.GENRE_NAME)));
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
        return db.rawQuery("SELECT * FROM " + MovieContract.GenresMovie.TABLE_NAME, null);
    }

    public Genre find(String name) {
		Genre genre = null;
		String[] projection = {
				MovieContract.GenresMovie._ID,
				MovieContract.GenresMovie.GENRE_ID,
				MovieContract.GenresMovie.GENRE_NAME
			};
		String selection = "UPPER(" + MovieContract.GenresMovie.GENRE_NAME + ")" + " LIKE ?";
		String[] selectionArgs = { name.toUpperCase() };
		
		Cursor c = db.query(
				MovieContract.GenresMovie.TABLE_NAME,
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
			genre.setId(c.getLong(c.getColumnIndex(MovieContract.GenresMovie._ID)));
			genre.setGenreId(c.getLong(c.getColumnIndex(MovieContract.GenresMovie.GENRE_ID)));
			genre.setName(c.getString(c.getColumnIndex(MovieContract.GenresMovie.GENRE_NAME)));
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		
		return genre;
	}
	
}
