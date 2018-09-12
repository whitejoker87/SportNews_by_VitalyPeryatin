package com.sport_news.infinity_coder.sportnews.ui.article;

import com.sport_news.infinity_coder.sportnews.data.network.response.Article;
import com.sport_news.infinity_coder.sportnews.ui.BasePresenter;
import com.sport_news.infinity_coder.sportnews.ui.network_callbacks.ArticleCallback;

public class ArticlePresenter extends BasePresenter<ArticleView> {
    private static ArticlePresenter instance;
    private static ArticleCallback articleCallback;
    private String articleId;
    private Article article;

    private ArticlePresenter(){
        super();
    }

    public static synchronized ArticlePresenter getInstance(){
        if(instance == null) {
            instance = new ArticlePresenter();
            articleCallback = new ArticleCallback(instance);
        }
        return instance;
    }

    public void getArticle(String articleId){
        if(articleId.equals(this.articleId) && article != null)
            successRequest(this.article);
        else {
            this.articleId = articleId;
            article = null;
            news.getArticle(articleId).enqueue(articleCallback);
        }
    }

    @Override
    public void attachView(ArticleView view){
        super.attachView(view);
    }

    public void successRequest(Article article){
        this.article = article;
        view.successRequest(article);
    }

    public void showError(String error){
        view.showError(error);
    }

    public void noConnection(){
        view.noConnection();
    }
}
