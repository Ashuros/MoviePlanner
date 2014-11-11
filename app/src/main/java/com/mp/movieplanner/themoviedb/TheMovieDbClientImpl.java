package com.mp.movieplanner.themoviedb;

import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.model.MovieSearchResult;
import com.mp.movieplanner.model.MovieSearchResultResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.mp.movieplanner.themoviedb.TheMovieDbURL.API_KEY;
import static com.mp.movieplanner.themoviedb.TheMovieDbURL.FIND_FEED_URL;
import static com.mp.movieplanner.themoviedb.TheMovieDbURL.SEARCH_FEED_URL;

public class TheMovieDbClientImpl implements TheMovieDbClient {

    public static final String TAG = TheMovieDbClientImpl.class.getSimpleName();

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TITLE = "original_title";
    private static final String DATE = "release_date";
    private static final String POSTER = "poster_path";
    private static final String POPULARITY = "popularity";

    private static final String OVERVIEW = "overview";
    private static final String GENRES = "genres";

    private RestTemplate restTemplate;

    public TheMovieDbClientImpl() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public List<MovieSearchResult> search(String searchString) {
        try {
            return searchMovies(searchString);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<MovieSearchResult> searchMovies(String query) throws JSONException {
        query = replaceWhitespacesInQuery(query);
        MovieSearchResultResponse response = queryRestService(SEARCH_FEED_URL, MovieSearchResultResponse.class, query);
        return response.getResults();
    }

    private String replaceWhitespacesInQuery(String query) {
        query = query.replaceAll("[^a-zA-Z]", "+");
        return query.toLowerCase();
    }

    private <T> T queryRestService(String url, Class<T> responseType, String... query) {
        return restTemplate.getForObject(url, responseType, query);
    }

    public Movie find(String id) throws IOException {
        String path = FIND_FEED_URL + id + "?" + API_KEY;

      /*  InputStream inStream = getInputStream(path);

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
                Log.i("JSON", movie.findAllGenres().toString() + "\n" + movie.getMovieId()
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
        }*/
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

    public Set<Genre> findAllGenres() {
        /*BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(getInputStream(GET_GENRES)));
        try {
            JSONObject json = new JSONObject(streamToJson(reader));
            return getGenresFromJson(json);
        } catch (Exception e) {
            e.printStackTrace();*/
        return null;
    }
}
