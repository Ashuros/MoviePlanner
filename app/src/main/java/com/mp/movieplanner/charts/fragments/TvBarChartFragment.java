package com.mp.movieplanner.charts.fragments;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.mp.movieplanner.MoviePlannerApp;
import com.mp.movieplanner.data.service.TvService;
import com.mp.movieplanner.model.Tv;

import java.util.ArrayList;
import java.util.List;

public class TvBarChartFragment extends AbstractBarChartFragment {
    private TvService tvService;

    @Override
    protected void setData() {
        MoviePlannerApp app = (MoviePlannerApp) getActivity().getApplication();
        tvService = app.getTvService();

        List<Tv> tvs = tvService.getAllTvs();

        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < tvs.size(); i++) {
            xVals.add(tvs.get(i).getOriginal_name());
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < tvs.size(); i++) {
            yVals1.add(new BarEntry((float) tvs.get(i).getVote_average(), i));
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
