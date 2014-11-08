package com.mp.movieplanner;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class SearchableActivity extends ActionBarActivity implements
			SearchMovieListFragment.OnSearchElementSelectedListener{

	private static final String TAG_LIST_SEARCH = "SEARCH_FRAGMENT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_view);

		FragmentManager fm = getSupportFragmentManager();
		
		SearchMovieListFragment fragment =
				(SearchMovieListFragment) fm.findFragmentByTag(TAG_LIST_SEARCH);
		
		if (fragment == null) {
			fragment = new SearchMovieListFragment();
			
			Intent intent = getIntent();
			
			if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
				Log.i("SEARCH", "DOWNLOADING DATA IN SEARCH");
				String query = intent.getStringExtra(SearchManager.QUERY);				
				Bundle bundle = new Bundle();
				bundle.putString("QUERY", query);
				fragment.setArguments(bundle);
			}			
			fm.beginTransaction().add(R.id.fragment_search_container, fragment, TAG_LIST_SEARCH).commit();
		}
	}

	@Override
	public void onSearchElementSelected(int position) {		
	}
}
