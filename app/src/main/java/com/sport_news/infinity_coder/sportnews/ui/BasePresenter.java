package com.sport_news.infinity_coder.sportnews.ui;

import com.sport_news.infinity_coder.sportnews.data.network.ISportNews;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BasePresenter<T extends BaseView> {

    private static final String URL = "http://mikonatoruri.win/";
    protected T view;
    protected ISportNews news;

    protected BasePresenter(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build();
        news = retrofit.create(ISportNews.class);
    }

    public void attachView(T view){
        this.view = view;
    }

    public void detachView(){
        this.view = null;
    }
}
