package com.mp.movieplanner;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mp.movieplanner.data.service.MovieService;
import com.mp.movieplanner.data.service.TvService;

import java.util.ArrayList;

public class ChartActivity extends Activity {

    private BarChart barChart;
    private MovieService movieService;
    private TvService tvService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_info);

        MoviePlannerApp app = (MoviePlannerApp) getApplication();
        movieService = app.getMovieService();
        tvService = app.getTvService();

        barChart = (BarChart) findViewById(R.id.chart);
        barChart.setDescription(getString(R.string.chart_desc));
        setData();
    }

    private void setData() {
        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("");

        ArrayList<BarEntry> movieVals = new ArrayList<>();
        BarEntry movies = new BarEntry(movieService.getAllMovies().size(), 0);
        movieVals.add(movies);
        BarDataSet set1 = new BarDataSet(movieVals, "Movies");
        set1.setColor(getResources().getColor(R.color.opaque_red));

        ArrayList<BarEntry> tvVals = new ArrayList<>();
        BarEntry tvs = new BarEntry(tvService.getAllTvs().size(), 0);
        tvVals.add(tvs);
        BarDataSet set2 = new BarDataSet(tvVals, "TV");
        set2.setColor(getResources().getColor(R.color.translucent_blue));

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);

        barChart.setData(data);
    }
}
