package com.sport_news.infinity_coder.sportnews.ui.network_callbacks;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sport_news.infinity_coder.sportnews.data.network.response.Events;
import com.sport_news.infinity_coder.sportnews.ui.news_list.NewsListPresenter;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Response;

public class EventsCallback extends BaseCallback<NewsListPresenter, Events> {
    public EventsCallback(NewsListPresenter presenter) {
        super(presenter);
    }

    @Override
    public void onResponse(@NonNull Call<Events> call, @NonNull Response<Events> response) {
        if(response.code() == 200) {
            presenter.successRequest(response.body().events);
        }
    }

    @Override
    public void onFailure(@NonNull Call<Events> call, @NonNull Throwable t) {
        if(t instanceof SocketTimeoutException || t instanceof UnknownHostException)
            presenter.noConnection();
        else
            presenter.showError("Error: " + t.getMessage());
    }
}
