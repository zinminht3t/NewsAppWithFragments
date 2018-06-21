package me.zinminhtet.newsappwithfragments.Classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsItem extends HashMap<String, String> {
    public NewsItem(String area, String title, String url) {
        put("area", area);
        put("title", title);
        put("url", url);
    }

    public static List<NewsItem> jread() {
        List<NewsItem> list = new ArrayList<NewsItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl("http://172.27.240.226/my/news?json");
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new NewsItem(b.getString("area"), b.getString("title"),
                        b.getString("url")));
            }
        } catch (Exception e) {
            Log.e("NewsItem", "JSONArray error");
        }
        return(list);
    }
}