package org.mobiletrain.food.bean;

/**
 * Created by wangsong on 2016/6/15.
 */
public class Classfy {
    private int id;
    private int foodclass; //上级分类ID，0为定级
    private String name;
    private String title;
    private String keywords;
    private String description;
    private int seq;//排序 从0。。。。10开始

    public Classfy() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFoodclass() {
        return foodclass;
    }

    public void setFoodclass(int foodclass) {
        this.foodclass = foodclass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Classfy(String description, int foodclass, int id, String keywords, String name, int seq, String title) {
        this.description = description;
        this.foodclass = foodclass;
        this.id = id;
        this.keywords = keywords;
        this.name = name;
        this.seq = seq;
        this.title = title;
    }
}
