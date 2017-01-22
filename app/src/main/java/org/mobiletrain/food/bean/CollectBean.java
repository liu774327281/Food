package org.mobiletrain.food.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wangsong on 2016/6/16.
 */
public class CollectBean extends BmobObject {
    private String url;
    private String title;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CollectBean() {
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CollectBean(String url) {
        this.url = url;
    }
}
