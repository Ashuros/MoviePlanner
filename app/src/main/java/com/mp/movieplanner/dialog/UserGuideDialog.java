package com.mp.movieplanner.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mp.movieplanner.R;

public class UserGuideDialog extends DialogFragment {

    public static UserGuideDialog newInstance() {
        return new UserGuideDialog();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.user_guide_title);

        View dialogView = inflater.inflate(R.layout.user_guide, null);
        builder.setView(dialogView);

//        TextView textView1 = (TextView) dialogView.findViewById(R.id.user_guide_text_1);
//        textView1.setText(R.string.user_guide_text1);
//
//        ImageView imageView1 = (ImageView) dialogView.findViewById(R.id.user_guide_image_1);
//        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.search));
//
//        TextView textView2 = (TextView) dialogView.findViewById(R.id.user_guide_text_2);
//        textView2.setText(R.string.user_guide_text2);
//
//        ImageView imageView2 = (ImageView) dialogView.findViewById(R.id.user_guide_image_2);
//        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.voice_search));

        return builder.create();
    }
}
