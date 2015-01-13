package com.mp.movieplanner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class CollectionPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public CollectionPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new MovieListFragment());
        fragments.add(new TvListFragment());
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + fragments.get(position);
    }
}
