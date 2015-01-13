package com.mp.movieplanner.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import android.graphics.Bitmap;


public class ImageCache {
	
	private static final int IMAGE_CACHE_SIZE = 250;
	
	private final Map<String, Bitmap> cache;
	
	public ImageCache() {
		cache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(ImageCache.IMAGE_CACHE_SIZE + 1, .75f, true) {
			@Override
			public boolean removeEldestEntry(Map.Entry<String, Bitmap> eldest) {
				return size() > ImageCache.IMAGE_CACHE_SIZE;
			}
		});
	}
	
	public Bitmap get(String urlString) {
		return cache.get(urlString);
	}
	
	public void put(String urlString, Bitmap bitmap) {
		cache.put(urlString, bitmap);
	}
}
