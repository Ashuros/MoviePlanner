package com.mp.movieplanner.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mp.movieplanner.MoviePlannerApp;
import com.mp.movieplanner.R;
import com.mp.movieplanner.common.CollectionType;
import com.mp.movieplanner.data.service.MovieService;
import com.mp.movieplanner.data.service.Service;
import com.mp.movieplanner.data.service.TvService;

public class AddDialog extends DialogFragment {

    public interface AddDialogListener {
        public void onAddDialogPositiveClick(DialogFragment dialog);
	}

    public static String TITLE = "POSITION";
    public static String OVERVIEW = "OVERVIEW";
    public static String DATE = "DATE";


	public static AddDialog newInstance(String title, String overview, String date) {
		AddDialog dialog = new AddDialog();

		Bundle args = new Bundle();
		args.putString(TITLE, title);
        args.putString(OVERVIEW, overview);
        args.putString(DATE, date);
		dialog.setArguments(args);
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
		String title = args.getString(TITLE) == null ? "No title" : args.getString(TITLE);
        String overview = args.getString(OVERVIEW) == null ? "No overview" : args.getString(OVERVIEW);
        String date = args.getString(DATE) == null ? "No date" : args.getString(DATE);

        MoviePlannerApp app = (MoviePlannerApp) getActivity().getApplication();
		String dialogTitle = String.format(getString(R.string.dialog_add_movie_title), title);
        String firstAirDate = String.format(getString(R.string.first_air_date), date);

        LayoutInflater inflater = getActivity().getLayoutInflater();
					
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = inflater.inflate(R.layout.add_entry_dialog, null);
        builder.setView(dialogView);

        TextView overviewView = (TextView) dialogView.findViewById(R.id.dialog_overview);
        overviewView.setText(overview);
        TextView dateView = (TextView) dialogView.findViewById(R.id.dialog_date);
        dateView.setText(firstAirDate);

		builder.setTitle(dialogTitle)
			   .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       AddDialogListener searchFragment = (AddDialogListener) AddDialog.this.getTargetFragment();
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
