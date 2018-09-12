package com.sport_news.infinity_coder.sportnews.data.network.response;

import java.util.List;

public class Article implements ResponseModel {
    public String team1;
    public String team2;
    public String time;
    public List<SubArticle> article;
    public String prediction;

    public class SubArticle{
        public String header;
        public String text;
    }
}
