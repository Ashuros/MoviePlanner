package com.mp.movieplanner.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;
import com.mp.movieplanner.util.Utils;

public class JsonMovieParser implements MovieFeed {
	
	public static final String TAG = JsonMovieParser.class.getSimpleName();

	private static final String API_KEY = "api_key=dd94fa84a437364111702da7d2979812";

	private static final String SEARCH_FEED_URL = "http://api.themoviedb.org/3/search/movie?page=1&" + API_KEY;

	private static final String FIND_FEED_URL = "http://api.themoviedb.org/3/movie/";

	private static final String GET_GENRES = "http://api.themoviedb.org/3/genre/movie/list?" + API_KEY;

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String TITLE = "original_title";
	private static final String DATE = "release_date";
	private static final String POSTER = "poster_path";
	private static final String POPULARITY = "popularity";

	private static final String OVERVIEW = "overview";
	private static final String GENRES = "genres";

	private InputStream getInputStream(String path) {
		try {
			HttpGet request = new HttpGet(path);
			HttpResponse response = Utils.getHttpClient().execute(request);
			return response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String streamToJson(BufferedReader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		return sb.toString();
	}

    @Override
	public List<MovieSearchResult> search(String searchString)
			throws IOException {
		
		searchString = searchString.replaceAll("[^a-zA-Z]", "+");
		searchString = searchString.toLowerCase();
		
		List<MovieSearchResult> movies = new ArrayList<>();
		String path = SEARCH_FEED_URL + "&query="  + searchString;

		Log.i(TAG, path);
		InputStream inStream = getInputStream(path);
		if (inStream != null) {
			BufferedReader reader = 
					new BufferedReader(
							new InputStreamReader(inStream));
			try {
				JSONObject json = new JSONObject(streamToJson(reader));
				JSONArray results = json.getJSONArray("results");
				for (int i = 0; i < results.length(); i++) {
					JSONObject movie = results.getJSONObject(i);
					movies.add(getMovieSearchResult(movie));
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				reader.close();
			}
			return movies;
		}
		return Collections.emptyList();
	}

	private MovieSearchResult getMovieSearchResult(JSONObject jsonMovie)
			throws JSONException {
		MovieSearchResult movie = new MovieSearchResult();
		movie.setId(jsonMovie.get("id").toString());
		movie.setTitle(jsonMovie.get("title").toString());
		return movie;
	}

	public Movie find(String id) throws IOException {
		String path = FIND_FEED_URL + id + "?" + API_KEY;
		
		InputStream inStream = getInputStream(path);
		
		if (inStream != null) {
		BufferedReader reader = 
				new BufferedReader(
						new InputStreamReader(inStream));
			try {
				Movie movie = new Movie();
				JSONObject jsonMovie = new JSONObject(streamToJson(reader));
	
				movie.setMovieId(Long.parseLong(id));
				movie.setGenres(getGenresFromJson(jsonMovie));
				movie.setOriginalTitle(jsonMovie.getString(TITLE));
				movie.setOverview(jsonMovie.getString(OVERVIEW));
				movie.setPopularity(Double.parseDouble(jsonMovie.getString(POPULARITY)));
				movie.setPosterPath(jsonMovie.getString(POSTER));
				movie.setReleaseDate(jsonMovie.getString(DATE));
				Log.i("JSON", movie.getGenres().toString() + "\n" + movie.getMovieId()
								+ "\n" + movie.getOriginalTitle() + "\n"
								+ movie.getOverview() + "\n"
								+ movie.getPopularity() + "\n"
								+ movie.getPosterPath() + "\n"
								+ movie.getReleaseDate() + "\n");
				
				return movie;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		}
		return null;
	}

	private Set<Genre> getGenresFromJson(JSONObject movie) throws JSONException {
		Set<Genre> resultGenres = new LinkedHashSet<>();
		JSONArray genres = movie.getJSONArray(GENRES);
		for (int i = 0; i < genres.length(); i++) {
			JSONObject genre = genres.getJSONObject(i);
			
			Genre g = new Genre();
			g.setGenreId(Integer.parseInt(genre.getString(ID)));
			g.setName(genre.getString(NAME));

			resultGenres.add(g);
		}
		return resultGenres;
	}

	public Set<Genre> getGenres() {
		BufferedReader reader =
				new BufferedReader(
						new InputStreamReader(getInputStream(GET_GENRES)));
		try {
			JSONObject json = new JSONObject(streamToJson(reader));
			return getGenresFromJson(json);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
