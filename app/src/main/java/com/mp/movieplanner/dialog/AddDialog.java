package com.mp.movieplanner.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mp.movieplanner.R;


public class AddDialog extends DialogFragment {

    public interface AddDialogListener {
        public void onAddDialogPositiveClick(DialogFragment dialog);
	}

    public static String ARG_TITLE = "TITLE";

	public static AddDialog newInstance(String title) {
		AddDialog dialog = new AddDialog();

		Bundle args = new Bundle();
		args.putString(ARG_TITLE, title);
		dialog.setArguments(args);
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString(ARG_TITLE);
		
		String dialogTitle = String.format(getString(R.string.dialog_add_movie_title), title);
					
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(dialogTitle)
			   .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					AddDialogListener searchFragment =(AddDialogListener)AddDialog.this.getTargetFragment();
					searchFragment.onAddDialogPositiveClick(AddDialog.this);
				}				   
		});
		
		builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}			
		});
		return builder.create();
	}
}
