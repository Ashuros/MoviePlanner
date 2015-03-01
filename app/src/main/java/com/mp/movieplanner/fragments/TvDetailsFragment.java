package com.mp.movieplanner.fragments;


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
import com.mp.movieplanner.model.Tv;
import com.mp.movieplanner.tasks.DownloadListImageTask;

public class TvDetailsFragment extends Fragment {
    final static String ARG_POSITION = "POSITION";
    private long currentPosition = -1;

    private MoviePlannerApp app;

    private ImageView image;
    private TextView originalTitle;
    private TextView releaseDate;
    private TextView overview;
    private TextView genres;

    @Override
    public View onCreateView(LayoutInflater infalter, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getLong(ARG_POSITION);
        }
        currentPosition = getArguments().getLong("POSITION", -1);
        return infalter.inflate(R.layout.details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (MoviePlannerApp)getActivity().getApplication();
        image = (ImageView) getView().findViewById(R.id.details_image);
        originalTitle = (TextView) getView().findViewById(R.id.details_title);
        releaseDate = (TextView) getView().findViewById(R.id.details_date);
        overview = (TextView) getView().findViewById(R.id.details_overview);
        genres = (TextView) getView().findViewById(R.id.details_genres);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (currentPosition != -1) {
            updateMovieView(currentPosition);
        }
    }

    public void updateMovieView(long position){
        Tv tv = app.getTvService().getTvById(position);
        image.setTag(position);
        new DownloadListImageTask(app.getImageCache(), image, position).execute(tv.getPoster_path());
        originalTitle.setText(tv.getOriginal_name());
        releaseDate.setText(tv.getFirst_air_date());
        overview.setText(tv.getOverview());

        StringBuilder genreLabels = new StringBuilder();
        for (Genre g : tv.getGenres()) {
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
