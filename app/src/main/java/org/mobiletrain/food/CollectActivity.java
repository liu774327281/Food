package org.mobiletrain.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.mobiletrain.food.adapter.ColListViewAdapter;
import org.mobiletrain.food.bean.CollectBean;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CollectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_collect);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的收藏");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ListView listView = (ListView) findViewById(R.id.lv);
        BmobQuery<CollectBean> query = new BmobQuery<>();
        query.findObjects(this, new FindListener<CollectBean>() {
            @Override
            public void onSuccess(final List<CollectBean> list) {
                ColListViewAdapter adapter = new ColListViewAdapter(CollectActivity.this, list);
                listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(CollectActivity.this, DetailActivity.class);
                        intent.putExtra("id", list.get(position).getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
