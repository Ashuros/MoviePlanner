package com.mp.movieplanner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SwipeViewAdapter extends FragmentPagerAdapter {

    private List<Fragment> views;

    public SwipeViewAdapter(FragmentManager fm) {
        super(fm);
        views = new ArrayList<>();
        views.add(new MovieListFragment());
        views.add(new MovieDetailsFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
