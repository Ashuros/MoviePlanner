package com.mp.movieplanner.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mp.movieplanner.R;


public class AddMovieDialog extends DialogFragment {
	
	public interface NoticeDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
	}
	
	public static String ARG_TITLE = "TITLE";
	
//	private NoticeDialogListener  mListener;
	
	
	public static AddMovieDialog newInstance(String movieTitle) {
		AddMovieDialog dialog = new AddMovieDialog();
		
		Bundle args = new Bundle();
		args.putString(ARG_TITLE, movieTitle);
		
		dialog.setArguments(args);
		return dialog;		
	}
	
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		try {
//			mListener = (NoticeDialogListener) activity;
//		} catch(ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement NoticeDialogListener");
//		}
//	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		String title = getArguments().getString(ARG_TITLE);
		
		String movieTitle = String.format(getString(R.string.dialog_add_movie_title), title);
					
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(movieTitle)
			   .setPositiveButton(R.string.dialog_add_movie_confirm, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
//					mListener.onDialogPositiveClick(AddMovieDialog.this);
					NoticeDialogListener mSearchFragment =(NoticeDialogListener)AddMovieDialog.this.getTargetFragment();
					mSearchFragment.onDialogPositiveClick(AddMovieDialog.this);
				}				   
		});
		
		builder.setNegativeButton(R.string.dialog_add_movie_cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}			
		});
		return builder.create();
	}
}
