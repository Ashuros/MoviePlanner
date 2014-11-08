package com.mp.movieplanner;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.mp.movieplanner.data.service.MovieService;
import com.mp.movieplanner.data.service.impl.MovieServiceImpl;
import com.mp.movieplanner.util.ImageCache;

public class MoviePlannerApp extends Application {

    private MovieService movieService;
    private ConnectivityManager cMgr;
    private ImageCache mCache;

    public MovieService getDataManager() {
        return movieService;
    }

    public ImageCache getImageCache() {
        return mCache;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        movieService = new MovieServiceImpl(this);
        mCache = new ImageCache();
    }

    public boolean isConnectionPresent() {
        NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
        return (netInfo != null) && (netInfo.getState() != null) && netInfo.getState().equals(State.CONNECTED);
    }

}
