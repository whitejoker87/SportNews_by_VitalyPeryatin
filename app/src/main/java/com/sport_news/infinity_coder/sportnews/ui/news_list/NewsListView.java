package com.sport_news.infinity_coder.sportnews.ui.news_list;

import com.sport_news.infinity_coder.sportnews.data.network.response.Events;
import com.sport_news.infinity_coder.sportnews.ui.BaseView;

import java.util.List;

public interface NewsListView extends BaseView {
    void showError(String error);
    void noConnection();
    void successRequest(List<Events.Category> categories);
}
