package com.mp.movieplanner;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mp.movieplanner.common.Utils;
import com.mp.movieplanner.data.service.TvService;
import com.mp.movieplanner.dialog.AddDialog;
import com.mp.movieplanner.model.Tv;
import com.mp.movieplanner.model.TvSearchResult;

import java.util.Collections;
import java.util.List;

public class SearchTvFragment extends ListFragment implements AdapterView.OnItemLongClickListener,
        AddDialog.NoticeDialogListener {

    private static final String TAG = SearchTvFragment.class.getSimpleName();

    private OnSearchTvSelectedListener callback;
    private ArrayAdapter<TvSearchResult> adapter;

    private MoviePlannerApp app;
    private TvService tvService;

    private TvSearchResult tvToAdd;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach(Activity)");
        try {
            callback = (OnSearchTvSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSearchTvSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle");

        final int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        adapter = new ArrayAdapter<>(getActivity(), layout);
        setListAdapter(adapter);

        setRetainInstance(true);
        app = (MoviePlannerApp) getActivity().getApplication();
        tvService = app.getTvService();
        new SearchTvTask().execute(getArguments().getString("QUERY"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated(Bundle)");
        getListView().setLongClickable(true);
        getListView().setOnItemLongClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        tvToAdd = (TvSearchResult) parent.getItemAtPosition(position);
        DialogFragment dialog = AddDialog.newInstance(tvToAdd.getOriginal_name());
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getFragmentManager(), "ADD_DIALOG_TAG");
        return true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        new AddTvToDatabase().execute(tvToAdd);
    }

    private void showToast(int id) {
        Toast.makeText(getActivity(),
                getString(id),
                Toast.LENGTH_SHORT).show();
    }

    public interface OnSearchTvSelectedListener {
        public void onSearchTvSelected(int position);
    }

    private class SearchTvTask extends AsyncTask<String, Void, List<TvSearchResult>> {
        @Override
        protected List<TvSearchResult> doInBackground(String... query) {
            if (app.isConnectionPresent()) {
                return Utils.getTheMovieDBClient().searchTvs(query[0]);
            }
            return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(List<TvSearchResult> tvs) {
            if (tvs.isEmpty()) {
                showToast(R.string.search_error_retrieving_data);
                ((Activity) callback).finish();
                return;
            }
            tvs = filterTvsNotPresentInDatabase(tvs);
            adapter.clear();
            for (TvSearchResult tv : tvs) {
                adapter.add(tv);
            }
            adapter.notifyDataSetChanged();
        }

        private List<TvSearchResult> filterTvsNotPresentInDatabase(List<TvSearchResult> tvs) {
            List<Tv> allTvs = tvService.getAllTvs();
            List<TvSearchResult> dbTvs = Utils.toTvSearchResult(allTvs);
            tvs.removeAll(dbTvs);
            return tvs;
        }
    }

    private class AddTvToDatabase extends AsyncTask<TvSearchResult, Void, Long> {
        @Override
        protected Long doInBackground(TvSearchResult... tvs) {
            Tv tv = Utils.getTheMovieDBClient().findTv(tvs[0].getId());
            return tvService.saveTv(tv);
        }

        @Override
        protected void onPostExecute(Long tvId) {
            if (tvId != 0) {
                adapter.remove(tvToAdd);
                adapter.notifyDataSetChanged();
                showToast(R.string.tv_saved);
            } else {
                showToast(R.string.tv_error_save);
            }
        }
    }
}
