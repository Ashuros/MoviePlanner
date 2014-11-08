package com.mp.movieplanner;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.mp.movieplanner.data.service.DataManager;
import com.mp.movieplanner.data.service.impl.MovieServiceImpl;
import com.mp.movieplanner.util.ImageCache;

public class MoviePlannerApp extends Application {

	private DataManager mDataManager;
	private ConnectivityManager cMgr;
	private ImageCache mCache;

	public DataManager getDataManager() {
		return mDataManager;
	}
	
	public ImageCache getImageCache() {
		return mCache;
	}

	@Override
	public void onCreate() {
		super.onCreate();		
		// Should create database instance in seperate thread (could be long running operation)
//		new Thread( new Runnable() {
//			@Override
//			public void run() {
		cMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);	
		mDataManager = new MovieServiceImpl(MoviePlannerApp.this);
		mCache = new ImageCache();
//			}
//		}).start();

	}

	public boolean isConnectionPresent() {
		NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
		if ((netInfo != null) && (netInfo.getState() != null)) {
			return netInfo.getState().equals(State.CONNECTED);
		}
		return false;
	}

}
