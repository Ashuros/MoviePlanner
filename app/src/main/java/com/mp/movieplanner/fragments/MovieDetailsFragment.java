package com.mp.movieplanner.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mp.movieplanner.MoviePlannerApp;
import com.mp.movieplanner.R;
import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.tasks.DownloadListImageTask;

public class MovieDetailsFragment extends Fragment {
    final static String ARG_POSITION = "POSITION";
    private long currentPosition = -1;

    private MoviePlannerApp app;

    private ImageView image;
    private TextView originalTitle;
    private TextView releaseDate;
    private TextView overview;
    private TextView popularity;
    private TextView genres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState !=  null) {
            currentPosition = savedInstanceState.getLong(ARG_POSITION);
        }
        currentPosition = getArguments().getLong("POSITION", -1);
        return inflater.inflate(R.layout.details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (MoviePlannerApp) getActivity().getApplication();
        image = (ImageView) getView().findViewById(R.id.details_image);
        originalTitle = (TextView) getView().findViewById(R.id.details_title);
        releaseDate = (TextView) getView().findViewById(R.id.details_date);
        overview = (TextView) getView().findViewById(R.id.details_overview);
        popularity = (TextView) getView().findViewById(R.id.details_popularity);
        genres = (TextView) getView().findViewById(R.id.details_genres);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (currentPosition != -1) {
            updateMovieView(currentPosition);
        }
    }

    public void updateMovieView(long position) {
        Movie movie = app.getMovieService().getMovie(position);
        image.setTag(position);
        new DownloadListImageTask(app.getImageCache(), image, position).execute(movie.getPoster_path());
        originalTitle.setText(movie.getOriginal_title());
        releaseDate.setText(movie.getRelease_date());
        overview.setText(movie.getOverview());
        popularity.setText(String.format("Popularity: %.2f", movie.getPopularity()));

        StringBuilder genreLabels = new StringBuilder();
        for (Genre g : movie.getGenres()) {
            genreLabels.append(g.getName())
                       .append(", ");
        }
        genres.setText(genreLabels.substring(0, genreLabels.length() > 0 ? genreLabels.length() - 2 : genreLabels.length()));
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current article selection in case we need to recreate the fragment
        outState.putLong(ARG_POSITION, currentPosition);
    }
}
