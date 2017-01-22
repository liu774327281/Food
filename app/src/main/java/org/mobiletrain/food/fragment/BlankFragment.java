package org.mobiletrain.food.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.mobiletrain.food.DetailActivity;
import org.mobiletrain.food.R;
import org.mobiletrain.food.adapter.AdsAdapter;
import org.mobiletrain.food.adapter.BlankFGLvAdapter;
import org.mobiletrain.food.bean.Food;
import org.mobiletrain.food.net.HttpUtils;
import org.mobiletrain.food.tools.Constants;
import org.mobiletrain.food.tools.ParseJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsong on 2016/6/15.
 */
public class BlankFragment extends BaseFragment {

    private ListView listView;
    private View progressBar;
    private List<Food> foods;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.DOWNLOADSUCCESS:
                    progressBar.setVisibility(View.GONE);
                    foods = (List<Food>) msg.obj;
                    BlankFGLvAdapter blankFGLvAdapter = new BlankFGLvAdapter(getActivity(), foods);
                    listView.setAdapter(blankFGLvAdapter);
                    break;
                case Constants.DOWNLOADFAILED:
                    progressBar.setVisibility(View.GONE);
                    break;
            }
        }
    };

    //通过id来拼接每一个分类的请求地址
    //index用来标注该Fragment是第几个Fragment（为了给第一个Fragment添加一个ViewPager而设置此参数）
    public static BlankFragment newInstance(String id, int index) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container);
        //当当前Fragment是第0个Fragment时初始化广告条
        if (getArguments().getInt("index") == 0) {
            initAdsBar(inflater);
        }
        initData();
        return view;
    }

    @NonNull
    private View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.blank_fragment_layout, container, false);
        listView = (ListView) view.findViewById(R.id.lv);
        TextView textView = (TextView) view.findViewById(R.id.nodata);
        //当ListView中没有数据时显示TextView
        listView.setEmptyView(textView);
        progressBar = view.findViewById(R.id.progress_bar);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //当给ListView添加了头布局之后，ListView的item position会从1开始计数
                int newPosition = position;
                if (getArguments().getInt("index") == 0) {
                    newPosition = position - 1;
                }
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", foods.get(newPosition).getId());
                startActivity(intent);
            }
        });
        return view;
    }

    private void initAdsBar(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.ads_layout, null);
        ViewPager pager = (ViewPager) view.findViewById(R.id.view_pager);
        List<String> urls = new ArrayList<>();
        urls.add("http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg");
        urls.add("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
        urls.add("http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg");
        urls.add("http://img.my.csdn.net/uploads/201309/01/1378037234_3539.jpg");
        urls.add("http://img.my.csdn.net/uploads/201309/01/1378037234_6318.jpg");
        AdsAdapter adsAdapter = new AdsAdapter(getActivity(), urls);
        pager.setAdapter(adsAdapter);
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //禁止父容器拦截事件
                v.getParent().requestDisallowInterceptTouchEvent(true);
                //这里返回值为false，如果返回true，则会屏蔽掉滑动事件
                return false;
            }
        });
        //给ListView添加头布局，必须在给ListView设置Adapter之前添加头布局，否则没有效果
        listView.addHeaderView(view);
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                //提取出构造一个Fragment时传递的id，用该id替换FOODLISTURL请求地址中的%s
                String id = String.format(Constants.FOODLISTURL, getArguments().getString("id"));
                byte[] data = HttpUtils.getData(id);
                if (data == null || data.length == 0) {
                    mHandler.sendEmptyMessage(Constants.DOWNLOADFAILED);
                    return;
                }

                String json = new String(data, 0, data.length);
                List<Food> foods = ParseJson.parseJson2Food(json);
                Message message = mHandler.obtainMessage();
                message.obj = foods;
                message.what = Constants.DOWNLOADSUCCESS;
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
