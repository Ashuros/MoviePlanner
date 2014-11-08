
package com.mp.movieplanner;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity 
	implements MovieListFragment.OnMovieSelectedListener{
	
	public static final String TAG = MainActivity.class.getSimpleName();
	
	private static final String TAG_MOVIE_LIST = "movie_list";
	
	private MoviePlannerApp app;
	
	private FragmentManager fm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		app = (MoviePlannerApp) getApplication();
		
		fm = getSupportFragmentManager();
		MovieListFragment listFragment = (MovieListFragment) fm.findFragmentByTag(TAG_MOVIE_LIST);

		// If we are in two-pane mode execute this if block
		if (findViewById(R.id.fragment_container) != null) { 			
			//if (savedInstanceState == null) {
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
			fm.executePendingTransactions();
			t.addToBackStack(null);			
			t.commit();
			fm.executePendingTransactions();
		}
		detailsFrag.updateMovieView(position);
	}
	
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	   // Inflate the menu; this adds items to the action bar if it is present.
	        MenuInflater inflater = getMenuInflater();

	        inflater.inflate(R.menu.options_menu, menu);

	        // Associate searchable configuration with the SearchView
	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

	        // The below line returned null even though it was used in Google sample code
	        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
	        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);

	        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	        searchView.setSearchableInfo(searchManager.getSearchableInfo(
	                new ComponentName(getApplicationContext(), SearchableActivity.class)));	        
	        //searchView.setSubmitButtonEnabled(true);	        
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
	        
	        return super.onCreateOptionsMenu(menu);
	    }
}