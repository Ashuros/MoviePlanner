package com.mp.movieplanner.tasks;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.mp.movieplanner.common.ImageCache;

public class DownloadListImageTask extends DownloadImageTask {

	private final long id;
	
	public DownloadListImageTask(ImageCache cache, ImageView imageView, long id) {
		super(cache, imageView);
		this.id = id;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		long forPosition = (long) imageView.getTag();
		if (bitmap != null && forPosition == id) {
			imageView.setImageBitmap(bitmap);
		}
	}
}
