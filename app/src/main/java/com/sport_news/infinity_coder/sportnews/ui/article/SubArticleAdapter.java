package com.sport_news.infinity_coder.sportnews.ui.article;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport_news.infinity_coder.sportnews.R;
import com.sport_news.infinity_coder.sportnews.data.network.response.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubArticleAdapter extends RecyclerView.Adapter<SubArticleAdapter.SubArticleViewHolder> {

    private List<Article.SubArticle> subArticles = new ArrayList<>();
    private Context context;

    public SubArticleAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public SubArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_subarticle, parent, false);
        return new SubArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubArticleViewHolder holder, int position) {
        holder.tvSubArticle.setText(subArticles.get(position).text);
    }

    public void setList(List<Article.SubArticle> subArticles){
        this.subArticles.clear();
        for (Article.SubArticle subArticle : subArticles)
            if(subArticle.text.length() > 0)
                this.subArticles.add(subArticle);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return subArticles.size();
    }

    class SubArticleViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvSubarticleText)
        TextView tvSubArticle;

        SubArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setTypeface();
        }

        private void setTypeface(){
            if(context != null) {
                Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/PlayfairDisplay-Regular.ttf");
                tvSubArticle.setTypeface(type);
            }
        }
    }
}
