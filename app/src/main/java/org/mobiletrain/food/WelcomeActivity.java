package org.mobiletrain.food;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;

public class WelcomeActivity extends BaseActivity {

    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //更新配置文件、检查用户是否登录....
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                if (flag) {
                    SharedPreferences preferences = getSharedPreferences("qianfeng", MODE_PRIVATE);
                    boolean isFirstLogin = preferences.getBoolean("isFirstLogin", true);
                    if (isFirstLogin) {
                        startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    }
                    WelcomeActivity.this.finish();
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag = false;
    }
}
