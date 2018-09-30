package com.androstock.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by oliver on 2018/4/5.
 */

public class SearchActivity extends AppCompatActivity {
    private String SearchResult;
    SearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview_android_example);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search View");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
                SearchResult = query;
                Intent i = new Intent(SearchActivity.this, ListRow.class);
                Intent putExtra = i.putExtra("searchresult", SearchResult);
                Intent putExtra1 = i.putExtra("uniqid","fromSearchActivity");
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //   Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });




    }
}

