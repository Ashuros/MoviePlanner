package com.mp.movieplanner;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity implements MovieListFragment.OnMovieSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String TAG_MOVIE_LIST = "movie_list";

    private MoviePlannerApp app;

    private FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        app = (MoviePlannerApp) getApplication();

        fm = getFragmentManager();
        MovieListFragment listFragment = (MovieListFragment) fm.findFragmentByTag(TAG_MOVIE_LIST);

        if (findViewById(R.id.fragment_container) != null) {
            if (listFragment == null) {
                listFragment = new MovieListFragment();

                FragmentTransaction t = fm.beginTransaction();
                t.add(R.id.fragment_container, listFragment, TAG_MOVIE_LIST);
                t.commit();
            }
        }
    }

    @Override
    public void onMovieSelected(long position) {
        MovieDetailsFragment detailsFrag = null;
        if (fm.findFragmentById(R.id.fragment_container) != null) {
            detailsFrag = new MovieDetailsFragment();
            FragmentTransaction t = fm.beginTransaction();
            t.replace(R.id.fragment_container, detailsFrag);
            t.addToBackStack(null);
            t.commit();
            fm.executePendingTransactions();
        }
        detailsFrag.updateMovieView(position);
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