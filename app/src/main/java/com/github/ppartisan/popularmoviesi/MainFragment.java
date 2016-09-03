package com.github.ppartisan.popularmoviesi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.ppartisan.popularmoviesi.utils.FetchJsonMovieDataUtils;
import com.github.ppartisan.popularmoviesi.utils.MetricUtils;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainFragment extends Fragment implements FetchJsonMovieDataTask.OnJsonMovieDataReadyListener {

    private static final String SORT_PREFERENCE_KEY = "sort_pref_key";
    private static final String RECYCLER_VIEW_POSITION_KEY = "rv_position_key";

    private FetchJsonMovieDataTask task = null;
    private JsonMovieDatabaseParser parser;

    private MovieGridAdapter mAdapter;
    private RecyclerView mRecyclerView;

    //Stores list position during config changes
    private int listPosition = 0;

    //Could save this value to SharedPreferences for persistence beyond app's scope. Future idea?
    private String sortPreference = FetchJsonMovieDataUtils.SORT_BY_POPULARITY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new MovieGridAdapter(null);
        final int columnCount = MetricUtils.getColumnCountForScreenWidth(getContext());

        mRecyclerView = (RecyclerView) root.findViewById(R.id.fm_recycler);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnCount));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        parser = new JsonMovieDatabaseParser();

        if (savedInstanceState != null) {
            sortPreference = savedInstanceState.getString(
                    SORT_PREFERENCE_KEY, FetchJsonMovieDataUtils.SORT_BY_POPULARITY
            );
            listPosition = savedInstanceState.getInt(RECYCLER_VIEW_POSITION_KEY,0);
        }

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        launchFetchMovieDataTask(sortPreference);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        final int firstVisibleItem =
                ((GridLayoutManager)mRecyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();

        outState.putInt(RECYCLER_VIEW_POSITION_KEY,
                (firstVisibleItem < 0) ? listPosition : firstVisibleItem
        );

        outState.putString(SORT_PREFERENCE_KEY, sortPreference);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort_popularity:
                launchFetchMovieDataTask(FetchJsonMovieDataUtils.SORT_BY_POPULARITY);
                break;
            case R.id.action_sort_votes:
                launchFetchMovieDataTask(FetchJsonMovieDataUtils.SORT_BY_VOTE);
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onJsonMovieDataReady(String movieDataJson) {
        try {
            List<MovieModel> movieModels = parser.getMovieModelsFromJson(movieDataJson);
            mAdapter.setMovieModels(movieModels);
            if (listPosition != 0 ) {
                mRecyclerView.getLayoutManager().scrollToPosition(listPosition);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            task = null;
        }
    }

    private void launchFetchMovieDataTask(String sortCode) {
        sortPreference = sortCode;
        if (task == null) {
            task = new FetchJsonMovieDataTask(
                    new WeakReference<FetchJsonMovieDataTask.OnJsonMovieDataReadyListener>(this)
            );
            task.execute(sortCode);
        }
    }

}
