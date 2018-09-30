package com.androstock.newsapp;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by oliver on 2018/4/6.
 */

public class CreateSearch extends AsyncTask<String, String, String> {


    HttpURLConnection conn;
    URL url = null;
    String HashNum;
    String WordCount;
    String Word1;
    String Word2;
    String Word3;


    public void setHashNum(String hashNum){
        this.HashNum=hashNum;
    }

    public void setWordCount(String wordCount){
        this.WordCount = wordCount;
    }

    public void setWord1(String word1){
        this.Word1 = word1;
    }

    public void setWord2(String word2){
        this.Word2 = word2;
    }

    public void setWord3(String word3){
        this.Word3 = word3;
    }
    @Override
    protected void onPreExecute() {


    }

    @Override
    protected String doInBackground(String... params) {
        try {

            // Enter URL address where your php file resides
            url = new URL("http://10.6.2.137/android_connect/create_searchterm.php");

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }
        try {

            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            // setDoInput and setDoOutput to true as we send and recieve data
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // add parameter to our above url
            Uri.Builder builder = new Uri.Builder().appendQueryParameter("hashnum", HashNum);
            builder = builder.appendQueryParameter("wordcount", WordCount);
            builder = builder.appendQueryParameter("word1", Word1);
            builder = builder.appendQueryParameter("word2", Word2);
            builder = builder.appendQueryParameter("word3", Word3);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return e1.toString();
        }

        try {

            int response_code = conn.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Pass data to onPostExecute method
                return (result.toString());

            } else {
                return("Connection error");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            conn.disconnect();
        }


    }

    @Override
    protected void onPostExecute(String result) {

        //this method will be running on UI thread

        if(result.equals("no rows")) {

        }else{



            // Setup and Handover data to recyclerview


        }

    }

}

