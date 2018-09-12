package com.sport_news.infinity_coder.sportnews.ui.article;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sport_news.infinity_coder.sportnews.R;
import com.sport_news.infinity_coder.sportnews.data.network.response.Article;
import com.sport_news.infinity_coder.sportnews.ui.WaitingFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ArticleActivity extends AppCompatActivity implements ArticleView{

    @BindView(R.id.toolbar_article)
    Toolbar toolbar;

    private ArticlePresenter presenter;
    private Unbinder unbinder;
    private boolean hasRefresh = false;
    private String articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        unbinder = ButterKnife.bind(this);
        presenter = ArticlePresenter.getInstance();
        presenter.attachView(this);

        articleId = getIntent().getStringExtra("article");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.wait_please);
        getArticle();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void successRequest(Article article) {
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArticle(article);
        setFragment(fragment);
        toolbar.setTitle(article.team1 + " - " + article.team2);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_article, fragment);
        transaction.commit();
    }

    @Override
    public void showError(String error) {
        setHasRefreshMenu(true);
        setFragment(new ArticleFragment());
        toolbar.setTitle(error);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        menu.setGroupVisible(R.id.group_refresh, hasRefresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_item:
                getArticle();
                break;
        }
        return true;
    }

    private void getArticle(){
        setHasRefreshMenu(false);
        toolbar.setTitle(getString(R.string.wait_please));
        setFragment(new WaitingFragment());
        presenter.getArticle(articleId);
    }

    private void setHasRefreshMenu(boolean hasRefresh){
        this.hasRefresh = hasRefresh;
        invalidateOptionsMenu();
    }

    @Override
    public void noConnection() {
        setHasRefreshMenu(true);
        setFragment(new ArticleFragment());
        toolbar.setTitle(R.string.no_connection);
        Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
    }
}
