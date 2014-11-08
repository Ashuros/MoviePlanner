package com.mp.movieplanner.tasks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.mp.movieplanner.util.ImageCache;
import com.mp.movieplanner.util.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
	
	public static final String TAG = DownloadTask.class.getSimpleName();
	
	
	public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
	public static final String IMAGE_SIZE = "w92/";
	
	private final ImageCache mCache;
	private final Drawable mPlaceholder;
	
	protected final ImageView mImageView;
	
	public DownloadTask(ImageCache cache, ImageView imageView) {
		mCache = cache;
		mImageView = imageView;
		mPlaceholder = imageView.getContext()
								.getResources()
								.getDrawable(android.R.drawable.gallery_thumb);		
	}
	
	@Override
	protected void onPreExecute() {
		mImageView.setImageDrawable(mPlaceholder);
	}
	
	@Override
	protected Bitmap doInBackground(String... inputUrls) {
		if (mCache.get(inputUrls[0]) != null) {
			return mCache.get(inputUrls[0]);
		}
		
		Log.i(TAG, "Http trip for image: " + inputUrls[0]);
		Bitmap bitmap = null;
		
		try {
	         // NOTE, be careful about just doing "url.openStream()"
	         // it's a shortcut for openConnection().getInputStream() and doesn't set timeouts
	         // (the defaults are "infinite" so it will wait forever if endpoint server is down)
	         // do it properly with a few more lines of code . . .
	         URL url = new URL(IMAGE_URL + IMAGE_SIZE + inputUrls[0]);
	         URLConnection conn = url.openConnection();
	         conn.setConnectTimeout(3000);
	         conn.setReadTimeout(5000);
	         bitmap = BitmapFactory.decodeStream(conn.getInputStream());
	         if (bitmap != null) {
	             mCache.put(inputUrls[0], bitmap);
	          }
		} catch (MalformedURLException e) {
			Log.i(TAG, "Exception loading image, malformed URL", e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(TAG, "Exception loading image, IO error", e);
			e.printStackTrace();
		}
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (bitmap != null) {
			mImageView.setImageBitmap(bitmap);
		}
	}

}
