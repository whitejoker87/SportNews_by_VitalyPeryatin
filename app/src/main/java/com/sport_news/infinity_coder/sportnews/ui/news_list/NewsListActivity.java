package com.sport_news.infinity_coder.sportnews.ui.news_list;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sport_news.infinity_coder.sportnews.R;
import com.sport_news.infinity_coder.sportnews.data.network.response.Events;
import com.sport_news.infinity_coder.sportnews.ui.WaitingFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sport_news.infinity_coder.sportnews.ui.news_list.CategoryKeys.BASKETBALL;
import static com.sport_news.infinity_coder.sportnews.ui.news_list.CategoryKeys.CYBERSPORT;
import static com.sport_news.infinity_coder.sportnews.ui.news_list.CategoryKeys.FOOTBALL;
import static com.sport_news.infinity_coder.sportnews.ui.news_list.CategoryKeys.HOCKEY;
import static com.sport_news.infinity_coder.sportnews.ui.news_list.CategoryKeys.TENNIS;
import static com.sport_news.infinity_coder.sportnews.ui.news_list.CategoryKeys.VOLLEYBALL;

public class NewsListActivity extends AppCompatActivity implements NewsListView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;
    private String title, currentCategory;
    private SharedPreferences sPref;
    private static final String DEFAULT_CATEGORY = FOOTBALL;
    private static final String CATEGORY_KEY = "category";
    private static final String
            FRAGMENT_TAG_EMPTY = "empty",
            FRAGMENT_TAG_WAIT = "wait";

    private HashMap<String, String> titleMap = new HashMap<>();
    private boolean hasRefresh = false;
    private NewsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        unbinder = ButterKnife.bind(this);
        presenter = NewsListPresenter.getInstance();
        presenter.attachView(this);

        navigationView.setNavigationItemSelectedListener(this);
        setTitleMap();
        sPref = getSharedPreferences("savedData", MODE_PRIVATE);
        currentCategory = sPref.getString(CATEGORY_KEY, "");
        if(currentCategory.equals("")){
            currentCategory = DEFAULT_CATEGORY;
            saveCategory(currentCategory);
        }
        setToolbar();
        title = titleMap.get(currentCategory);
        toolbar.setTitle(title);
        getEventsByCategory(currentCategory);
        navigationView.setCheckedItem(getItemIdByCategory(currentCategory));
    }

    private void setTitleMap(){
        titleMap.put(FOOTBALL, getString(R.string.football));
        titleMap.put(HOCKEY, getString(R.string.hockey));
        titleMap.put(TENNIS, getString(R.string.tennis));
        titleMap.put(BASKETBALL, getString(R.string.basketball));
        titleMap.put(VOLLEYBALL, getString(R.string.volleyball));
        titleMap.put(CYBERSPORT, getString(R.string.cybersport));
    }

    private int getItemIdByCategory(String category){
        switch (category){
            case FOOTBALL:
                return R.id.football_item;
            case HOCKEY:
                return R.id.hockey_item;
            case TENNIS:
                return R.id.tennis_item;
            case BASKETBALL:
                return R.id.basketball_item;
            case VOLLEYBALL:
                return R.id.volleyball_item;
            case CYBERSPORT:
                return R.id.cybersport_item;
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showError(String error) {
        setHasRefreshMenu(true);
        RecyclerFragment fragment = new RecyclerFragment();
        fragment.setCategories(new ArrayList<>());
        setFragment(fragment, FRAGMENT_TAG_EMPTY);
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
                getEventsByCategory(currentCategory);
                break;
        }
        return true;
    }

    private void setHasRefreshMenu(boolean hasRefresh){
        this.hasRefresh = hasRefresh;
        invalidateOptionsMenu();
    }

    @Override
    public void noConnection() {
        setHasRefreshMenu(true);
        RecyclerFragment fragment = new RecyclerFragment();
        fragment.setCategories(new ArrayList<>());
        setFragment(fragment, FRAGMENT_TAG_EMPTY);
        toolbar.setTitle(getString(R.string.no_connection));
        Toast.makeText(this, getString(R.string.check_network), Toast.LENGTH_SHORT).show();
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        toolbar.setNavigationOnClickListener((view)->drawerLayout.openDrawer(navigationView));
    }

    @Override
    public void successRequest(List<Events.Category> categories) {
        setHasRefreshMenu(false);
        RecyclerFragment recyclerFragment = (RecyclerFragment) getSupportFragmentManager().findFragmentByTag(currentCategory);
        if (recyclerFragment != null && recyclerFragment.isVisible())
            recyclerFragment.updateRecycler(categories);
        else {
            RecyclerFragment fragment = new RecyclerFragment();
            fragment.setCategories(categories);
            setFragment(fragment, currentCategory);
            saveCategory(currentCategory);
        }
        toolbar.setTitle(title);
    }

    private void saveCategory(String category){
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(CATEGORY_KEY, category);
        editor.apply();
    }

    public void getEventsByCategory(String category){
        setHasRefreshMenu(false);
        setFragment(new WaitingFragment(), FRAGMENT_TAG_WAIT);
        toolbar.setTitle(getString(R.string.wait_please));
        presenter.getEventsByCategory(category);
    }

    public void refreshEvents(){
        presenter.getEventsByCategory(currentCategory, false);
    }

    private void setFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_news_list, fragment, tag);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.football_item:
                currentCategory = FOOTBALL;
                break;
            case R.id.hockey_item:
                currentCategory = HOCKEY;
                break;
            case R.id.tennis_item:
                currentCategory = TENNIS;
                break;
            case R.id.basketball_item:
                currentCategory = BASKETBALL;
                break;
            case R.id.volleyball_item:
                currentCategory = VOLLEYBALL;
                break;
            case R.id.cybersport_item:
                currentCategory = CYBERSPORT;
                break;
        }
        getEventsByCategory(currentCategory);
        title = item.getTitle().toString();
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(navigationView))
            drawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }
}
