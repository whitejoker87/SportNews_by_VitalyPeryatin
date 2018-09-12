package com.sport_news.infinity_coder.sportnews.ui.news_list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport_news.infinity_coder.sportnews.R;
import com.sport_news.infinity_coder.sportnews.data.network.response.Events;
import com.sport_news.infinity_coder.sportnews.ui.article.ArticleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewListAdapter extends RecyclerView.Adapter<NewListAdapter.NewsViewHolder> {

    private List<Events.Category> categories = new ArrayList<>();
    private Context context;

    NewListAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.tvTitle.setText(categories.get(position).title);
        holder.tvPreview.setText(categories.get(position).preview);
        holder.tvTime.setText(categories.get(position).time);
        holder.tvCoefficient.setText(categories.get(position).coefficient);
        holder.tvPlace.setText(categories.get(position).place);
    }

    public void updateList(List<Events.Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvPreview)
        TextView tvPreview;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvCoefficient)
        TextView tvCoefficient;
        @BindView(R.id.tvPlace)
        TextView tvPlace;

        NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setTypeface();
        }

        @OnClick(R.id.card_item)
        public void onClickCardView(){
            String article = categories.get(getAdapterPosition()).article;
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra("article", article);
            context.startActivity(intent);
        }

        private void setTypeface(){
            if(context != null) {
                Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/PlayfairDisplay-Regular.ttf");
                Typeface typeBold = Typeface.createFromAsset(context.getAssets(), "fonts/PlayfairDisplay-Bold.ttf");
                tvTitle.setTypeface(typeBold);
                tvPreview.setTypeface(type);
                tvTime.setTypeface(type);
                tvCoefficient.setTypeface(type);
                tvPlace.setTypeface(typeBold);

            }
        }

    }
}
