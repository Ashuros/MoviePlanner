package com.mp.movieplanner.charts;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mp.movieplanner.MoviePlannerApp;
import com.mp.movieplanner.R;
import com.mp.movieplanner.data.service.MovieService;
import com.mp.movieplanner.data.service.TvService;

import java.util.ArrayList;

public class ChartActivity extends Activity {

    private PieChart mChart;
    private MovieService movieService;
    private TvService tvService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chart_info);

        MoviePlannerApp app = (MoviePlannerApp) getApplication();
        movieService = app.getMovieService();
        tvService = app.getTvService();

        mChart = (PieChart) findViewById(R.id.pie_chart);
        mChart.setUsePercentValues(true);

        mChart.setHoleRadius(60f);

        mChart.setDescription("");

        mChart.setDrawCenterText(true);

        mChart.setDrawHoleEnabled(true);

        mChart.setRotationAngle(0);

        mChart.setRotationEnabled(true);

        mChart.animateXY(1500, 1500);

        setData();
    }

    private void setData() {
        ArrayList<Entry> yVals = new ArrayList<>();
        yVals.add(new Entry(movieService.getAllMovies().size(), 0));
        yVals.add(new Entry(tvService.getAllTvs().size(), 1));

        ArrayList<String> xVals = new ArrayList<>();

        xVals.add("Movies: " + movieService.getAllMovies().size());
        xVals.add("TV: " + tvService.getAllTvs().size());

        PieDataSet dataSet = new PieDataSet(yVals, "Your collection");
        dataSet.setSliceSpace(3f);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        mChart.setData(data);

        mChart.highlightValues(null);

        mChart.invalidate();
    }
}
