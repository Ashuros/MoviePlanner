package com.mp.movieplanner.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.mp.movieplanner.MoviePlannerApp;
import com.mp.movieplanner.R;
import com.mp.movieplanner.adapters.ImageAdapter;
import com.mp.movieplanner.common.ImageCache;
import com.mp.movieplanner.common.Utils;
import com.mp.movieplanner.data.service.TvService;
import com.mp.movieplanner.model.Tv;
import com.mp.movieplanner.themoviedb.response.Backdrop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment {

    private MoviePlannerApp app;
    private TvService tvService;
    private ImageCache imageCache;
    private ImageAdapter gridViewImageAdapter;
    private long position;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.app = (MoviePlannerApp) activity.getApplication();

        position = getArguments().getLong("POSITION", -1L);

        tvService = app.getTvService();
        imageCache = app.getImageCache();
    }

    @Override
    public View onCreateView(LayoutInflater infalter, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(infalter, container, savedInstanceState);
        return infalter.inflate(R.layout.grid_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridViewImageAdapter = new ImageAdapter(getActivity(), new ArrayList<Backdrop>(), imageCache);

        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(gridViewImageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tv tv = tvService.getTvById(position);
        new FetchImagesURLs(tv.getTvId()).execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        app = null;
    }

    private class FetchImagesURLs extends AsyncTask<Void, List<Backdrop>, List<Backdrop>> {
        private final long tvId;

        public FetchImagesURLs(long tvId) {
            this.tvId = tvId;
        }

        @Override
        protected List<Backdrop> doInBackground(Void... movieId) {
            if (app.isConnectionPresent()) {
                return Utils.getTheMovieDBClient().getImagesForMovie(tvId);
            }
            return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(List<Backdrop> backdrops) {
            gridViewImageAdapter.addAll(backdrops);
        }
    }
}
