package org.mobiletrain.food.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.mobiletrain.food.bean.Classfy;

import java.util.List;

/**
 * Created by wangsong on 2016/6/15.
 */
public class MainViewPagerAdatper extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<Classfy> titles;

    public MainViewPagerAdatper(FragmentManager fm, List<Fragment> fragments, List<Classfy> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getTitle();
    }
}
