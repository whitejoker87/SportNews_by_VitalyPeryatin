package com.sport_news.infinity_coder.sportnews.ui.article;

import com.sport_news.infinity_coder.sportnews.data.network.response.Article;
import com.sport_news.infinity_coder.sportnews.ui.BaseView;

public interface ArticleView extends BaseView {
    void successRequest(Article article);
    void showError(String error);
    void noConnection();
}
