package com.mp.movieplanner.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

import com.mp.movieplanner.json.JsonMovieParser;
import com.mp.movieplanner.json.MovieFeed;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.http.AndroidHttpClient;
import android.util.Log;
public class Utils {
	
	private static final AbstractHttpClient httpClient;
	
	private static final HttpRequestRetryHandler retryHandler;
	
	static {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		
		HttpParams connManagerParams = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(connManagerParams, 5);
		ConnManagerParams.setMaxConnectionsPerRoute(connManagerParams, new ConnPerRouteBean(5));;
		ConnManagerParams.setTimeout(connManagerParams, 15 * 1000);
		
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(connManagerParams, schemeRegistry);
		
		HttpParams clientParams = new BasicHttpParams();
		HttpProtocolParams.setUserAgent(clientParams, "MoviePlanner/1.0");
		HttpConnectionParams.setConnectionTimeout(clientParams, 15 * 1000);
		HttpConnectionParams.setSoTimeout(clientParams,  15 * 1000);
		
		httpClient = new DefaultHttpClient(cm, clientParams);
		retryHandler = new DefaultHttpRequestRetryHandler(5, false) {
			
			public boolean retryRequest(IOException exception, int executionCount,
					HttpContext context) {
				if (!super.retryRequest(exception, executionCount, context)) {
					Log.d("HTTP retry-handler", "Won't retry");
					return false;
				}
				try {
					Thread.sleep(2000);					
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
				Log.d("HTTP retry-handler", "Retrying request...");
				return true;
			}
		};
		
		httpClient.setHttpRequestRetryHandler(retryHandler);	
	}
	
	public static HttpClient getHttpClient() {
		return AndroidHttpClient.newInstance("");
	}	
	
	
	private static MovieFeed jsonParser = new JsonMovieParser();
	
	public static MovieFeed getJsonParser() {
		return jsonParser;
	}
}
