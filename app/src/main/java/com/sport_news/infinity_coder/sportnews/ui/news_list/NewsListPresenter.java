package com.sport_news.infinity_coder.sportnews.ui.news_list;

import com.sport_news.infinity_coder.sportnews.data.network.response.Events;
import com.sport_news.infinity_coder.sportnews.ui.BasePresenter;
import com.sport_news.infinity_coder.sportnews.ui.network_callbacks.EventsCallback;

import java.util.HashMap;
import java.util.List;

public class NewsListPresenter extends BasePresenter<NewsListView> {

    private static NewsListPresenter instance;
    private static EventsCallback eventsCallback;
    private HashMap<String, List<Events.Category>> categoriesMap = new HashMap<>();
    private String category;

    private NewsListPresenter(){
        super();
    }

    public static synchronized NewsListPresenter getInstance(){
        if(instance == null) {
            instance = new NewsListPresenter();
            eventsCallback = new EventsCallback(instance);
        }
        return instance;
    }

    public void getEventsByCategory(String category, boolean isFromLocalData){
        this.category = category;
        if(categoriesMap.containsKey(category) && isFromLocalData)
            successRequest(categoriesMap.get(category));
        else
            news.getCategories(category).enqueue(eventsCallback);
    }

    public void getEventsByCategory(String category){
        getEventsByCategory(category, true);
    }

    public void successRequest(List<Events.Category> categories){
        categoriesMap.put(category, categories);
        view.successRequest(categories);
    }

    public void showError(String error){
        view.showError(error);
    }

    public void noConnection(){
        view.noConnection();
    }
}
