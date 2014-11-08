package com.mp.movieplanner.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import com.mp.movieplanner.R;

public class CheckableRelativeLayout extends RelativeLayout implements Checkable {

	private boolean isChecked;
	private List<Checkable> checkableViews;
	
	
	public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}
	
	public CheckableRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	
	public CheckableRelativeLayout(Context context, int checkableId) {
		super(context);
		init(null);
	}
	
	private void init(AttributeSet attrs) {
		isChecked = false;
		checkableViews = new ArrayList<Checkable>(5);
	}
	
	@Override
	public boolean isChecked() {
		return isChecked;
	}
	
	@Override
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;		
		if (isChecked) {
			setBackgroundColor(getResources().getColor(R.color.translucent_blue));
		} else {
			setBackgroundColor(0x01060013);			
		}
		for (Checkable c : checkableViews) {
		// Pass the information to all the child Checkable widgets 
			c.setChecked(isChecked);
		}
	}
	
	@Override
	public void toggle() {
		isChecked = !isChecked;
		for (Checkable c : checkableViews) {
			// Pass the information to all the child Checkable widgets   
			c.toggle();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//super.onTouchEvent(event);
		return false;
	}
	
	@Override
	public void onFinishInflate() {
		super.onFinishInflate();
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			findCheckableChildren(getChildAt(i));
		}
	}
	
	private void findCheckableChildren(View v) {
		if (v instanceof Checkable) {
			this.checkableViews.add((Checkable) v);
		}
		if (v instanceof ViewGroup) {
			final ViewGroup vg = (ViewGroup) v;
			final int childCount = vg.getChildCount();
			for (int i = 0; i < childCount; i++) {
				findCheckableChildren(vg.getChildAt(i));
			}
		}
	}
}
