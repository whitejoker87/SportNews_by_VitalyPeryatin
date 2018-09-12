package com.sport_news.infinity_coder.sportnews.ui.article;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport_news.infinity_coder.sportnews.R;
import com.sport_news.infinity_coder.sportnews.data.network.response.Article;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ArticleFragment extends Fragment{

    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvPrediction)
    TextView tvPrediction;
    @BindView(R.id.subArticleRecycler)
    RecyclerView subArticleRecycler;
    SubArticleAdapter subArticleAdapter;

    private Unbinder unbinder;
    private Article article;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false);
        subArticleRecycler.setLayoutManager(horizontalLayoutManager);
        subArticleAdapter = new SubArticleAdapter(getContext());
        subArticleRecycler.setAdapter(subArticleAdapter);
        setTypeface();
        return view;
    }

    public void setTypeface(){
        if(context != null) {
            Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/PlayfairDisplay-Regular.ttf");
            tvTime.setTypeface(type);
            tvPrediction.setTypeface(type);
        }
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(article != null) {
            tvTime.setText(article.time);
            tvPrediction.setText(article.prediction);
            if(article.article.size() == 0)
                subArticleRecycler.setVisibility(View.INVISIBLE);
            else
                subArticleAdapter.setList(article.article);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
