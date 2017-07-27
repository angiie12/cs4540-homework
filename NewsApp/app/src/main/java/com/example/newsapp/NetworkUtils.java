package com.example.newsapp;

import android.net.Uri;
import android.util.Log;

import com.example.newsapp.data.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Angie on 6/18/2017.
 */

public class NetworkUtils {
    public static final String TAG = "NetworkingURL";

    public static final String BASE_URL = "https://newsapi.org/v1/articles";
    public static final String PARAM_SOURCE = "source";
    public static final String source = "the-next-web";
    public static final String PARAM_SORT = "sortBy";
    public static final String sortBy = "latest";
    public static final String PARAM_KEY = "apiKey";

    // TODO Please insert your API key here
    public static final String apiKey = "";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, source)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .appendQueryParameter(PARAM_KEY, apiKey)
                .build();

        URL url = null;
        try {
            String urlString = builtUri.toString();
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


    // add parseJSON method
    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        ArrayList<NewsItem> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");
        String imgUrl = null;

        for (int i = 0; i < articles.length(); i++) {
            JSONObject item = articles.getJSONObject(i);
            String author = item.getString("author");
            String title = item.getString("title");
            String description = item.getString("description");
            String url = item.getString("url");
            String urlToImage = item.getString("urlToImage");
            String publishedAt = item.getString("publishedAt");

            JSONArray mediaObjects = item.optJSONArray("media");
            if (mediaObjects != null) {
                JSONObject img = mediaObjects.getJSONObject(0);
                JSONArray metaData = img.getJSONArray("media-metadata");
                JSONObject thumbNail = metaData.getJSONObject(0);
                imgUrl = thumbNail.getString("url");
            }

            NewsItem newsItem = new NewsItem(author, title, description, url, imgUrl, publishedAt);
            result.add(newsItem);
        }
        return result;
    }
}