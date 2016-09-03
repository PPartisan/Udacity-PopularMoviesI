package com.github.ppartisan.popularmoviesi;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.github.ppartisan.popularmoviesi.utils.FetchJsonMovieDataUtils;

import java.lang.ref.WeakReference;

import static com.github.ppartisan.popularmoviesi.utils.FetchJsonMovieDataUtils.SORT_BY_POPULARITY;

public class FetchJsonMovieDataTask extends AsyncTask<String, Void, String> {

    private static final String TAG = FetchJsonMovieDataTask.class.getSimpleName();

    private final WeakReference<OnJsonMovieDataReadyListener> listener;

    public FetchJsonMovieDataTask(@NonNull WeakReference<OnJsonMovieDataReadyListener> listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... sortCode) {
        String code = (sortCode.length > 0) ? sortCode[0] : SORT_BY_POPULARITY;
        String request = FetchJsonMovieDataUtils.getMovieDatabaseRequestString(code);
        return FetchJsonMovieDataUtils.getMovieDatabaseJsonString(TAG, request);
    }

    @Override
    protected void onPostExecute(String jsonResultsString) {
        if (listener.get() != null) {
            listener.get().onJsonMovieDataReady(jsonResultsString);
        }
    }

    interface OnJsonMovieDataReadyListener {
        void onJsonMovieDataReady(String movieDataJson);
    }

}
