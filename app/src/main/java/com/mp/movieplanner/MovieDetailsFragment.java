package com.mp.movieplanner;

import com.mp.movieplanner.data.DataManager;
import com.mp.movieplanner.model.Genre;
import com.mp.movieplanner.model.Movie;
import com.mp.movieplanner.tasks.DownloadListItemTask;
import com.mp.movieplanner.MoviePlannerApp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailsFragment extends Fragment {
	final static String ARG_POSITION = "position";
	private int mCurrentPosition = -1;
	
	private MoviePlannerApp app;
	
	private Activity activity;
	
	private ImageView image;
	private TextView originalTitle;
	private TextView releaseDate;
	//private TextView popularity;
	private TextView overview;
	private TextView genres;
	
	@Override
	public View onCreateView(LayoutInflater infalter, ViewGroup container,
			Bundle savedInstanceState) {		
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}
		
		return infalter.inflate(R.layout.movie_view, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		app = (MoviePlannerApp)getActivity().getApplication();
		image = (ImageView)getView().findViewById(R.id.movie_view_image);
		originalTitle = (TextView) getView().findViewById(R.id.movie_view_title);
		releaseDate = (TextView) getView().findViewById(R.id.movie_view_date);
		//popularity = (TextView) getView().findViewById(R.id.movie_view_popularity);
		overview = (TextView) getView().findViewById(R.id.movie_view_overview);
		genres = (TextView) getView().findViewById(R.id.movie_view_genres);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		if (mCurrentPosition != -1) {
			updateMovieView(mCurrentPosition);
		}
	}
	
	public void updateMovieView(long position){
		Movie movie = app.getDataManager().getMovie(position);
		Log.i("UPDATE_MOVIE_VIEW", movie.toString());
		image.setTag(position);
		new DownloadListItemTask(app.getImageCache(), image, position).execute(movie.getPosterPath());
		originalTitle.setText(movie.getOriginalTitle());
		releaseDate.setText(movie.getReleaseDate());
		//popularity.setText(String.valueOf(movie.getPopularity()));
		overview.setText(movie.getOverview());
		StringBuilder genreLabels = new StringBuilder();
		for (Genre g: movie.getGenres()) {
			genreLabels.append(g.toString());
			genreLabels.append(" ");
		}
		genres.setText(genreLabels.toString());
	}
	
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
        // Save the current article selection in case we need to recreate the fragment
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}
}
