package com.mp.movieplanner;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mp.movieplanner.adapters.TvCursorAdapter;
import com.mp.movieplanner.common.Utils;
import com.mp.movieplanner.data.TvContract;
import com.mp.movieplanner.data.service.TvService;
import com.mp.movieplanner.dialog.RemoveDialog;
import com.mp.movieplanner.model.Tv;

public class TvListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemLongClickListener, RemoveDialog.RemoveDialogListener {

    public static String TAG = TvListFragment.class.getSimpleName();

    private OnTvSelectedListener callback;

    private TvService tvService;

    private TvCursorAdapter adapter;

    private Tv tvToRemove;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach(Activity)");
        tvService = ((MoviePlannerApp) activity.getApplication()).getTvService();
        try {
            callback = (OnTvSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMovieSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated(Bundle)");
        adapter = new TvCursorAdapter(getActivity(), null, 0, ((MoviePlannerApp) getActivity().getApplication()).getImageCache());
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        getListView().setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
        tvToRemove = Utils.buildTvFromCursor(cursor);
        DialogFragment dialog = RemoveDialog.newInstance(tvToRemove.getOriginal_name());
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getFragmentManager(), "REMOVE_DIALOG_TAG");
        return true;
    }

    @Override
    public void onRemoveDialogPositiveClick(DialogFragment dialog) {
        new RemoveTvTask().execute(tvToRemove);
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
        callback = null;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        if (callback != null) {
            listView.setItemChecked(position, true);

            Cursor c = ((TvCursorAdapter) listView.getAdapter()).getCursor();
            c.moveToPosition(position);
            long tvId = c.getLong(c.getColumnIndex(TvContract.Tv._ID));

            callback.onTvSelected(tvId);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity()) {
            @Override
            public Cursor loadInBackground() {
                return tvService.getTvCursor();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        adapter.swapCursor(null);
    }


    public interface OnTvSelectedListener {
        public void onTvSelected(long position);
    }

    private class RemoveTvTask extends AsyncTask<Tv, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Tv... tv) {
            return tvService.deleteTv(tv[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Utils.showToastById(getActivity(), R.string.entry_deleted);
            } else {
                Utils.showToastById(getActivity(), R.string.delete_error);
            }
            getLoaderManager().restartLoader(0, null, TvListFragment.this);
        }
    }

}
