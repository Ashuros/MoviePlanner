package com.mp.movieplanner.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mp.movieplanner.R;

public class RemoveDialog extends DialogFragment {
    public interface RemoveDialogListener {
        public void onRemoveDialogPositiveClick(DialogFragment dialog);
    }

    public static String ARG_TITLE = "TITLE";

    public static RemoveDialog newInstance(String title) {
        RemoveDialog dialog = new RemoveDialog();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(ARG_TITLE);

        String dialogTitle = String.format(getString(R.string.dialog_remove), title);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(dialogTitle)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RemoveDialogListener searchFragment =(RemoveDialogListener)RemoveDialog.this.getTargetFragment();
                        searchFragment.onRemoveDialogPositiveClick(RemoveDialog.this);
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
