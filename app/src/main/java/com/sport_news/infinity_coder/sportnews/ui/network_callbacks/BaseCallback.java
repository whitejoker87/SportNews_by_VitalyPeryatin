package com.sport_news.infinity_coder.sportnews.ui.network_callbacks;

import android.support.annotation.NonNull;

import com.sport_news.infinity_coder.sportnews.data.network.response.ResponseModel;
import com.sport_news.infinity_coder.sportnews.ui.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<P extends BasePresenter, R extends ResponseModel> implements Callback<R> {

    P presenter;

    BaseCallback(P presenter){
        this.presenter = presenter;
    }

    public abstract void onResponse(@NonNull Call<R> call, @NonNull Response<R> response);
    public abstract void onFailure(@NonNull Call<R> call, @NonNull Throwable t);
}
