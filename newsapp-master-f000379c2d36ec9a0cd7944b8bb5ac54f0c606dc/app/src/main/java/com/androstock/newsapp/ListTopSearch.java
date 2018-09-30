package com.androstock.newsapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ListTopSearch extends AsyncTask <String, Void, String> {
    private ArrayList<TopSearch> dataset = new ArrayList<TopSearch>();
    private static final String url_get_searchterm = "http://10.6.29.62/android_connect/get_searchterm.php/";
    private static final String url_update_count = "http://10.6.29.62/android_connect/update_count.php";
    private static final String url_create_searchterm = "http://10.6.29.62/android_connect/create_searchterm.php";
    private static final String url_get_top5 = "http://10.6.24.125/android_connect/get_top5.php";

    public ArrayList<TopSearch> getDataset() {
        return dataset;
    }

    protected void onPreExecute() {
        super.onPreExecute();

    }

    protected String doInBackground(String... args) {
        String xml = "";
        String urlParameters = "";

        xml = Function.excuteGet(url_get_top5, urlParameters);

        return xml;

    }


    protected void onPostExecute(String xml) {
        xml = xml.substring(14);

        if (xml.length() > 10) { // Just checking if not empty

            try {
                JSONObject jsonResponse = new JSONObject(xml);
                JSONArray jsonArray = jsonResponse.optJSONArray("top5");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String hashnum = jsonObject.optString("hashnum");
                    String wordcount = jsonObject.optString("wordcount");
                    String word1 = jsonObject.optString("word1");
                    String word2 = jsonObject.optString("word2");
                    String word3 = jsonObject.optString("word3");
                    String count = jsonObject.optString("count");
                    String searchterm = word1 + " " + word2 + " " + word3;
                    TopSearch topSearch = new TopSearch(hashnum, wordcount, word1, word2, word3, count, searchterm);
                    dataset.add(topSearch);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
