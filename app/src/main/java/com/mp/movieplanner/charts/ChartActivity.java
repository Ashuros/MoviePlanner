package com.mp.movieplanner.charts;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.mp.movieplanner.R;
import com.mp.movieplanner.adapters.SwipeViewPagerAdapter;
import com.mp.movieplanner.charts.fragments.MovieBarChartFragment;
import com.mp.movieplanner.charts.fragments.PieChartFragment;
import com.mp.movieplanner.charts.fragments.TvBarChartFragment;

public class ChartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view_pager);

        SwipeViewPagerAdapter swipeAdapter = new SwipeViewPagerAdapter(getFragmentManager(), new PieChartFragment(), new MovieBarChartFragment(), new TvBarChartFragment());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(swipeAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }
        });

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            }
        };

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.pie_chart)
                .setTabListener(tabListener));

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.movie_bar_chart)
                .setTabListener(tabListener));

        actionBar.addTab(actionBar.newTab()
                .setText(R.string.tv_bar_chart)
                .setTabListener(tabListener));
    }

}
