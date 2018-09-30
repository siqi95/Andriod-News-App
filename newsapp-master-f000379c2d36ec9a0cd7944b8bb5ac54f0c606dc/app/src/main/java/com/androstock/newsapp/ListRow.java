package com.androstock.newsapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ListRow extends AppCompatActivity {

    ListView listNews;
    ProgressBar loader;
    private RefreshLayout mRefreshLayout;
    private static boolean isFirstEnter = true;
    private String newsTopic;
    private  String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (Function.isNetworkAvailable(getApplicationContext())) {
            getSupportActionBar().hide();
            setContentView(R.layout.list);

            //decide which activity starts the intent
            Intent intent = getIntent();
            if(intent != null){
                String source = intent.getStringExtra("uniqid");
                if(source.equals("fromSearchActivity")){

                    newsTopic = intent.getStringExtra("searchresult");
                }
                if(source.equals("fromMainActivity")){
                    //do nothing because they are already in the homepage
                    newsTopic = intent.getStringExtra("topic");
                }

            }


            NewsArray newsTask1 = new NewsArray();
            newsTask1.execute();
            doTable();

            listNews = (ListView) findViewById(R.id.listNews);
            loader = (ProgressBar) findViewById(R.id.loader);
            listNews.setEmptyView(loader);
            listNews.setDivider(getResources().getDrawable(R.drawable.list_divider));
            listNews.setDividerHeight((int) pxFromDp(getApplicationContext(), 0.5f));
            listNews.setSelector(R.color.transparent);
            //listNews.setBackgroundResource(R.drawable.list_news_shape);
            listNews.setCacheColorHint(Color.TRANSPARENT);

            mRefreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
            if (isFirstEnter) {
                isFirstEnter = false;
                NewsArray newsTask2 = new NewsArray();
                newsTask2.execute();
                mRefreshLayout.autoLoadmore();


            }

        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public String getNewsTopic() {
        return newsTopic;
    }


    class NewsArray extends AsyncTask<String, Void, String> {

        private ArrayList<News> dataset = new ArrayList<News>();


        String API_KEY = "17d5ac208dc844ffabe44a2d87e1d3f0"; // ### YOUE NEWS API HERE ###
        String NEWS_SOURCES = "bbc-news,fox-news,buzzfeed,cbs-news,new-york-times";
        String NEWS_TOPIC = ListRow.this.newsTopic;
        String NEWS_DATE = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        Calendar cal = Calendar.getInstance();


        public ArrayList<News> getDataset() {
            return dataset;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";
            xml = Function.excuteGet("https://newsapi.org/v2/everything?q=" + NEWS_TOPIC + "&sources=" + NEWS_SOURCES + "&from=" + NEWS_DATE + "&apikey=" + API_KEY, urlParameters);
            //xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=" + API_KEY, urlParameters);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (xml.length() > 10) { // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getJSONObject("source").optString("id");
                        String name = jsonObject.getJSONObject("source").optString("name");
                        String url = jsonObject.optString("url");
                        String title = jsonObject.optString("title");
                        String author = jsonObject.optString("author");
                        String publishedAT = jsonObject.optString("publishedAt");
                        String description = jsonObject.optString("description");
                        String urlToImage = jsonObject.optString("urlToImage");
                        News news = new News(url, urlToImage, description, author, title, publishedAT,name);
                        if(news.getNEWSpic()!=null&&news.getAuthor()!=null&&news.getDiscription()!=null&&news.getName()!=null&&news.getTitle()!=null&&news.getUrl()!=null){
                            dataset.add(news);
                        }





                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListNewsAdapter adapter = new ListNewsAdapter(ListRow.this, dataset);
                listNews.setAdapter(adapter);

                //direct to a web page when click the item here
                //need to call another page here to display the article
                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(ListRow.this, DetailsActivity.class);
                        i.putExtra("url", dataset.get(+position).getUrl());
                        startActivity(i);
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }

        }

        public void setDataset(ArrayList<News> dataset){
            this.dataset =dataset;
        }



    }

//         class CardPage extends NewsArray{
//         private ArrayList<card> cards = new ArrayList<>();
//
//
//             public ArrayList<card> getCards() {
//                 return cards;
//             }
//
//             //use card adapters here
//             //implement on click method and direct to the newslist page, check example from above class
//             //setOnItemClickListener(new AdapterView.OnItemClickListener()
//
//         }
    public void doTable(){
        String news = ListRow.this.newsTopic;
        String smallnews = news.toLowerCase();
        String splitnews[] = smallnews.split("\\s+");
        int hash1 = 7;
        int hash2 = 7;
        int hash3 = 7;

        for (int i = 0; i <splitnews[0].length(); i++){
            hash1 = hash1*31 + splitnews[0].charAt(i);
        }

        if(splitnews.length == 2 || splitnews.length == 3){
            for (int i = 0; i < splitnews[1].length(); i++) {
                hash2 = hash2 * 31 + splitnews[1].charAt(i);
            }
        }
        if(splitnews.length == 3) {
            for (int i = 0; i < splitnews[2].length(); i++) {
                hash3 = hash3 * 31 + splitnews[2].charAt(i);
            }
        }


        int hashnum = hash1 + hash2 + hash3;
        String hash = Integer.toString(hashnum);
        String length = Integer.toString(splitnews.length);
        AddSearch addSearch = new AddSearch();
        addSearch.setSearchQuery(hash);
        addSearch.setWord1(splitnews[0]);
        if(splitnews.length == 2 || splitnews.length == 3) {
            addSearch.setWord2(splitnews[1]);
        }
        if(splitnews.length == 3) {
            addSearch.setWord3(splitnews[2]);
        }
        addSearch.setWordCount(length);
        addSearch.execute();
    }


}












