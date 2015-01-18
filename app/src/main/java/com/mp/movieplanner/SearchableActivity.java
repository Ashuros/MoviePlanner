package com.mp.movieplanner;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class SearchableActivity extends Activity implements
        SearchMovieListFragment.OnSearchMovieSelectedListener, SearchTvFragment.OnSearchTvSelectedListener {

    private static final String TAG = SearchableActivity.class.getSimpleName();

    private static final String TAG_LIST_SEARCH = "SEARCH_FRAGMENT";

    private MoviePlannerApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");
        app = (MoviePlannerApp) getApplication();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent(Intent intent)");
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentByTag(TAG_LIST_SEARCH);

        if (fragment == null) {

            fragment = app.getType().newSearchFragmentInstance();

            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                Log.d(TAG, "Received query {" + query + "}");
                Bundle bundle = new Bundle();
                bundle.putString("QUERY", query);
                fragment.setArguments(bundle);
            }
            fm.beginTransaction().add(R.id.fragment_search_container, fragment, TAG_LIST_SEARCH).commit();
        }
    }

    @Override
    public void onSearchMovieSelected(int position) {}

    @Override
    public void onSearchTvSelected(int position) {}
}
