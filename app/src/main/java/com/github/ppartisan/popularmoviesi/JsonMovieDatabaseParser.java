package com.github.ppartisan.popularmoviesi;

import com.github.ppartisan.popularmoviesi.utils.FetchJsonMovieDataUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonMovieDatabaseParser {

    private static final String KEY_RESULTS = "results";
    private static final String KEY_IMAGE = "poster_path";
    private static final String KEY_SYNOPSIS = "overview";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_AVERAGE_VOTE = "vote_average";

    public List<MovieModel> getMovieModelsFromJson(String jsonString) throws JSONException {

        JSONObject json = new JSONObject(jsonString);
        JSONArray results = json.getJSONArray(KEY_RESULTS);

        List<MovieModel> movies = new ArrayList<>(results.length());

        for (int i = 0; i < results.length(); i++) {

            JSONObject item = results.getJSONObject(i);
            final String imageUrl = item.getString(KEY_IMAGE);
            final String synopsis = item.getString(KEY_SYNOPSIS);
            final String releaseDate = item.getString(KEY_RELEASE_DATE);
            final String title = item.getString(KEY_ORIGINAL_TITLE);
            final double averageVote = item.getDouble(KEY_AVERAGE_VOTE);

            MovieModel movieModel = new MovieModel.Builder()
                    .title(title)
                    .imageUrl(FetchJsonMovieDataUtils.getImageUrlFromRelativePath(imageUrl))
                    .synopsis(synopsis)
                    .releaseDate(releaseDate)
                    .averageVote(averageVote)
                    .build();

            movies.add(movieModel);

        }

        return movies;

    }

}
