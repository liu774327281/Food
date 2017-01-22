package org.mobiletrain.food.app;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by wangsong on 2016/6/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"a4dbf977ceeb703011b12f01aa6aa5d8");
    }
}
