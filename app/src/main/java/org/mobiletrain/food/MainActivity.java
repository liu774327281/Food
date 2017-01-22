package org.mobiletrain.food;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.mobiletrain.food.adapter.MainViewPagerAdatper;
import org.mobiletrain.food.bean.Classfy;
import org.mobiletrain.food.fragment.BlankFragment;
import org.mobiletrain.food.net.HttpUtils;
import org.mobiletrain.food.tools.Constants;
import org.mobiletrain.food.tools.ParseJson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.DOWNLOADSUCCESS:
                    List<Classfy> classfies = ParseJson.parseJson2Classfy((String) msg.obj);
                    List<Fragment> fragments = new ArrayList<>();
                    for (int i = 0; i < classfies.size(); i++) {
                        fragments.add(BlankFragment.newInstance(classfies.get(i).getId() + "", i));
                    }
                    MainViewPagerAdatper adatper = new MainViewPagerAdatper(getSupportFragmentManager(), fragments, classfies);
                    viewPager.setAdapter(adatper);
                    tabLayout.setupWithViewPager(viewPager);
                    break;
            }
        }
    };
    private TextView loginTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initView();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_header_container);
        //获取NavigationView的头布局
        View headerView = navigationView.getHeaderView(0);
        //获取登录控件
        loginTv = (TextView) headerView.findViewById(R.id.login_tv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                startActivity(new Intent(MainActivity.this,CollectActivity.class));
                return false;
            }
        });
        updateLoginTv();
//        loginTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "123", Toast.LENGTH_SHORT).show();
//            }
//        });
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] data = HttpUtils.getData(Constants.CLASSFYURL);
                String json = new String(data, 0, data.length);
                Message message = mHandler.obtainMessage();
                message.obj = json;
                message.what = Constants.DOWNLOADSUCCESS;
                mHandler.sendMessage(message);
            }
        }).start();
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    //当登录Activity销毁后回调该方法，在该方法中更新loginTv的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateLoginTv();
    }

    private void updateLoginTv() {
        SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        boolean isLogin = loginInfo.getBoolean("isLogin", false);
        if (isLogin) {
            loginTv.setText(loginInfo.getString("username", "请登录"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除消息队列中所有的消息，避免发生内存泄露
        mHandler.removeCallbacksAndMessages(null);
    }

    public void loginTv(View view) {
//        Toast.makeText(MainActivity.this, "456", Toast.LENGTH_SHORT).show();
        if (!getSharedPreferences("loginInfo", MODE_PRIVATE).getBoolean("isLogin", false)) {
            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 1);
        } else {
            //跳转到用户详情页
        }
    }
}
