package com.example.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    private ProgressBar progress;
    private TextView mSearchResultsTextView;
    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.search_box);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        mUrlDisplayTextView = (TextView) findViewById(R.id.url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.search_results);
    }

    private void makeNewsSearchQuery() {
        String newsQuery = mSearchBoxEditText.getText().toString();
        URL newsSearchUrl = NetworkUtils.buildUrl(newsQuery);
        mUrlDisplayTextView.setText(newsSearchUrl.toString());

        new NewsQueryTask().execute(newsSearchUrl);
    }

    public class NewsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String newsSearchResults = null;
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String newsSearchResults) {
            progress.setVisibility(View.INVISIBLE);

            if (newsSearchResults != null && !newsSearchResults.equals("")) {
                mSearchResultsTextView.setText(newsSearchResults);
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
            makeNewsSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
