package com.sport_news.infinity_coder.sportnews.data.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Events implements ResponseModel {
    public List<Category> events;

    public class Category {
        public String title;
        public String coefficient;
        public String time;
        public String place;
        public String preview;
        public String article;
    }
}
