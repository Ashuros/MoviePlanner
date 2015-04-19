package com.mp.movieplanner.charts.fragments;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mp.movieplanner.MoviePlannerApp;
import com.mp.movieplanner.data.service.MovieService;
import com.mp.movieplanner.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieBarChartFragment extends AbstractBarChartFragment {

    private MovieService movieService;

    @Override
    protected void setData() {
        MoviePlannerApp app = (MoviePlannerApp) getActivity().getApplication();
        movieService = app.getMovieService();

        List<Movie> movies = movieService.getAllMovies();

        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            xVals.add(movies.get(i).getOriginal_title());
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++) {
            yVals1.add(new BarEntry((float) movies.get(i).getPopularity(), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        mChart.setData(data);
    }
}
