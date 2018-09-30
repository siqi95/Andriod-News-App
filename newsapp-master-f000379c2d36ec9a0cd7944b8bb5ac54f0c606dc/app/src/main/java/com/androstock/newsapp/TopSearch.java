package com.androstock.newsapp;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by xxm on 14/03/18.
 */

public class TopSearch {
    private String hashnum;
    private String wordcount;
    private String word1;
    private String word2;
    private String word3;
    private String count;
    private String searchterm;

    public TopSearch(String hashnum,String wordcount, String word1,String word2, String word3, String count,String searchterm) {
        this.hashnum = hashnum;
        this.wordcount = wordcount;
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.count = count;
        this.searchterm = searchterm;
    }


    public String getHashnum() {
        return hashnum;
    }

    public void setHashnum(String hashnum) {
        this.hashnum = hashnum;
    }

    public String getWordcount() {
        return wordcount;
    }

    public void setWordcount(String wordcount) {
        this.wordcount = wordcount;
    }

    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public String getWord3() {
        return word3;
    }

    public void setWord3(String word3) {
        this.word3 = word3;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count){
        this.count = count;
    }

    public String getSearchterm() {
        return searchterm;
    }

    public void setSearchterm() {
        this.searchterm = searchterm;
    }


}

