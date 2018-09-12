package com.sport_news.infinity_coder.sportnews.ui.news_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sport_news.infinity_coder.sportnews.R;
import com.sport_news.infinity_coder.sportnews.data.network.response.Events;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.news_list)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private NewListAdapter newsListAdapter;
    private Unbinder unbinder;
    private List<Events.Category> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        newsListAdapter = new NewListAdapter(getContext());
        newsListAdapter.updateList(categories);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsListAdapter);
        super.onActivityCreated(savedInstanceState);
    }

    public void setCategories(List<Events.Category> categories){
        this.categories = categories;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        ((NewsListActivity) Objects.requireNonNull(getActivity())).refreshEvents();
    }

    public void updateRecycler(List<Events.Category> categories){
        newsListAdapter.updateList(categories);
        refreshLayout.setRefreshing(false);
    }
}
