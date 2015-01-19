package com.mp.movieplanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.tasks.DownloadListItemTask;

public class MovieDetails extends Activity {
    private MoviePlannerApp app;

    private ImageView image;
    private TextView originalTitle;
    private TextView releaseDate;
    private TextView overview;
    private TextView genres;

    private long position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_fragment);
        position = getIntent().getExtras().getLong("POSITION");
        app = (MoviePlannerApp) getApplication();
        image = (ImageView) findViewById(R.id.movie_view_image);
        originalTitle = (TextView) findViewById(R.id.movie_view_title);
        releaseDate = (TextView) findViewById(R.id.movie_view_date);
        overview = (TextView) findViewById(R.id.movie_view_overview);
        genres = (TextView) findViewById(R.id.movie_view_genres);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieView(position);
    }

    public void updateMovieView(long position) {
        Movie movie = app.getMovieService().getMovie(position);
        image.setTag(position);
        new DownloadListItemTask(app.getImageCache(), image, position).execute(movie.getPoster_path());
        originalTitle.setText(movie.getOriginal_title());
        releaseDate.setText(movie.getRelease_date());
        overview.setText(movie.getOverview());

        StringBuilder genreLabels = new StringBuilder();
        for (Genre g : movie.getGenres()) {
            genreLabels.append(g.getName())
                       .append(" ");
        }
        genres.setText(genreLabels.toString());
    }
}
