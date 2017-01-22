package org.mobiletrain.food;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.mobiletrain.food.bean.CollectBean;
import org.mobiletrain.food.bean.FoodDetail;
import org.mobiletrain.food.net.HttpUtils;
import org.mobiletrain.food.tools.Constants;
import org.mobiletrain.food.tools.ParseJson;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class DetailActivity extends BaseActivity {

    private ImageView foodImg;
    private TextView content;
    private boolean isCol = false;
    private String urlStr;
    private FoodDetail foodDetail;
    private boolean isLive;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.DOWNLOADSUCCESS:
                    foodDetail = (FoodDetail) msg.obj;
                    Glide.with(DetailActivity.this).load(foodDetail.getImg()).placeholder(R.drawable.p1).centerCrop().into(foodImg);
                    //Html.fromHtml("")表示将一个HTML文本解析为HTML格式显示出来
                    content.setText(Html.fromHtml(foodDetail.getMessage()));
                    toolbar.setTitle(foodDetail.getName());
                    break;
                case Constants.DOWNLOADFAILED:
                    Toast.makeText(DetailActivity.this, "数据加载失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        isLive = true;
        urlStr = String.format(Constants.FOODDETAILURL, getIntent().getIntExtra("id", 0));
        initView();
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                byte[] data = HttpUtils.getData(urlStr);
                if (data == null || data.length == 0) {
                    mHandler.sendEmptyMessage(Constants.DOWNLOADFAILED);
                    return;
                }
                String json = new String(data, 0, data.length);
                FoodDetail foodDetail = ParseJson.parseJson2FoodDetail(json);
                if (isLive) {
                    Message message = mHandler.obtainMessage();
                    message.obj = foodDetail;
                    message.what = Constants.DOWNLOADSUCCESS;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        foodImg = (ImageView) findViewById(R.id.food_img);
        content = (TextView) findViewById(R.id.content);
        final ImageView colIv = (ImageView) findViewById(R.id.col_iv);
        BmobQuery<CollectBean> query = new BmobQuery<>();
        query.addWhereEqualTo("url", urlStr);
        query.findObjects(this, new FindListener<CollectBean>() {
            @Override
            public void onSuccess(List<CollectBean> list) {
                if (list != null && list.size() > 0) {
                    isCol = true;
                    colIv.setImageResource(R.drawable.star);
                } else {
                    isCol = false;
                    colIv.setImageResource(R.drawable.unstar);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        colIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //表示已经被收藏，此时点击收藏按钮取消收藏
                if (isCol) {
                    isCol = false;
                    BmobQuery<CollectBean> bmobQuery = new BmobQuery<CollectBean>();
                    bmobQuery.addWhereEqualTo("url", urlStr);
                    bmobQuery.findObjects(DetailActivity.this, new FindListener<CollectBean>() {
                        @Override
                        public void onSuccess(List<CollectBean> list) {
                            if (list != null && list.size() > 0) {
                                CollectBean collectBean = list.get(0);
                                collectBean.delete(DetailActivity.this, new DeleteListener() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(DetailActivity.this, "取消关注！", Toast.LENGTH_SHORT).show();
                                        colIv.setImageResource(R.drawable.unstar);
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                } else {
                    //收藏的方法
                    final CollectBean collectBean = new CollectBean();
                    collectBean.setUrl(urlStr);
                    collectBean.setTitle(foodDetail.getName());
                    collectBean.setId(foodDetail.getId());
                    collectBean.save(DetailActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(DetailActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                            colIv.setImageResource(R.drawable.star);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                    isCol = true;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isLive = false;
    }
}
