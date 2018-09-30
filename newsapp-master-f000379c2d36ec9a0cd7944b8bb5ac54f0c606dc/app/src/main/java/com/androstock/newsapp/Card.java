package com.androstock.newsapp;

import org.json.JSONObject;

/**
 * Created by xxm on 15/03/18.
 */


// each card contians the a hot topic
// do your algorithms here inside this class

// better using inheritance here i.e. card inherits NewsArray class
// will have to change this later
public class Card {
//   private String topic;
//   private String pic;
//   private int numView;
//   private String decription;
//   private ArrayList<News> newslist;
//   private String lastRefresh;

    private String topic;
    private String trend;
    private String coverImageUrl;
    private String sources;
    private String description;
    private String lastRefresh;
    private String mapImageUrl;


    public Card(JSONObject jsonObject){
        this.topic = jsonObject.optString("topic");
        this.trend = jsonObject.optString("trend");
        this.coverImageUrl = jsonObject.optString("coverImageUrl");
       // this.sources = jsonObject.optString("sources");
        this.description = jsonObject.optString("description");
        this.lastRefresh = jsonObject.optString("lastRefresh");
        this.mapImageUrl = jsonObject.optString("mapImageUrl");
    }


public String getTopic() {
    return topic;
}

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(String lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    public String getMapImageUrl() {
        return mapImageUrl;
    }

    public void setMapImageUrl(String mapImageUrl) {
        this.mapImageUrl = mapImageUrl;
    }
}
