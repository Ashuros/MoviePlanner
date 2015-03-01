package com.mp.movieplanner;


import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.mp.movieplanner.adapters.SwipeViewPagerAdapter;
import com.mp.movieplanner.fragments.GalleryFragment;
import com.mp.movieplanner.fragments.TvDetailsFragment;

public class TvDetails extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view_pager);

        long position = getIntent().getExtras().getLong("POSITION");

        Bundle bundle = new Bundle();
        bundle.putLong("POSITION", position);

        TvDetailsFragment tvDetailsFragment = new TvDetailsFragment();
        tvDetailsFragment.setArguments(bundle);

        GalleryFragment gallery = new GalleryFragment();
        gallery.setArguments(bundle);

        final SwipeViewPagerAdapter detailsAdapter = new SwipeViewPagerAdapter(getFragmentManager(), tvDetailsFragment, gallery);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.details_pager);
        viewPager.setAdapter(detailsAdapter);
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
                viewPager.setBackgroundColor(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            }
        };

        actionBar.addTab(actionBar.newTab()
                .setText("Details")
                .setTabListener(tabListener));

        actionBar.addTab(actionBar.newTab()
                .setText("Gallery")
                .setTabListener(tabListener));
    }
}
