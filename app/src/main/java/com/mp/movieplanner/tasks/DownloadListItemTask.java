package com.mp.movieplanner.tasks;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.mp.movieplanner.util.ImageCache;

public class DownloadListItemTask extends DownloadTask {

	private final long mId;
	
	public DownloadListItemTask(ImageCache cache, ImageView imageView, long id) {
		super(cache, imageView);
		mId = id;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		long forPosition = (long) imageView.getTag();
		if (forPosition == mId && bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}
	}
}
