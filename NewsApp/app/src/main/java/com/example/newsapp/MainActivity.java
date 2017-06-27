package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newsapp.model.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";
    private ProgressBar progress;
    private EditText mSearchBoxEditText;
    private RecyclerView rv;
//    private TextView mSearchResultsTextView;
//    private TextView mUrlDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.search_box);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        rv = (RecyclerView)findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));
//        mUrlDisplayTextView = (TextView) findViewById(R.id.url_display);
//        mSearchResultsTextView = (TextView) findViewById(R.id.search_results);
    }

//    private void makeNewsSearchQuery() {
//        String newsQuery = mSearchBoxEditText.getText().toString();
//        URL newsSearchUrl = NetworkUtils.buildUrl();
////        mUrlDisplayTextView.setText(newsSearchUrl.toString());
//
//        new NewsQueryTask().execute(newsSearchUrl);
//    }

    class NewsQueryTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> {
        String query;

        NewsQueryTask(String s) {
            query = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {
            ArrayList<NewsItem> result = null;
            URL url = NetworkUtils.buildUrl(query, "latest");
            Log.d(TAG, "url: " + url.toString());
            try {
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("doInBackground", result == null ? "result is null!" : "There is something worst!");
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            progress.setVisibility(View.GONE);
            if (data != null) {
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, new RecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(String url) {
//                        String url = data.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("Url %s", url));
                        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browser);
                    }
                });
                rv.setAdapter(adapter);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            String s = mSearchBoxEditText.getText().toString();
            NewsQueryTask task = new NewsQueryTask(s);

            task.execute();
        }
        return true;
    }

}
