package org.mobiletrain.food.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobiletrain.food.bean.Classfy;
import org.mobiletrain.food.bean.Food;
import org.mobiletrain.food.bean.FoodDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsong on 2016/6/15.
 */
public class ParseJson {
    /**
     * 解析食物分类Json
     *
     * @param obj
     * @return
     */
    public static List<Classfy> parseJson2Classfy(String obj) {
        List<Classfy> classfies = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(obj);
            JSONArray tngou = jo.getJSONArray("tngou");
            for (int i = 0; i < tngou.length(); i++) {
                JSONObject data = tngou.getJSONObject(i);
                String description = data.getString("description");
                int foodclass = data.getInt("foodclass");
                int id = data.getInt("id");
                String keywords = data.getString("keywords");
                String name = data.getString("name");
                int seq = data.getInt("seq");
                String title = data.getString("title");
                classfies.add(new Classfy(description, foodclass, id, keywords, name, seq, title));
            }
            return classfies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Food> parseJson2Food(String json) {
        List<Food> list = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(json);
            JSONArray tngou = jo.getJSONArray("tngou");
            for (int i = 0; i < tngou.length(); i++) {
                JSONObject data = tngou.getJSONObject(i);
                int count = data.getInt("count");
                String description = data.getString("description");
                String disease = data.getString("disease");
                int fcount = data.getInt("fcount");
                String food = data.getString("food");
                int id = data.getInt("id");
                String img = "http://tnfs.tngou.net/image" + data.getString("img");
                String keywords = data.getString("keywords");
                String name = data.getString("name");
                int rcount = data.getInt("rcount");
                String summary = data.getString("summary");
                String symptom = data.getString("symptom");
                list.add(new Food(count, description, disease, fcount, food, id, img, keywords, name, rcount, summary, symptom));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FoodDetail parseJson2FoodDetail(String json) {
        try {
            JSONObject data = new JSONObject(json);
            int count = data.getInt("count");
            int fcount = data.getInt("fcount");
            int id = data.getInt("id");
            int rcount = data.getInt("rcount");
            boolean status = data.getBoolean("status");
            String description = data.getString("description");
            String disease = data.getString("disease");
            String food = data.getString("food");
            String img = "http://tnfs.tngou.net/image"+data.getString("img");
            String keywords = data.getString("keywords");
            String message = data.getString("message");
            String name = data.getString("name");
            String summary = data.getString("summary");
            String symptom = data.getString("symptom");
            String url = data.getString("url");
            return new FoodDetail(count, description, disease, fcount, food, id, img, keywords, message, name, rcount, status, summary, symptom, url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
