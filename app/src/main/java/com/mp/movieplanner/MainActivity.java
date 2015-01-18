package com.mp.movieplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import com.mp.movieplanner.common.CollectionType;

public class MainActivity extends Activity implements MovieListFragment.OnMovieSelectedListener, TvListFragment.OnTvSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String TAG_MOVIE_LIST = "movie_list";

    private MoviePlannerApp app;

    private CollectionType type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        app = (MoviePlannerApp) getApplication();

        CollectionPagerAdapter swipeAdapter = new CollectionPagerAdapter(getFragmentManager());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(swipeAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
                type = CollectionType.valueOf(position);
                app.setType(type);
                Log.d(TAG, type.toString());
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
    public void onMovieSelected(long position) {}

    @Override
    public void onTvSelected(long position) {}

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