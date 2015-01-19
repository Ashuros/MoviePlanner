package com.mp.movieplanner.tasks;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.mp.movieplanner.common.ImageCache;

public class DownloadListItemTask extends DownloadTask {

	private final long id;
	
	public DownloadListItemTask(ImageCache cache, ImageView imageView, long id) {
		super(cache, imageView);
		this.id = id;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		long forPosition = (long) imageView.getTag();
		if (forPosition == id && bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}
	}
}
