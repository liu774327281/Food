package org.mobiletrain.food.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wangsong on 2016/6/16.
 */
public class User extends BmobObject {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
