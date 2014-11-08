package com.mp.movieplanner.data;

import java.util.ArrayList;
import java.util.List;

import com.mp.movieplanner.data.MoviePlannerContract.Genres;
import com.mp.movieplanner.data.MoviePlannerContract.MoviesGenres;
import com.mp.movieplanner.model.Genre;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MoviesGenresDao {
	
	private SQLiteDatabase db;
	
	public MoviesGenresDao(SQLiteDatabase db) {
		this.db = db;
	}
	
	public long save(MovieGenreKey entity) {
		ContentValues values = new ContentValues();
		values.put(MoviesGenres.MOVIE_ID, entity.getMovieId());
		values.put(MoviesGenres.GENRE_ID, entity.getGenreId());		
		return db.insert(MoviesGenres.TABLE_NAME, null, values);
	}
	
	public void delete(MovieGenreKey key) {
		if ((key.getMovieId()) > 0 && (key.getGenreId() > 0)) {
			
			String selection = MoviesGenres.MOVIE_ID + " = ? and " +
							   MoviesGenres.GENRE_ID + " = ? ";
			
			String[] selectionArgs = { String.valueOf(key.getMovieId()),
									   String.valueOf(key.getGenreId())};
			
			Log.i("DB_INFO", "deleting MGK rows" + db.delete(MoviesGenres.TABLE_NAME, selection, selectionArgs));	
		}
	}
	
	public boolean exists(MovieGenreKey key) {
		boolean result = false;		
		String selection = MoviesGenres.MOVIE_ID + " = ? and " + 
						   MoviesGenres.GENRE_ID + " = ?";
		
		String[] selectionArgs = new String[] {String.valueOf(key.getMovieId()),
											   String.valueOf(key.getGenreId())};		
		Cursor c = db.query(
			MoviesGenres.TABLE_NAME,
			new String[] { MoviesGenres.MOVIE_ID, MoviesGenres.GENRE_ID },
			selection,
			selectionArgs,
			null,
			null,
			null,
			"1"
		);
		
		if (c.moveToFirst()) {
			result = true; // don't just "return true" here, or Cursor won't get closed
		}		
		if (!c.isClosed()) {
			c.close();
		}
		return result;					
	}
	
	public List<Genre> getGenres(long movieId) {
		List<Genre> list = new ArrayList<>();		
		String sql = "select " + "mg." + MoviesGenres.GENRE_ID + ", " + "g." + Genres.GENRE_NAME + " from " +
				     MoviesGenres.TABLE_NAME + " mg " + ", " + Genres.TABLE_NAME + " g " + " where " +
				     " mg. " + MoviesGenres.MOVIE_ID + " = ? and " + "mg." + MoviesGenres.GENRE_ID + " = " + "g." + Genres._ID;
		
		/*String sql = "select mg.genre_id, g.genre_name from " +
					  "movies_genres mg, genres g where " +
					  "mg.movie_id = ? and mg.genre_id = g._id";
		
		String sql = "select mg.genre_id,  g.genre_name " +
				      "from movies_genres mg " +
				      "JOIN genres g " +
				      "ON g._id = mg.genre_id " +
				      "WHERE mg.movie_id = ?;";
		
		String all = "select * from movies_genres where movie_id = ?";
		*/
		Cursor c = db.rawQuery(sql, new String[] { String.valueOf(movieId) });
		if (c.moveToFirst()) {
			do {
				Genre genre = new Genre(c.getLong(0), c.getString(1));
				Log.d("DB_INFO", genre.toString());
				list.add(genre);
			} while (c.moveToNext());
		}
		
		if (!c.isClosed()) {
			c.close();
		}		
		return list;				
	}
	
	
	
	
	
	
	
	
	
}
