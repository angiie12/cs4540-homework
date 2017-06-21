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
//    public static final String BASE_URL = "https://newsapi.org/v1/articles?source=techcrunch&apiKey=f3ab37873c744bdcbf5b696fff7fa489";
    public static final String BASE_URL = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=f3ab37873c744bdcbf5b696fff7fa489";
    public static final String PARAM_QUERY = "q";
    public static final String PARAM_SORT = "sort";
    public static final String sortBy = "latest";

    public static URL buildUrl(String searchQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, searchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
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
