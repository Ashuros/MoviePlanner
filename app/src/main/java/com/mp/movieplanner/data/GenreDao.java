package com.mp.movieplanner.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mp.movieplanner.data.MoviePlannerContract.Genres;
import com.mp.movieplanner.model.Genre;

public class GenreDao implements Dao<Genre>{
	
	
	private SQLiteDatabase db;
	
	
	public GenreDao(SQLiteDatabase db) {
		this.db = db;
	}
	
	@Override
	public long save(Genre genre) {
		ContentValues values = new ContentValues();
		values.put(Genres.GENRE_ID, genre.getGenreId());
		values.put(Genres.GENRE_NAME, genre.getName());		
		return db.insert(Genres.TABLE_NAME, null, values);
	}

	@Override
	public void delete(Genre genre) {
		if (genre.getId() > 0) {
			String selection = Genres.GENRE_NAME + " LIKE ?";
			String[] selectionArgs = { genre.getName() };
			db.delete(Genres.TABLE_NAME, selection, selectionArgs);
		}
	}

	@Override
	public Genre get(long id) {
		Genre genre = null;
		String[] projection = {
			Genres._ID,
			Genres.GENRE_ID,
			Genres.GENRE_NAME
		};
		
		String selection = Genres._ID + " = ?";
		String[] selectionArgs = {String.valueOf(id)};
		
		Cursor c = db.query(
			Genres.TABLE_NAME,
			projection,
			selection,
			selectionArgs,
			null,
			null,
			null
		);
		
		if (c.moveToFirst()) {
			genre = new Genre();
			genre.setId(c.getLong(c.getColumnIndex(Genres._ID)));
			genre.setGenreId(c.getLong(c.getColumnIndex(Genres.GENRE_ID)));
			genre.setName(c.getString(c.getColumnIndex(Genres.GENRE_NAME)));
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
			Genres.TABLE_NAME,
			new String[] { Genres._ID, Genres.GENRE_ID, Genres.GENRE_NAME },
			null,
			null,
			null,
			null,
			Genres.GENRE_NAME
		);
		
		if (c.moveToFirst()) {
			do {
				Genre g = new Genre();
				g.setId(c.getLong(c.getColumnIndex(Genres._ID)));
				g.setGenreId(c.getLong(c.getColumnIndex(Genres.GENRE_ID)));
				g.setName(c.getString(c.getColumnIndex(Genres.GENRE_NAME)));
				genres.add(g);
			} while (c.moveToNext());
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		
		return genres;
	}
	
	public Genre find(String name) {
		Genre genre = null;
		String[] projection = {
				Genres._ID,
				Genres.GENRE_ID,
				Genres.GENRE_NAME
			};
		String selection = "UPPER(" + Genres.GENRE_NAME + ")" + " LIKE ?";
		String[] selectionArgs = { name.toUpperCase() };
		
		Cursor c = db.query(
				Genres.TABLE_NAME,
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
			genre.setId(c.getLong(c.getColumnIndex(Genres._ID)));
			genre.setGenreId(c.getLong(c.getColumnIndex(Genres.GENRE_ID)));
			genre.setName(c.getString(c.getColumnIndex(Genres.GENRE_NAME)));
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		
		return genre;
	}
	
}
