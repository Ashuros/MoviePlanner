package com.mp.movieplanner.tasks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.mp.movieplanner.themoviedb.TheMovieDbURL;
import com.mp.movieplanner.common.ImageCache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

	public static final String TAG = DownloadImageTask.class.getSimpleName();

	private final ImageCache imageCache;
	private final Drawable imagePlaceholder;
	
	protected final ImageView imageView;
	
	public DownloadImageTask(ImageCache cache, ImageView imageView) {
		imageCache = cache;
		this.imageView = imageView;
		imagePlaceholder = imageView.getContext()
								.getResources()
								.getDrawable(android.R.drawable.gallery_thumb);
	}
	
	@Override
	protected void onPreExecute() {
		imageView.setImageDrawable(imagePlaceholder);
	}
	
	@Override
	protected Bitmap doInBackground(String... inputUrls) {
        if (inputUrls[0] == null) {
            return null;
        }

		if (imageCache.get(inputUrls[0]) != null) {
			return imageCache.get(inputUrls[0]);
		}
		
		Log.i(TAG, "Http trip for image: " + inputUrls[0]);
		Bitmap bitmap = null;
		
		try {
	         URL url = new URL(TheMovieDbURL.IMAGE_URL + TheMovieDbURL.IMAGE_SIZE_W92 + inputUrls[0]);
	         URLConnection conn = url.openConnection();
	         conn.setConnectTimeout(3000);
	         conn.setReadTimeout(5000);
	         bitmap = BitmapFactory.decodeStream(conn.getInputStream());
	         if (bitmap != null) {
	             imageCache.put(inputUrls[0], bitmap);
	          }
		} catch (MalformedURLException e) {
			Log.i(TAG, "Exception while loading image, malformed URL", e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(TAG, "Exception while loading image, IO error", e);
			e.printStackTrace();
		}
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}
	}
}
