package com.sport_news.infinity_coder.sportnews.data.network;

import com.sport_news.infinity_coder.sportnews.data.network.response.Article;
import com.sport_news.infinity_coder.sportnews.data.network.response.Events;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ISportNews {
    @GET("list.php")
    Call<Events> getCategories(@Query("category") String category);

    @POST("post.php")
    Call<Article> getArticle(@Query("article") String article);

}
