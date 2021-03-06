package org.mobiletrain.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.mobiletrain.food.adapter.GuideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private LinearLayout dotLayout;
    private int prePosition;
    private int currentPosition;
    private int preState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //跳转成功之后，设置isFirstLogin值为false，
        getSharedPreferences("qianfeng", MODE_PRIVATE).edit().putBoolean("isFirstLogin", false).commit();
        initView();
    }

    private void initView() {
        prePosition = 0;
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dot_layout);
        int[] images = new int[]{R.drawable.p95, R.drawable.p96, R.drawable.p97, R.drawable.p98, R.drawable.p99};
        List<ImageView> imageViews = new ArrayList<>();
        for (int image : images) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(image);
            imageViews.add(imageView);

            View view = new View(this);
            view.setBackgroundResource(R.drawable.dot_selector);
            view.setEnabled(false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
            layoutParams.leftMargin = 20;
            view.setLayoutParams(layoutParams);
            dotLayout.addView(view);
        }
        dotLayout.getChildAt(0).setEnabled(true);
        GuideViewPagerAdapter adapter = new GuideViewPagerAdapter(imageViews);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //0->1 position一直都是0，直到滑动结束时position变为1
            //1->0 position一直都是0，直到滑动结束时position变为0
            //无论从左往右滑动还是从右往左滑动，position始终表示左边页面的位置，在滑动结束的最后一刻，position
            //会变为滑动结束后页面的位置，positionOffset表示右边页面偏移百分比，
            // positionOffsetPixels表示右边页面偏移像素
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                dotLayout.getChildAt(prePosition).setEnabled(false);
                dotLayout.getChildAt(position).setEnabled(true);
                prePosition = position;
            }

            //状态共分为3种，
            //0表示静止
            //1表示手指拖动
            //2表示自由滚动
            //只有在首页向右滑和尾页向左滑动时，状态的变换为1->0,如果再加上条件position=4，就可以唯一确定在最后一个页面向左滑动
            //其他滑动时，状态的变化为1—>2—>0
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("google_lenve_fb", "onPageScrollStateChanged: " + state);
                if (state == 0 && preState == 1 && currentPosition == 4) {
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    GuideActivity.this.finish();
                }
                preState = state;
            }
        });
    }
}