package com.example.newsapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Angie on 6/18/2017.
 */

public class NetworkUtils {
    public static final String TAG = "NetworkingURL";

    public static final String BASE_URL = "https://newsapi.org/v1/articles";
    public static final String PARAM_KEY = "apikey";
    public static final String PARAM_SOURCE="source";
    public static final String source = "the-next-web";
    public static final String PARAM_SORT = "sortBy";
    public static final String sortBy = "latest";

    // TODO Insert API key
    public static final String API_key = "";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE,source)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .appendQueryParameter(PARAM_KEY, API_key)
                .build();

        URL url = null;
        try {
            String urlString= builtUri.toString();
            Log.d(TAG, "Url: " + urlString);
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
