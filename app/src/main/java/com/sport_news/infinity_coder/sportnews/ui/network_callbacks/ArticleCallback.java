package com.sport_news.infinity_coder.sportnews.ui.network_callbacks;

import android.support.annotation.NonNull;

import com.sport_news.infinity_coder.sportnews.data.network.response.Article;
import com.sport_news.infinity_coder.sportnews.ui.article.ArticlePresenter;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Response;

public class ArticleCallback extends BaseCallback<ArticlePresenter, Article> {

    public ArticleCallback(ArticlePresenter presenter) {
        super(presenter);
    }

    @Override
    public void onResponse(@NonNull Call<Article> call, @NonNull Response<Article> response) {
        if(response.code() == 200)
            presenter.successRequest(response.body());
    }

    @Override
    public void onFailure(@NonNull Call<Article> call, @NonNull Throwable t) {
        if(t instanceof SocketTimeoutException || t instanceof UnknownHostException)
            presenter.noConnection();
        else
            presenter.showError("Error: " + t.getMessage());
    }
}
