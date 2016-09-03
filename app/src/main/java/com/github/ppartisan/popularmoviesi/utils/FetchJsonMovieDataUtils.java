package com.github.ppartisan.popularmoviesi.utils;

import android.net.Uri;
import android.util.Log;

import com.github.ppartisan.popularmoviesi.ApiKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class FetchJsonMovieDataUtils {

    private static final String MOVIE_REQUEST_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String API_KEY_PARAM = "api_key";

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";

    public static final String SORT_BY_VOTE = "top_rated";
    public static final String SORT_BY_POPULARITY = "popular";

    public static final String IMAGE_WIDTH = "w500";

    private FetchJsonMovieDataUtils() { throw new AssertionError(); }

    public static String getMovieDatabaseRequestString(String sortCriteria) {
        return Uri.parse(MOVIE_REQUEST_BASE_URL).buildUpon()
                .appendPath(sortCriteria)
                .appendQueryParameter(API_KEY_PARAM, ApiKey.KEY).build().toString();
    }

    public static String getMovieDatabaseJsonString(String errorTag, String urlString) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String forecastJsonString = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }

            if (builder.length() == 0) {
                return null;
            }

            forecastJsonString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) connection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(errorTag, "Error closing stream", e);
                }
            }
        }

        return forecastJsonString;

    }

    public static String getImageUrlFromRelativePath(String relativePath) {
        return getImageUrlFromRelativePath(IMAGE_WIDTH, relativePath);
    }

    public static String getImageUrlFromRelativePath(String preferredWidth, String relativePath) {
        if (relativePath.charAt(0) == '/') {
            relativePath = relativePath.substring(1);
        }
        return Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(preferredWidth).appendPath(relativePath).build().toString();
    }

}
