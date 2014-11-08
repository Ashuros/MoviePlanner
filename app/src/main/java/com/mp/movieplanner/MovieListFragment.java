package com.mp.movieplanner;

import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.mp.movieplanner.data.service.DataManager;
import com.mp.movieplanner.data.MoviePlannerContract.Movies;

public class MovieListFragment extends ListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {
	
	public static String TAG = MovieListFragment.class.getSimpleName();
	
	private OnMovieSelectedListener mCallback;	
	//private ArrayAdapter<Movie> adapter;
	private DataManager mDataManager;

	private MovieCursorAdapter mAdapter;
	
	public interface OnMovieSelectedListener {
		public void onMovieSelected(long position);		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(TAG, "onAttach(Activity)");
		mDataManager = ((MoviePlannerApp)activity.getApplication()).getDataManager();
		try {
			mCallback = (OnMovieSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnMovieSelectedListener");
		}	
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		Log.i(TAG, "onCreate(Bundle)");
		final int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.layout.simple_list_item_activated_1
				: android.R.layout.simple_list_item_1;
	
		//adapter = new ArrayAdapter<>(getActivity(), layout);
		//setListAdapter(adapter);
		//setListAdapter(mAdapter);		
		setRetainInstance(true);

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    Log.i(TAG, "onActivityCreated(Bundle)");
	    super.onActivityCreated(savedInstanceState);
	    mAdapter = new MovieCursorAdapter(getActivity(), null, 0, ((MoviePlannerApp)getActivity().getApplication()).getImageCache());
	    setListAdapter(mAdapter);
	    getLoaderManager().initLoader(0, null, this);
	    
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart()");
		if (getFragmentManager().findFragmentById(R.id.movie_details_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume()");
		//new GetMoviesTask().execute();
		getLoaderManager().restartLoader(0, null, this);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		Log.i(TAG, "onDetach()");
		mCallback = null;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Notify the parent activity of selected item		
		if (mCallback != null) {
			// Set the item as checked to be highlighted when in two-pane layout
			Log.i("CHECKABLE", String.valueOf(position));
			//getListView().setItemChecked(position, true);
			l.setItemChecked(position, true);
			Cursor c = ((MovieCursorAdapter)l.getAdapter()).getCursor();
			c.moveToPosition(position);
			long movieId = c.getLong(c.getColumnIndex(Movies._ID));
			Log.i("DAMN IT!", movieId  + "");
			mCallback.onMovieSelected(movieId);
		}
	}
	
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity()) {
			@Override
			public Cursor loadInBackground() {
				return mDataManager.getMovieCursor();
			}
		};
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);		
	}
	
	

/*	// Fill movie list from database in seperate thread (Prevent ANR - Activity Not Responding).
	private class GetMoviesTask extends AsyncTask<Void, Void, List<Movie>> {		
		@Override
		protected List<Movie> doInBackground(Void... search) {			
			return mDataManager.getAllMovies();
		}
		
		@Override
		protected void onPostExecute(List<Movie> movies) {
			if (movies != null) {
				adapter.clear();			
				for (Movie m : movies) {
					adapter.add(m);
				}
				adapter.notifyDataSetChanged();
			}
		}
	}*/
		  
	@Override
	public void onPause() {
		Log.i(TAG, "onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i(TAG, "onStop()");
		super.onStop();
	}
	  
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy()");
	}
}
