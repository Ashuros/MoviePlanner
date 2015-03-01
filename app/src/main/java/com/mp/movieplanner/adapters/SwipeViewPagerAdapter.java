package com.mp.movieplanner.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SwipeViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public SwipeViewPagerAdapter(FragmentManager fm, Fragment... frags) {
        super(fm);
        fragments = new ArrayList<>(Arrays.asList(frags));
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
