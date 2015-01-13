package com.mp.movieplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity implements MovieListFragment.OnMovieSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String TAG_MOVIE_LIST = "movie_list";

    private MoviePlannerApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        app = (MoviePlannerApp) getApplication();

        SwipeViewAdapter swipeAdapter = new SwipeViewAdapter(getFragmentManager());
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
                                  .setText(R.string.movie_tab)
                                  .setTabListener(tabListener));

        actionBar.addTab(actionBar.newTab()
                                  .setText(R.string.tv_tab)
                                  .setTabListener(tabListener));
    }

    @Override
    public void onMovieSelected(long position) {
//        MovieDetailsFragment detailsFrag = null;
//        if (fm.findFragmentById(R.id.fragment_container) != null) {
//            detailsFrag = new MovieDetailsFragment();
//            FragmentTransaction t = fm.beginTransaction();
//            t.replace(R.id.fragment_container, detailsFrag);
//            t.addToBackStack(null);
//            t.commit();
//            fm.executePendingTransactions();
//        }
//        detailsFrag.updateMovieView(position);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.search_movies).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (app.isConnectionPresent()) {
                    return false;
                }
                Toast.makeText(getApplicationContext(),
                        getString(R.string.search_network_unavailable),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;
    }
}